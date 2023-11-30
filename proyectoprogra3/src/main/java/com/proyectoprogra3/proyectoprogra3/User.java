package com.proyectoprogra3.proyectoprogra3;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class User {
    //************************************************************Variables************************************************************
    private int usuarioID;
    private String nombre;
    private String apellido;
    private String password;
    private String email;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private boolean activeStatus;
    private String jwtToken;

    private List<Notes> listaNotas = new ArrayList<Notes>();

    //************************************************************Contructors************************************************************
    public User() {
    }
    
    // public User(String email, Integer usuarioID){
    //     this.email = email;
    //     this.usuarioID = usuarioID;
    // }

    public User(String email){
        this.email = email.toLowerCase().strip();
    }

    public User(int usuarioID, String email, String password){
        this.usuarioID = usuarioID;
        this.email = email.toLowerCase().strip();
        this.password = password;
        this.jwtToken = generateJsonWebToken(email, password);
    }

    public User(int usuarioID, String nombre, String apellido, String password, String email){
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.email = email.toLowerCase().strip();
    }

    public User(int usuarioID, String nombre, String apellido, String password, String email, LocalDate createdDate, LocalDate modifiedDate, boolean activeStatus) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.email = email.toLowerCase().strip();
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.activeStatus = activeStatus;
    }

    //************************************************************Setters and Getters************************************************************
    //##################################################################Setters##################################################################
    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setListaNotas(List<Notes> listaNotas) {
        this.listaNotas = listaNotas;
    }

    //##################################################################Getters##################################################################
    public int getUsuarioID() {
        return usuarioID;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPassword() {
        return password;
    }

    public String getJWTToken() {
        return jwtToken;
    }
    
    public String getEmail() {
        return email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public boolean getActiveStatus() {
        return activeStatus;
    }

    public List<Notes> getListaNotas() {
        return listaNotas;
    }
    
    //************************************************************Class Funtions************************************************************

    public void deleteNota(int index){
        this.listaNotas.remove(index);
    }

    public List<Notes> searchNotas(String textToSearch){
        String texto;
        String titulo;
        List<Notes> notasEncontradas = new ArrayList<Notes>();

        for(Notes NotasIterador : listaNotas){
            texto = NotasIterador.getNoteText().toLowerCase();
            titulo = NotasIterador.getNoteTitle().toLowerCase();
            
            if(texto.contains(textToSearch) || titulo.contains(textToSearch)){
                notasEncontradas.add(NotasIterador);
            }
        }

        return notasEncontradas;
    }

    private String generateJsonWebToken(String email, String password) {
        String elSecreto = "msX8ssd7C&2N5XXUuE#JXx*j5";
        try {
            // Concatenate username, password, and secret key
            String hashedString = email + elSecreto + password;
            // Use SHA-256 algorithm to hash the data
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(hashedString.getBytes(StandardCharsets.UTF_8));

            // Convert hashed bytes to a hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            System.out.println("jtw = " + hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Handle the exception appropriately in a real-world scenario
        }
    }
}
