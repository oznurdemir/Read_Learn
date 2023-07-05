package com.oznurdemir.readlearn;

public class User {
    private String mail;
    //private String password;

    public User() {
    }

   /* public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }*/
    public User(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

   /* public String getPassword() {
        return password;
    }*/

   /* public void setPassword(String password) {
        this.password = password;
    }*/
}
