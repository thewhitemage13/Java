package org.example.hw1.controller;


import org.example.hw1.entity.Contact;
import org.example.hw1.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contacts")
public class ContactViewController {

    private final ContactService contactService;

    public ContactViewController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String showAllContacts(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "contacts";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("formTitle", "Додати контакт");
        return "contact-form";
    }

    @PostMapping
    public String createContact(@ModelAttribute Contact contact) {
        contactService.save(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("contact", contactService.findById(id));
        model.addAttribute("formTitle", "Оновити контакт");
        return "contact-form";
    }

    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute Contact contact) {
        contactService.update(id, contact);
        return "redirect:/contacts";
    }

    @PostMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.delete(id);
        return "redirect:/contacts";
    }
}
