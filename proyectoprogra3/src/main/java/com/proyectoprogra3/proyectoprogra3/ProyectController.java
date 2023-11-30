package com.proyectoprogra3.proyectoprogra3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProyectController {

    @Autowired
    private LogicLayer logicService;

    //------------------------------------------------------------Usuarios------------------------------------------------------------
    @GetMapping("/getListaUsuarios") //check
    public List<User> getListaUsuarios(String token){
        return logicService.getListaUsuarios(token);
    }

    @GetMapping("/getUsuario") //check
    public User getListaUsuarios(String email, String token){
        return logicService.getUsuario(email, token);
    }

    @PostMapping("/addUsuario") //check
    public String addUsuario(String nombre, String apellido, String email, String password){
        User miUser = new User(0, nombre, apellido, password, email, null, null, true);
        if (logicService.addUser(miUser)){
            return "Usuario creado con exito! : Nombre: "+ nombre +" | Apellido: "+ apellido +" | Email: "+ email;
        }else{
            return "Creacion de usuario fallida!";
        }
    }

    @PutMapping("/updateUsuario") //check
    public String updateUsuario(Integer userid, String nombre, String apellido, String email, String password, String token){
        User theUser = new User(userid,nombre, apellido,password,email);
        if (logicService.updateUser(theUser, token)){
            return "Usuario actualizado con exito! : Nombre: "+ nombre +" | Apellido: "+ apellido +" | Email: "+ email;
        }else{
            return "Actualizacion de usuario fallida!";
        }
    }

    @DeleteMapping("/deleteUsuario") //check
    public String deleteUsuario(String email, String token){
        User theUser = new User(email);
        if (logicService.deleteUser(theUser, token)){
            return "Usuario eliminado con exito! : Email: "+ email;
        }else{
            return "Error al eliminar usuario!";
        }
    }

    //------------------------------------------------------------Notas------------------------------------------------------------

    @PostMapping("/addNota") //check
    public String addNota(int userID, String textoNota, String tituloNota, String token){
        Notes theNote = new Notes(userID, textoNota, tituloNota);
        if (logicService.addNote(theNote, token)){
            return "La nota fue agregada con exito";
        }else{
            return "Error al agregar nota!";
        }
    }

    @GetMapping("/getAllNotas") //check
    public List<Notes> getallNotes(String token){
        return logicService.getAllNotes(token);
    }

    @GetMapping("/getNotasDeUsuario") //check
    public List<Notes> getNotesForUser(String userEmail, String token){
        return logicService.getNotesForUser(userEmail, token);
    }
    
    @PutMapping("/updateNota") //check
    public String updateNote(Integer noteID, String texto, String titulo, String token){
        Notes theNote = new Notes();
        theNote.setNoteID(noteID);
        theNote.setNoteText(texto);
        theNote.setNoteTitle(titulo);
        if (logicService.updateNote(theNote, token)){
            return "Nota actualizada con exito!";
        }else{
            return "Actualizacion de Nota fallida!";
        }
    }

    @DeleteMapping("/deleteNota") //check
    public String deleteNote(Integer noteID, String token){
        Notes theNote = new Notes();
        theNote.setNoteID(noteID);
        if (logicService.deleteNote(theNote, token)){
            return "Nota eliminada con exito!";
        }else{
            return "Error al eliminar la nota!";
        }
    }
    //------------------------------------------------------------Progra web------------------------------------------------------------
    @PutMapping("/setPasswordEnabled") //check
    public String setPasswordEnabled(Boolean enabled, Integer noteID, String password, String token){
        Notes theNote = new Notes();
        theNote.setNotePasswordEnabled(enabled);
        theNote.setNoteID(noteID);
        theNote.setNotePassword(password);
        if (logicService.setPasswordEnabled(theNote, token)){
            if (enabled){
                return "Password habilitado con exito!";
            }else{
                return "Password removido con exito!";
            }
        }else{
            return "Error al habilitar password";
        }
    }

    @PutMapping("/setCategoriaNota") //check
    public String setNoteCategory(Integer noteID, String categoryName, String token){
        Notes theNote = new Notes();
        theNote.setNoteID(noteID);
        theNote.setNoteCategory(categoryName);
        if (logicService.setNoteCategory(theNote, token)){
            return "Categoria actualizada con exito!";
        }else{
            return "Error al actualizar la categoria";
        }
    }

    @PutMapping("/setColorBackground")
    public String setBackgroundColor(Integer noteID, String hexCodigoColor, String token){
        Notes theNote = new Notes();
        theNote.setNoteID(noteID);
        theNote.setNoteBackgroundColor("#"+hexCodigoColor);
        if (logicService.setBackgroundColor(theNote, token)){
            return "Color de background actualizado con exito!";
        }else{
            return "Error al actualizar el color de background";
        }
    }

    @PutMapping("/setEmailsParaNotaCompartida")
    public String setNoteSharedEmails(Integer noteID, String accion, String email, String token){
        Notes theNote = new Notes();
        theNote.setNoteID(noteID);
        if (logicService.setNoteSharedEmails(accion, email, theNote, token)){
            return "Emails compartidos actualizados con exito!";
        }else{
            return "Error al actualizar emails compartidos";
        }
    }

    @PostMapping("/login")
    public User loginUser(String email, String password) {
        User tmpUser = logicService.login(email, password);
        return tmpUser;
    }
}
