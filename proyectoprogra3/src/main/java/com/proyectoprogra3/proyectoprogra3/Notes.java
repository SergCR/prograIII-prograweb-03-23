package com.proyectoprogra3.proyectoprogra3;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Notes {
    //************************************************************Variables************************************************************
    private Integer noteID;
    private Integer userID;
    private String noteText; //El texto en si que lleva la nota
    private String noteTitle; //Titulo de la nota
    private String noteBackgroundColor; //Variable para almacenar algun color para usar como fondo en la nota
    private Boolean notePasswordEnabled; //Variable para almacenar si la nota requiere contraseña para ser abierta
    private String notePassword; //contraseña de la nota
    private String noteCategory; //Categoria customizable por el usuario para las notas
    private List<User> noteSharedWithListOfUsers; //Lista de usuarios con acceso a la nota especifica
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    //************************************************************Contructors************************************************************
    public Notes(){
        this.noteSharedWithListOfUsers = new ArrayList<>();
    }

    public Notes(int userID, String noteText, String noteTitle){
        this.noteID = 0;
        this.userID = userID;
        this.noteText = noteText;
        this.noteTitle = noteTitle;
        this.noteBackgroundColor = "#FFFFFF";
        this.notePasswordEnabled = false;
        this.notePassword = "";
        this.noteCategory = "";
        this.noteSharedWithListOfUsers = new ArrayList<>();
    }

    public Notes(Integer noteID, Integer userID, String noteText, String noteTitle, String noteBackgroundColor, Boolean notePasswordEnabled, String notePassword, String noteCategory, List<User> noteSharedWithListOfUsers, LocalDate createdDate, LocalDate modifiedDate) {
        this.noteID = noteID;
        this.userID = userID;
        this.noteText = noteText;
        this.noteTitle = noteTitle;
        this.noteBackgroundColor = noteBackgroundColor;
        this.notePasswordEnabled = notePasswordEnabled;
        this.notePassword = notePassword;
        this.noteCategory = noteCategory;
        this.noteSharedWithListOfUsers = noteSharedWithListOfUsers;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    //************************************************************Setters and Getters************************************************************
    //##################################################################Setters##################################################################

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setNoteText(String noteText){
        this.noteText = noteText;
    }

    public void setNoteBackgroundColor(String noteBackgroundColor){
        this.noteBackgroundColor = noteBackgroundColor;
    }
    
    public void setNoteTitle(String noteTitle){
        this.noteTitle = noteTitle;
    }

    public void setNotePasswordEnabled(Boolean notePasswordEnabled){
        this.notePasswordEnabled = notePasswordEnabled;
    }

    public void setNotePassword(String notePassword){
        this.notePassword = notePassword;
    }

    public void setNoteCategory(String noteCategory){
        this.noteCategory = noteCategory;
    }

    public void setNoteSharedWithListOfUsers(List<User> noteSharedWithListOfUsers){
        this.noteSharedWithListOfUsers = noteSharedWithListOfUsers;
    }
    //##################################################################Getters##################################################################
    
    public Integer getNoteID() {
        return noteID;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public String getNoteText(){
        return noteText;
    }

    public String getNoteBackgroundColor(){
        return noteBackgroundColor;
    }
    
    public String getNoteTitle(){
        return noteTitle;
    }

    public Boolean getNotePasswordEnabled(){
        return notePasswordEnabled;
    }

    public String getNotePassword(){
        return notePassword;
    }

    public String getNoteCategory(){
        return noteCategory;
    }

    public List<User> getNoteSharedWithListOfUsers(){
        return noteSharedWithListOfUsers;
    }

    public String getNoteSharedWithStringOfUsers(){
        String stringOfUsers = "";
        for (User userIterator : noteSharedWithListOfUsers){
            stringOfUsers = stringOfUsers +","+userIterator.getEmail().toLowerCase();
        }
        return stringOfUsers;
    }

    //************************************************************Class Funtions************************************************************

    public void addSharedUser(User elUser){
        this.noteSharedWithListOfUsers.add(elUser);
    }
}
