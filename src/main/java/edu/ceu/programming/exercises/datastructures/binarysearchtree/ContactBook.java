package edu.ceu.programming.exercises.datastructures.binarysearchtree;

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

    // Exercise 2, May 7th
    // Root -> left -> right
    public void preOrderDisplay() {
        if (root != null){
            preOrderDisplayNode(root);
        }
    }

    private void preOrderDisplayNode(ContactNode node) {
        if (node == null){return;}
        System.out.println("Name=" + node.name + "; Phone=" + node.phoneNumber);
        preOrderDisplayNode(node.childLeft);
        preOrderDisplayNode(node.childRight);
    }

    // Exercise 1, May 7th
    // Left -> right -> root
    public void postOrderDisplay() {
        if(root != null){
           postOrderDisplayNode(root);
        }
    }

    private void postOrderDisplayNode(ContactNode node) {
        if (node == null) return;
        postOrderDisplayNode(node.childLeft);
        postOrderDisplayNode(node.childRight);
        System.out.println("Name=" + node.name + "; Phone=" + node.phoneNumber);
    }

    // left -> root -> right
    public void inOrderDisplay() {
        if (root != null) {
            inOrderDisplayNode(root);
        }
    }

    private void inOrderDisplayNode(ContactNode node) {
        if (node == null) return;
        inOrderDisplayNode(node.childLeft);
        System.out.println("Name=" + node.name + "; Phone=" + node.phoneNumber);
        inOrderDisplayNode(node.childRight);
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

        //System.out.println(book.search("Oscar"));

        System.out.println("InOrder Display");
        book.inOrderDisplay();
        System.out.println("PostOrder Display");
        book.postOrderDisplay();
        System.out.println("PreOrder Display");
        book.preOrderDisplay();

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