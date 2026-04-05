package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ContactDAO contactDAO = new ContactDAO();

    public static void main(String[] args) {
        DBConnection.initializeDatabase();

        int choice;
        do {
            showMenu();
            System.out.print("Оберіть пункт меню: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Введіть число!");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addContact();
                case 2 -> updateContact();
                case 3 -> deleteContact();
                case 4 -> showAllContacts();
                case 0 -> System.out.println("Вихід з програми...");
                default -> System.out.println("Невірний пункт меню.");
            }

        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n===== Список контактів =====");
        System.out.println("1. Додати контакт");
        System.out.println("2. Оновити контакт");
        System.out.println("3. Видалити контакт");
        System.out.println("4. Переглянути всі контакти");
        System.out.println("0. Вихід");
    }

    private static void addContact() {
        System.out.print("Введіть ім'я: ");
        String firstName = scanner.nextLine();

        System.out.print("Введіть прізвище: ");
        String lastName = scanner.nextLine();

        System.out.print("Введіть номер телефону: ");
        String phone = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, phone);
        contactDAO.addContact(contact);
    }

    private static void updateContact() {
        System.out.print("Введіть ID контакту для оновлення: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Нове ім'я: ");
        String firstName = scanner.nextLine();

        System.out.print("Нове прізвище: ");
        String lastName = scanner.nextLine();

        System.out.print("Новий номер телефону: ");
        String phone = scanner.nextLine();

        Contact contact = new Contact(id, firstName, lastName, phone);
        contactDAO.updateContact(contact);
    }

    private static void deleteContact() {
        System.out.print("Введіть ID контакту для видалення: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        contactDAO.deleteContact(id);
    }

    private static void showAllContacts() {
        List<Contact> contacts = contactDAO.getAllContacts();

        if (contacts.isEmpty()) {
            System.out.println("Список контактів порожній.");
            return;
        }

        System.out.println("\n===== Усі контакти =====");
        for (Contact contact : contacts) {
            System.out.println("ID: " + contact.getId()
                    + ", Ім'я: " + contact.getFirstName()
                    + ", Прізвище: " + contact.getLastName()
                    + ", Телефон: " + contact.getPhone());
        }
    }
}