package io.leoindahause.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int email;
    private int password;

    public Person() {
        this.name = name;
        this.email = email;
        this.password = password;
        
    }

    public String getName() {
        return name;
    }

    public int getEmail() {
        return email;
    }

    public int getPassword() {
        return password;
    }
}
