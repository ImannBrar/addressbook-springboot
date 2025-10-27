package ca.carleton.s4806;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressbook_id")
    @JsonBackReference
    private AddressBook addressBook;

    public BuddyInfo() {
    }

    public BuddyInfo(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public String toString() {
        String base = name + " (" + phone + ")";
        return address == null || address.isBlank() ? base : base + " @ " + address;
    }
}
