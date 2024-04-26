package com.learning.contactweb;


import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class contactController {
    private ContactRepository contactRepository;

    @Autowired
    public contactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Contact> contacts(Model model) {
        return contactRepository.findAll();
    }


    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String>
    addContact(@RequestBody Contact contact
    )
    {
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String number  = contact.getNumber();


        if(number==null || lastName==null || firstName==null) {
        return  ResponseEntity.badRequest().body("Invalid parameters");
        }
        if (firstName.isEmpty() || lastName.isEmpty() || number.isEmpty()) {
            return ResponseEntity.badRequest().body("First name, last name, and number are required.");
        }

        if(contactRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            return ResponseEntity.badRequest().body("Full Name, last name already exists.");
        }

        if(contactRepository.existsByNumber(number)) {
            return ResponseEntity.badRequest().body("Contact number already exists.");
        }

        Contact newContact = new Contact();

        newContact.setFirstName(firstName);
        newContact.setLastName(lastName);
        newContact.setNumber(number);
        contactRepository.save(newContact);
        return ResponseEntity.ok().body("Contact added successfully.");
    }


    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteContact(@PathVariable int id) {
    contactRepository.deleteById(id);
    return "Deleted contact with id " + id;
    }

}
