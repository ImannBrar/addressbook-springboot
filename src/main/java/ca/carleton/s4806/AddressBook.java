package ca.carleton.s4806;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // EAGER for simple lab JSON/Thymeleaf; fine here
    @OneToMany(
            mappedBy = "addressBook",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private final List<BuddyInfo> buddies = new ArrayList<>();


    public AddressBook() {}


    public Long getId() { return id; }

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            buddies.add(buddy);
            buddy.setAddressBook(this);
        }
    }

    public boolean removeBuddy(BuddyInfo buddy) {
        if (buddy == null) return false;
        buddy.setAddressBook(null);
        return buddies.remove(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return Collections.unmodifiableList(buddies);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddressBook:\n");
        for (BuddyInfo b : buddies) {
            sb.append(" - ").append(b).append('\n');
        }
        return sb.toString();
    }
}
