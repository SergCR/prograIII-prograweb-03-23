package com.proyectoprogra3.proyectoprogra3;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataAccessLayer {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LogicLayer logicService;

    public Boolean isDatabaseConnected() {
        try {
            jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public User authenticateUser(String email, String password) {
        email = email.toLowerCase();
        try {
            String query = "SELECT * FROM Users WHERE email = ? and password = ?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                    int qUserID = (int) rs.getInt("user_id");
                    String qEmail = rs.getString("email");
                    String qPassword = rs.getString("password");
                    return new User(qUserID, qEmail, qPassword);
            }, email, password);
        }catch(EmptyResultDataAccessException e) {
            //throw new RuntimeException("User ID does not exist");
            System.out.println("User does not exist!");
            return null;
        }catch(IncorrectResultSizeDataAccessException e) {
            //throw new RuntimeException("More than one users with the same Id .......");
            System.out.println("Multiple users match!");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean saveAccessToken(String token, String email){
        String expirationDate = LocalDate.now().toString();
        try{
            String query = "INSERT INTO inotes.tokens (token, expiration, email) VALUES (?, ?, ?)";
            jdbcTemplate.update(query, token, expirationDate, email);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean validateToken(String token){
        // 3f54f747ac23d5067c1a7324de070c5f27da8d3c34bc9ce1bed0929e9c14004b

        try {
            String todayDate = LocalDate.now().toString();
            String query = "SELECT * FROM inotes.tokens WHERE token = ? and expiration >= ?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                    return true;
            }, token, todayDate);
        }catch(EmptyResultDataAccessException e) {
            //throw new RuntimeException("User ID does not exist");
            System.out.println("Token not found!");
            return false;
        }catch(IncorrectResultSizeDataAccessException e) {
            //throw new RuntimeException("More than one users with the same Id .......");
            System.out.println("More than one token found!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> queryListAllUsers() {
        try {
            String query = "SELECT * FROM inotes.users";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
            List<User> listaUsers = new ArrayList<>();

            for (Map<String, Object> row : resultList){
                int userID = (int) row.get("user_id");
                String nombreUser = (String) row.get("nombre");
                String apellidoUser = (String) row.get("apellido");
                String passwordUser = (String) row.get("password");
                String emailUser = (String) row.get("email");
                LocalDate createDate = (LocalDate) LocalDate.parse(row.get("created_date").toString());
                LocalDate modifiedDate = (LocalDate) LocalDate.parse(row.get("modified_date").toString());
                Boolean activeUser = (Boolean) row.get("active");

                User elUser = new User(userID, nombreUser, apellidoUser, passwordUser, emailUser, createDate, modifiedDate, activeUser);
                listaUsers.add(elUser);
                }
            
            return listaUsers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public User querySpecificUser(String email) {
        try {
            String query = "SELECT * FROM inotes.users WHERE email = ?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                int userID = (int) rs.getInt("user_id");
                String nombreUser = rs.getString("nombre");
                String apellidoUser = rs.getString("apellido");
                String passwordUser = rs.getString("password");
                String emailUser = rs.getString("email");
                LocalDate createDate = LocalDate.parse(rs.getDate("created_date").toString());
                LocalDate modifiedDate = LocalDate.parse(rs.getDate("modified_date").toString());
                Boolean activeUser = rs.getBoolean("active");

                return new User(userID, nombreUser, apellidoUser, passwordUser, emailUser,createDate, modifiedDate, activeUser);
            }, email);
        }catch(EmptyResultDataAccessException e) {
            //throw new RuntimeException("User ID does not exist");
            System.out.println("User not found");
            User theUser = new User();
            theUser.setEmail("");
            return theUser;
        }catch(IncorrectResultSizeDataAccessException e) {
            //throw new RuntimeException("More than one users with the same Id .......");
            System.out.println("More than one user found!");
            User theUser = new User();
            theUser.setEmail("");
            return theUser;
        } catch (Exception e) {
            e.printStackTrace();
            User theUser = new User();
            theUser.setEmail("");
            return theUser;
        }
    }

    public Boolean addUser(User theUser) {
        try {
            String todayDate = LocalDate.now().toString();
            String query = "INSERT INTO inotes.users (nombre, apellido, password, email, created_date, modified_date, active) VALUES (?,?,?,?,?,?,?)";
            jdbcTemplate.update(query, theUser.getNombre(), theUser.getApellido(), theUser.getPassword(), theUser.getEmail(), todayDate, todayDate, 1);
            
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean updateUser(User theUser) {
        try {
            String todayDate = LocalDate.now().toString();
            String query = "UPDATE inotes.users SET nombre = ?, apellido = ?, email = ?, password = ?, modified_date = ?, active = 1 WHERE user_id = ?";
            jdbcTemplate.update(query, theUser.getNombre(), theUser.getApellido(), theUser.getEmail(), theUser.getPassword(), todayDate, theUser.getUsuarioID());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean deleteUser(User theUser){
        try {
            String query = "DELETE FROM inotes.users WHERE email = ?";
            jdbcTemplate.update(query, theUser.getEmail());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean addNote(Notes theNote) {
        try {
            String query = "INSERT INTO inotes.notes (user_id, text, title, bg_color, pw_enabled, created_date, modified_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String todayDate = LocalDate.now().toString();
            jdbcTemplate.update(query, theNote.getUserID(), theNote.getNoteText(), theNote.getNoteTitle(), theNote.getNoteBackgroundColor(), theNote.getNotePasswordEnabled(), todayDate, todayDate);
            
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public List<Notes> queryListAllNotes(){
        List<Notes> listOfNotes = new ArrayList<>();
        try {
            String query = "SELECT * FROM inotes.notes";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);

            for (Map<String, Object> row : resultList){
                int note_id = (int) row.get("note_id");
                int user_id = (int) row.get("user_id");
                String noteText = (String) row.get("text");
                String noteTitle = (String) row.get("title");
                String noteBGColor = (String) row.get("bg_color");
                Boolean notePWEnabled = (Boolean) row.get("pw_enabled");
                String notePW = (String) row.get("note_password");
                String noteCategory = (String) row.get("note_category");
                List<User> noteListUsers = logicService.buildUsersListFromEmailsString((String) row.get("note_shared_with_emails"));
                LocalDate createDate = LocalDate.parse((String) row.get("created_date").toString());
                LocalDate modifiedDate = LocalDate.parse((String) row.get("modified_date").toString());

                Notes theNote = new Notes(note_id, user_id, noteText, noteTitle, noteBGColor, notePWEnabled, notePW, noteCategory, noteListUsers,createDate, modifiedDate);
                listOfNotes.add(theNote);
                }
            return listOfNotes;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Notes> queryNotesForUser(User theUser){
        List<Notes> listOfNotes = new ArrayList<>();
        try {
            String query = "SELECT * FROM inotes.notes WHERE user_id = ? OR note_shared_with_emails like '%"+theUser.getEmail()+"%'";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query, theUser.getUsuarioID());

            for (Map<String, Object> row : resultList){
                int note_id = (int) row.get("note_id");
                int user_id = (int) row.get("user_id");
                String noteText = (String) row.get("text");
                String noteTitle = (String) row.get("title");
                String noteBGColor = (String) row.get("bg_color");
                Boolean notePWEnabled = (Boolean) row.get("pw_enabled");
                String notePW = (String) row.get("note_password");
                String noteCategory = (String) row.get("note_category");
                List<User> noteListUsers = logicService.buildUsersListFromEmailsString((String) row.get("note_shared_with_emails"));
                LocalDate createDate = LocalDate.parse((String) row.get("created_date").toString());
                LocalDate modifiedDate = LocalDate.parse((String) row.get("modified_date").toString());
                
                Notes theNote = new Notes(note_id, user_id,noteText, noteTitle, noteBGColor, notePWEnabled, notePW, noteCategory, noteListUsers,createDate, modifiedDate);
                listOfNotes.add(theNote);
                }
            return listOfNotes;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Boolean updateNote(Notes theNote){
        try {
            String todayDate = LocalDate.now().toString();
            String query = "UPDATE inotes.notes SET text = ?, title = ?, modified_date = ? WHERE note_id = ?";
            jdbcTemplate.update(query, theNote.getNoteText(), theNote.getNoteTitle(), todayDate, theNote.getNoteID());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean deleteNote(Notes theNote){
        try {
            String query = "DELETE FROM inotes.notes WHERE note_id = ?";
            jdbcTemplate.update(query, theNote.getNoteID());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean setPasswordEnabled(Notes theNote){
        try {
            String todayDate = LocalDate.now().toString();
            if (theNote.getNotePasswordEnabled()){
                String query = "UPDATE inotes.notes SET pw_enabled = 1, note_password = ?, modified_date = ? WHERE note_id = ?";
                jdbcTemplate.update(query, theNote.getNotePassword(), todayDate, theNote.getNoteID());
            }else{
                String query = "UPDATE inotes.notes SET pw_enabled = 0, note_password = '', modified_date = ? WHERE note_id = ?";
                jdbcTemplate.update(query, todayDate, theNote.getNoteID());
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean setNoteCategory(Notes theNote){
        try {
            String todayDate = LocalDate.now().toString();
            String query = "UPDATE inotes.notes SET note_category = ?, modified_date = ? WHERE note_id = ?";
            jdbcTemplate.update(query, theNote.getNoteCategory(), todayDate, theNote.getNoteID());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean setBackgroundColor(Notes theNote){
        try {
            String todayDate = LocalDate.now().toString();
            String query = "UPDATE inotes.notes SET bg_color = ?, modified_date = ? WHERE note_id = ?";
            jdbcTemplate.update(query, theNote.getNoteBackgroundColor(), todayDate, theNote.getNoteID());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public String getEmailsCompatidos(Notes theNote){
        try {
            String query = "SELECT note_shared_with_emails FROM inotes.notes WHERE note_id = ?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            return rs.getString("nombre");
        }, theNote.getNoteID());
        } catch (Exception e) {
            System.out.println(e);
            return "transaccion invalida";
        }
    }

    public Boolean setNoteSharedEmails(Integer noteID, String emails){
        try {
            jdbcTemplate.execute("UPDATE inotes.notes SET note_shared_with_emails = '"+emails+"' WHERE note_id = "+noteID);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


}
