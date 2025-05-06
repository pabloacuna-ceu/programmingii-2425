package edu.ceu.programming.classes.datastructures;

public class ContactBook {

    ContactNode root = null;

    public ContactBook() {}

    public void addContact(String name, int phoneNumber) {
        ContactNode contact = new ContactNode(name, phoneNumber);
        root = insert(root, contact);

    }

    private ContactNode insert(ContactNode node, ContactNode contact) {
        if (node != null) {
            if (contact.name.compareTo(node.name) <= 0) {
                node.childLeft = insert(node.childLeft, contact);
            } else {
                node.childRight = insert(node.childRight, contact);
            }
        } else {
            return contact;
        }
        return node;
    }

    public String search(String name) {
        ContactNode result = searchNode(root, name);
        if (result != null) {
            return result.name + ";" + result.phoneNumber;
        } else {
            return "Contact not found";
        }
    }

    private ContactNode searchNode(ContactNode node, String name) {
        if (node == null || node.name.equals(name)) {
            return node;
        }

        if (name.compareTo(node.name) < 0) {
            return searchNode(node.childLeft, name);
        } else {
            return searchNode(node.childRight, name);
        }
    }

    public void inOrderDisplay() {
        if (root != null) {
            displayNode(root);
        }
    }

    private void displayNode(ContactNode node) {
        if (node.childLeft != null) {
            displayNode(node.childLeft);
        }
        System.out.println("Name=" + node.name + "; Phone=" + node.phoneNumber);
        if (node.childRight != null) {
            displayNode(node.childRight);
        }
    }

    public static void main(String [] args) {
        ContactBook book = new ContactBook();
        book.addContact("Peter", 123123123);
        book.addContact("Mike", 223123123);
        book.addContact("Ana", 323123123);
        book.addContact("Oscar", 423123123);
        book.addContact("Boris", 523123123);
        book.addContact("Ruben", 623123123);
        book.addContact("Rodrigo", 723123123);
        book.addContact("Xavier", 823123123);
        book.addContact("Zoe", 923123123);

        System.out.println(book.search("Oscar"));

        book.inOrderDisplay();

    }

}

class ContactNode {
    String name;
    int phoneNumber;
    ContactNode childLeft;
    ContactNode childRight;

    public ContactNode(String name, int phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.childLeft = this.childRight = null;
    }

}