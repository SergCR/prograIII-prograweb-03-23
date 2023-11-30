package com.proyectoprogra3.proyectoprogra3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LogicLayer {
    @Autowired
    private DataAccessLayer databaseService; //inyecta el DataAccessLayer.java
    @Autowired
    public LogicLayer(DataAccessLayer databaseService) {
        this.databaseService = databaseService;
    }

    //------------------------------------------------------------Usuarios------------------------------------------------------------
    public List<User> getListaUsuarios(String token){
        if(databaseService.validateToken(token)){
            return databaseService.queryListAllUsers();
        }else{
            return new ArrayList<>();
        }
    }

    public User getUsuario(String searchEmail, String token){
        if(databaseService.validateToken(token)){
            return databaseService.querySpecificUser(searchEmail);
        }else{
            return new User();
        }
    }

    public boolean addUser(User theUser){
        return databaseService.addUser(theUser);
    }

    public boolean updateUser(User theUser, String token){
        if(databaseService.validateToken(token)){
            return databaseService.updateUser(theUser);
        }else{
            return false;
        }
    }

    public boolean deleteUser(User theUser, String token){
        if(databaseService.validateToken(token)){
            return databaseService.deleteUser(theUser);
        }else{
            return false;
        }
    }

    //------------------------------------------------------------Notas------------------------------------------------------------
    public boolean addNote(Notes theNote, String token){
        if(databaseService.validateToken(token)){
            return databaseService.addNote(theNote);
        }else{
            return false;
        }
    }

    public List<Notes> getAllNotes(String token){
        if(databaseService.validateToken(token)){
            return databaseService.queryListAllNotes();
        }else{
            return new ArrayList<>();
        }
    }

    public List<Notes> getNotesForUser(String userEmail, String token){
        if(databaseService.validateToken(token)){
            User theUser = this.getUsuario(userEmail, token);
            return databaseService.queryNotesForUser(theUser);
        }else{
            return new ArrayList<>();
        }
    }

    public Boolean updateNote(Notes theNote, String token){
        if(databaseService.validateToken(token)){
            return databaseService.updateNote(theNote);
        }else{
            return false;
        }
    }

    public Boolean deleteNote(Notes theNote, String token){
        if(databaseService.validateToken(token)){
            return databaseService.deleteNote(theNote);
        }else{
            return false;
        }
    }

    public Boolean setPasswordEnabled(Notes theNote, String token){
        if(databaseService.validateToken(token)){
            return databaseService.setPasswordEnabled(theNote);
        }else{
            return false;
        }
    }

    public Boolean setNoteCategory(Notes theNote, String token){
        if(databaseService.validateToken(token)){
            return databaseService.setNoteCategory(theNote);
        }else{
            return false;
        }
    }

    public Boolean setBackgroundColor(Notes theNote, String token){
        if(databaseService.validateToken(token)){
            return databaseService.setBackgroundColor(theNote);
        }else{
            return false;
        }
    }

    public Boolean setNoteSharedEmails(String tipo, String emailToWork, Notes theNote, String token){
        if(databaseService.validateToken(token)){
            String dbEmails = databaseService.getEmailsCompatidos(theNote).toLowerCase();
            emailToWork = emailToWork.toLowerCase().strip();
            switch (tipo) {
                case "agregar":
                    if (!dbEmails.toLowerCase().contains(emailToWork)){
                        dbEmails = dbEmails +","+emailToWork;
                    }
                    return databaseService.setNoteSharedEmails(theNote.getNoteID(), dbEmails);
                case "remover":
                    if (dbEmails.toLowerCase().contains(emailToWork)){
                        dbEmails = dbEmails.replace(","+emailToWork, "");
                    }
                    return databaseService.setNoteSharedEmails(theNote.getNoteID(), dbEmails);
                default:
                    return false;
            }
        }else{
            return false;
        }
    }

    public List<User> buildUsersListFromEmailsString(String emails){
        System.out.println("Emails: "+emails);
            List<User> listaUsers = new ArrayList<>();
            String cleanString = "";
            if (emails == null || emails.contentEquals(cleanString)){
                return listaUsers;
            }else{
                //Divide la lista de emails y los acomoda en un Array
                String[] usersTempArray = emails.toString().split(",");
                //Convierte el array en una lista iterable
                List<String> usersTempList = Arrays.asList(usersTempArray);
                //Itera la lista de emails
                for (String usersIterator1 : usersTempList) {
                    //Busca la informacion de cada usuario y lo agrega a la lista de Users que es una variable tipo List de la clase Notes
                    listaUsers.add(databaseService.querySpecificUser(usersIterator1));
                }
            }
            return listaUsers;
    }

    public User login(String email, String password) {
        User loginUser = databaseService.authenticateUser(email, password);
        if (loginUser != null){
            databaseService.saveAccessToken(loginUser.getJWTToken(), email);
            loginUser.setPassword("****************************");
        }else{
            loginUser = new User();
        }
        return loginUser;
    }
}
