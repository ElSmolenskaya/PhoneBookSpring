package ru.academits.phonebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phoneBook/rpc/api/v1")
public class PhoneBookController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactService contactService;

    public PhoneBookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        logger.info("called method getAllContacts");
        return contactService.getAllContacts();
    }

    @RequestMapping(value = "getContacts", method = RequestMethod.POST)
    @ResponseBody
    public List<Contact> getContacts(@RequestBody(required = false) String term) {
        logger.info("called method getContacts");
        return contactService.getContacts(term);
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody Contact contact) {
        return contactService.addContact(contact);
    }

    @RequestMapping(value = "deleteContacts", method = RequestMethod.POST)
    @ResponseBody
    public String deleteContacts(@RequestBody String idsToDeleteInString) {
        int[] ids = null;

        if (idsToDeleteInString != null && !idsToDeleteInString.isEmpty()) {
            ids = Arrays.stream(idsToDeleteInString.split(",")).mapToInt(Integer::parseInt).toArray();
        }

        return contactService.deleteContacts(ids);
    }
}