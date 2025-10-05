package ca.carleton.s4806.web;

import ca.carleton.s4806.AddressBook;
import ca.carleton.s4806.AddressBookRepository;
import ca.carleton.s4806.BuddyInfo;
import ca.carleton.s4806.BuddyInfoRepository;
import ca.carleton.s4806.web.dto.CreateBuddyRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/addressbooks")
public class AddressBookRestController {

    private final AddressBookRepository books;
    private final BuddyInfoRepository buddies;

    public AddressBookRestController(AddressBookRepository books, BuddyInfoRepository buddies) {
        this.books = books;
        this.buddies = buddies;
    }

    @PostMapping
    public ResponseEntity<AddressBook> createAddressBook() {
        AddressBook saved = books.save(new AddressBook());
        return ResponseEntity.created(URI.create("/api/addressbooks/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> getAddressBook(@PathVariable Long id) {
        return books.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Iterable<AddressBook> listAddressBooks() {
        return books.findAll();
    }

    @PostMapping("/{id}/buddies")
    public ResponseEntity<AddressBook> addBuddy(
            @PathVariable Long id,
            @Valid @RequestBody CreateBuddyRequest req) {

        return books.findById(id).map(book -> {
            BuddyInfo b = new BuddyInfo(req.name(), req.phone());
            book.addBuddy(b);
            books.save(book); // cascades to buddies
            return ResponseEntity.ok(book);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/buddies/{buddyId}")
    public ResponseEntity<Void> removeBuddy(@PathVariable Long id, @PathVariable Long buddyId) {
        var bookOpt = books.findById(id);
        if (bookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var book = bookOpt.get();
        var buddyOpt = buddies.findById(buddyId);
        if (buddyOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var buddy = buddyOpt.get();
        if (!book.getBuddies().contains(buddy)) {
            return ResponseEntity.notFound().build();
        }

        book.removeBuddy(buddy);
        books.save(book);
        return ResponseEntity.noContent().build();
    }
}
