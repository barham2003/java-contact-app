package com.learning.contactweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;


@Controller
public class contactController {
    final private ContactRepository contactRepository;

    @Autowired
    public contactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Contact> getContacts() {
        return contactRepository.findAll();
    }


    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<HashMap<String, String>>
    addContact(@RequestBody newContact contact
    ) {
        String firstName = contact.firstName;
        String lastName = contact.lastName;
        String number = contact.phone;

        HashMap<String, String> object = new HashMap<>();

        if (number == null || lastName == null || firstName == null) {
            object.put("message", "Invalid data");
            return ResponseEntity.badRequest().body(object);
        }

        if (firstName.isEmpty() || lastName.isEmpty() || number.isEmpty()) {
            object.put("message", "Please enter all the details");
            return ResponseEntity.badRequest().body(object);
        }

        if (contactRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            object.put("message", "firstName or lastName already exists");
            return ResponseEntity.badRequest().body(object);
        }

        if (contactRepository.existsByNumber(number)) {
            object.put("message", "number already exists");
            return ResponseEntity.badRequest().body(object);
        }

        Contact newContact = new Contact();

        newContact.setFirstName(firstName);
        newContact.setLastName(lastName);
        newContact.setNumber(number);
        contactRepository.save(newContact);
        object.put("message", "Contact added successfully");
        return ResponseEntity.ok().body(object);
    }


    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<HashMap<String, String>> deleteContact(@PathVariable int id) {
        HashMap<String, String> object = new HashMap<>();
        contactRepository.deleteById(id);
        object.put("message", "Contact deleted successfully");
        return ResponseEntity.ok().body(object);
    }


    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<HashMap<String, String>> updateContact(@PathVariable int id, @RequestBody newContact contact) {
        HashMap<String, String> object = new HashMap<>();
        Contact existingContact = contactRepository.findById(id).get();

        if (contact.firstName != null) existingContact.setFirstName(contact.phone);
        else if (contact.lastName != null) existingContact.setLastName(contact.phone);
        else if (contact.phone != null) existingContact.setNumber(contact.phone);
        else {
            object.put("message", "Invalid data");
            return ResponseEntity.badRequest().body(object);
        }


        contactRepository.save(existingContact);
        object.put("message", "Contact updated successfully");
        return ResponseEntity.ok().body(object);
    }


    @RequestMapping(value = "/search-contacts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<HashMap<String, ArrayList<Contact>>> findContact(@RequestParam String term) {
        HashMap<String, ArrayList<Contact>> object = new HashMap<>();

        object.put("contacts", new ArrayList<Contact>());
        if (term == null || term.isEmpty()) {
            return ResponseEntity.badRequest().body(object);
        }

        ArrayList<Contact> foundedContacts = contactRepository.searchContacts(term);

        object.put("contacts", foundedContacts);
        return ResponseEntity.ok().body(object);
    }
}
