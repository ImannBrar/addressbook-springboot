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

            BuddyInfo b1 = new BuddyInfo("Imann", "555-0100");
            b1.setAddress("Nicol");

            BuddyInfo b2 = new BuddyInfo("brar", "555-0200");
            b2.setAddress("carleton uc");

            BuddyInfo b3 = new BuddyInfo("testing", "555-0300");
            b3.setAddress("6ix");

            book.addBuddy(b1);
            book.addBuddy(b2);
            book.addBuddy(b3);

            books.save(book);
        };
    }
}
