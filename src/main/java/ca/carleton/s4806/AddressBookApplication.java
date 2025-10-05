package ca.carleton.s4806;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AddressBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(AddressBookRepository books) {
        return args -> {
            AddressBook book = new AddressBook();
            book.addBuddy(new BuddyInfo("imann", "555-0100"));
            book.addBuddy(new BuddyInfo("brar", "555-0200"));
            book.addBuddy(new BuddyInfo("testingggg", "555-0200"));

            books.save(book);
        };
    }
}
