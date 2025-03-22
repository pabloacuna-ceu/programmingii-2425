package edu.ceu.programming.exercises.maven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Class responsible for managing the contact book.
 */
public class ContactBook {

    private String fileName = "contacts.txt";
    private ArrayList<Contact> contacts = new ArrayList<>();

    /**
     * Generates a string representation of the contact book.
     * @return the string representation.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Contact contact : contacts) {
            sb.append(contact).append("\n");
        }
        return sb.toString();
    }

    /**
     * Loads the contact book from the file "contacts.txt".
     */
    public ContactBook() {
        try {
            File file = new File(fileName);
            file.createNewFile(); // Creates the file if it doesn't exist

            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                Contact contact = new Contact();
                contact.setName(sc.nextLine());
                contact.setPhone(sc.nextLine());
                contacts.add(contact);
            }
            sc.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Writes the current list of contacts back to the file.
     */
    private void writeContactsToFile() {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "\n");
                writer.write(contact.getPhone() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Adds a new contact to the contact book.
     * @param name The contact's name.
     * @param phone The contact's phone number.
     */
    public void addContact(String name, String phone) {
        Contact contact = new Contact(name, phone);
        contacts.add(contact);
        writeContactsToFile();
    }

    /**
     * Deletes the contact whose name matches the provided one.
     * @param name The name of the contact to delete.
     */
    public void deleteContact(String name) {
        Contact contact = new Contact();
        contact.setName(name);
        contacts.remove(contact);
        writeContactsToFile();
    }

    /**
     * Generates a spreadsheet with all contacts.
     */
    public void generateSpreadsheet() {
        final Object[][] data = new Object[contacts.size()][2];
        int i = 0;

        for (Contact contact : contacts) {
            data[i][0] = contact.getName();
            data[i++][1] = contact.getPhone();
        }

        String[] columns = new String[] { "Name", "Phone" };
        TableModel model = new DefaultTableModel(data, columns);

        final File file = new File("output/contacts.ods");
        try {
            SpreadSheet.createEmpty(model).saveAs(file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}