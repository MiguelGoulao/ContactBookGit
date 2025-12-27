package main;

import contactBook.Contact;
import contactBook.ContactBook;

import java.util.Objects;
import java.util.Scanner;


public class Main {
    // Constants that define commands
    public static final String ADD_CONTACT = "AC";
    public static final String REMOVE_CONTACT = "RC";
    public static final String GET_PHONE = "GP";
    public static final String GET_BY_PHONE = "GN";
    public static final String GET_EMAIL = "GE";
    public static final String SET_PHONE = "SP";
    public static final String SET_EMAIL = "SE";
    public static final String LIST_CONTACTS = "LC";
    public static final String HAS_DUPLICATES = "EP";
    public static final String QUIT = "Q";

    // Constants that define messages for the user
    public static final String CONTACT_EXISTS = "contactBook.Contact already exists.";
    public static final String NAME_NOT_EXIST = "contactBook.Contact does not exist.";
    public static final String CONTACT_ADDED = "contactBook.Contact added.";
    public static final String CONTACT_REMOVED = "contactBook.Contact removed.";
    public static final String CONTACT_UPDATED = "contactBook.Contact updated.";
    public static final String BOOK_EMPTY = "contactBook.Contact book empty.";
    public static final String PHONE_NOT_EXIST = "Phone number does not exist.";
    public static final String QUIT_MSG = "Goodbye!";
    public static final String COMMAND_ERROR = "Unknown command.";
    public static final String NO_DUPLICATES_EXIST = "All contacts have different phone numbers.";
    public static final String DUPLICATES_EXIST = "There are contacts that share phone numbers.";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ContactBook cBook = new ContactBook();
        String comm = getCommand(in);

        while (!QUIT.equals(comm)) {
            switch (comm) {
                case ADD_CONTACT ->  addContact(in, cBook);
                case REMOVE_CONTACT -> deleteContact(in, cBook);
                case GET_PHONE -> getPhone(in, cBook);
                case GET_EMAIL -> getEmail(in, cBook);
                case SET_PHONE -> setPhone(in, cBook);
                case SET_EMAIL -> setEmail(in, cBook);
                case LIST_CONTACTS -> listAllContacts(cBook);
                case GET_BY_PHONE -> getByPhone(in, cBook);
                case HAS_DUPLICATES -> hasDuplicates(cBook);
                default -> System.out.println(COMMAND_ERROR);
            }
            System.out.println();
            comm = getCommand(in);
        }
        System.out.println(QUIT_MSG);
        System.out.println();
        in.close();
    }

    private static String getCommand(Scanner in) {
        String input;

        input = in.nextLine().toUpperCase();
        return input;
    }

    private static void addContact(Scanner in, ContactBook cBook) {
        String name, email;
        int phone;

        name = in.nextLine();
        phone = in.nextInt();
        in.nextLine();
        email = in.nextLine();
        if (!cBook.hasContact(name)) {
            cBook.addContact(name, phone, email);
            System.out.println(CONTACT_ADDED);
        } else System.out.println(CONTACT_EXISTS);
    }

    private static void getByPhone(Scanner in, ContactBook cBook) {
        int phone;

        phone = in.nextInt();
        in.nextLine();
        System.out.println(Objects.requireNonNullElse(cBook.getByPhone(phone), PHONE_NOT_EXIST));
    }

    private static void hasDuplicates(ContactBook cBook) {
        String output = cBook.hasDuplicatedPhone()
                ? DUPLICATES_EXIST
                : NO_DUPLICATES_EXIST;
        System.out.println(output);
    }

    private static void deleteContact(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.deleteContact(name);
            System.out.println(CONTACT_REMOVED);
        } else System.out.println(NAME_NOT_EXIST);
    }

    private static void getPhone(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getPhone(name));
        } else System.out.println(NAME_NOT_EXIST);
    }

    private static void getEmail(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getEmail(name));
        } else System.out.println(NAME_NOT_EXIST);
    }

    private static void setPhone(Scanner in, ContactBook cBook) {
        String name;
        int phone;
        name = in.nextLine();
        phone = in.nextInt();
        in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.setPhone(name, phone);
            System.out.println(CONTACT_UPDATED);
        } else System.out.println(NAME_NOT_EXIST);
    }

    private static void setEmail(Scanner in, ContactBook cBook) {
        String name;
        String email;
        name = in.nextLine();
        email = in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.setEmail(name, email);
            System.out.println(CONTACT_UPDATED);
        } else System.out.println(NAME_NOT_EXIST);
    }

    private static void listAllContacts(ContactBook cBook) {
        if (cBook.getNumberOfContacts() != 0) {
            cBook.initializeIterator();
            while (cBook.hasNext()) {
                Contact c = cBook.next();
                System.out.println(c.getName() + "; " + c.getEmail() + "; " + c.getPhone());
            }
        } else System.out.println(BOOK_EMPTY);
    }
}
