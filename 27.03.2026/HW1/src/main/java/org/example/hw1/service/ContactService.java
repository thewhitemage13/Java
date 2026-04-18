package org.example.hw1.service;

import org.example.hw1.entity.Contact;
import org.example.hw1.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Контакт з id " + id + " не знайдено"));
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact update(Long id, Contact updatedContact) {
        Contact existing = findById(id);
        existing.setFirstName(updatedContact.getFirstName());
        existing.setLastName(updatedContact.getLastName());
        existing.setPhone(updatedContact.getPhone());
        return contactRepository.save(existing);
    }

    public void delete(Long id) {
        Contact existing = findById(id);
        contactRepository.delete(existing);
    }
}
