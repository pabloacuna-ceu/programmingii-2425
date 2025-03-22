package edu.ceu.programming.exercises.maven;

import java.util.Objects;

/**
 * Represents a contact with a name and a phone number.
 */
public class Contact {

    private String name;
    private String phone;

    public Contact() {
        // Default constructor
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name + " - " + phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contact)) return false;
        Contact other = (Contact) obj;
        return Objects.equals(this.name, other.name);
    }

}