package ca.carleton.s4806.web;

import ca.carleton.s4806.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AddressBookViewController {

    private final AddressBookRepository books;

    public AddressBookViewController(AddressBookRepository books) {
        this.books = books;
    }

    @GetMapping("/addressbooks/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        var book = books.findById(id).orElse(null);
        model.addAttribute("book", book);
        model.addAttribute("exists", book != null);
        return "addressbook"; // templates/addressbook.html
    }
}
