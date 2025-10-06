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

            BuddyInfo b1 = new BuddyInfo("Imann and Parinaaz", "<3");
            b1.setAddress("library");


            book.addBuddy(b1);


            books.save(book);
        };
    }
}
