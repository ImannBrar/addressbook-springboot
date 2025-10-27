package ca.carleton.s4806.web;

import ca.carleton.s4806.AddressBook;
import ca.carleton.s4806.AddressBookRepository;
import ca.carleton.s4806.BuddyInfo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AddressBookViewController {

    private final AddressBookRepository books;

    public AddressBookViewController(AddressBookRepository books) {
        this.books = books;
    }

    // Landing page: list all books + create form
    @GetMapping({"/", "/addressbooks"})
    public String listAddressBooks(Model model) {
        model.addAttribute("books", books.findAll());
        return "index"; // templates/index.html
    }

    // Create an address book (MVC path, works without JS)
    @PostMapping("/addressbooks")
    public String createAddressBook() {
        AddressBook saved = books.save(new AddressBook());
        return "redirect:/addressbooks/" + saved.getId();
    }

    // Show one book + add-buddy form
    @GetMapping("/addressbooks/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        var book = books.findById(id).orElse(null);
        model.addAttribute("book", book);
        model.addAttribute("exists", book != null);
        model.addAttribute("buddyForm", new BuddyInfo());
        return "addressbook"; // templates/addressbook.html
    }

    // Add a buddy (MVC form submits here; keeps Part 1 working w/out JS)
    @PostMapping("/addressbooks/{id}/buddies")
    public String addBuddy(@PathVariable Long id, @Valid @ModelAttribute("buddyForm") BuddyInfo form) {
        var book = books.findById(id).orElse(null);
        if (book == null) return "redirect:/addressbooks"; // fallback
        book.addBuddy(form);
        books.save(book);
        return "redirect:/addressbooks/" + id;
    }
}
