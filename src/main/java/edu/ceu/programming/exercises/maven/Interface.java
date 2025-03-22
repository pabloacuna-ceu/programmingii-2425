package edu.ceu.programming.exercises.maven;

/**
 * Implements a text-based interface for the contact book.
 */
public class Interface {

    /**
     * Starts the interface with parameters.
     * @param args Can be:
     * <ul>
     *   <li><code>add name phone</code> (e.g. <code>add Juan 653421367</code>) to add a contact</li>
     *   <li><code>rm name</code> to remove a contact by name</li>
     *   <li><code>show</code> to display all contacts</li>
     *   <li><code>sheet</code> to generate a spreadsheet</li>
     * </ul>
     */
    public static void main(String[] args) {
        ContactBook contactBook = new ContactBook();

        if (args.length == 0) {
            System.out.println("No arguments provided.");
            return;
        }

        switch (args[0]) {
            case "add":
                if (args.length >= 3) {
                    contactBook.addContact(args[1], args[2]);
                } else {
                    System.out.println("Usage: add <name> <phone>");
                }
                break;

            case "rm":
                if (args.length >= 2) {
                    contactBook.deleteContact(args[1]);
                } else {
                    System.out.println("Usage: rm <name>");
                }
                break;

            case "show":
                System.out.println(contactBook);
                break;

            case "sheet":
                contactBook.generateSpreadsheet();
                System.out.println("Spreadsheet generated at output/contacts.ods");
                break;

            default:
                System.out.println("Invalid option");
                break;
        }
    }
}