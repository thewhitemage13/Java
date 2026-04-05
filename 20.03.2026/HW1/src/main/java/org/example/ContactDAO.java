package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public void addContact(Contact contact) {
        String sql = "INSERT INTO contacts(first_name, last_name, phone) VALUES(?, ?, ?)";

        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setString(3, contact.getPhone());

            preparedStatement.executeUpdate();
            System.out.println("Контакт успішно додано.");
        } catch (SQLException e) {
            System.out.println("Помилка додавання контакту: " + e.getMessage());
        }
    }

    public void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET first_name = ?, last_name = ?, phone = ? WHERE id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.setInt(4, contact.getId());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Контакт успішно оновлено.");
            } else {
                System.out.println("Контакт з таким ID не знайдено.");
            }
        } catch (SQLException e) {
            System.out.println("Помилка оновлення контакту: " + e.getMessage());
        }
    }

    public void deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Контакт успішно видалено.");
            } else {
                System.out.println("Контакт з таким ID не знайдено.");
            }
        } catch (SQLException e) {
            System.out.println("Помилка видалення контакту: " + e.getMessage());
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, phone FROM contacts";

        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Contact contact = new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone")
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            System.out.println("Помилка отримання контактів: " + e.getMessage());
        }

        return contacts;
    }
}