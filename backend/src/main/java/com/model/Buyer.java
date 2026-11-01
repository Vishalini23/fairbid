package com.model;
public class Buyer {
    private String name, email, password, category, docNumber;
    public Buyer(String name, String email, String password, String category, String docNumber) {
        this.name = name; this.email = email; this.password = password; this.category = category; this.docNumber = docNumber;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getCategory() { return category; }
    public String getDocNumber() { return docNumber; }
}