package ca.carleton.s4806;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate rest;

    @Test
    void createListAddBuddyAndFetch() {
        // 1) Create a new address book
        ResponseEntity<String> created =
                rest.postForEntity(url("/api/addressbooks"), null, String.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Extract the id from the Location header: /api/addressbooks/{id}
        URI location = created.getHeaders().getLocation();
        assertThat(location).isNotNull();
        String path = location.getPath(); // e.g. /api/addressbooks/3
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        String bookUrl = "/api/addressbooks/" + idStr;

        // 2) List all address books
        ResponseEntity<String> list =
                rest.getForEntity(url("/api/addressbooks"), String.class);
        assertThat(list.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(list.getBody()).contains("\"id\"");

        // 3) Add a buddy to the newly created book
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> body =
                new HttpEntity<>("{\"name\":\"Alice\",\"phone\":\"555-0100\"}", headers);

        ResponseEntity<String> addBuddy =
                rest.postForEntity(url(bookUrl + "/buddies"), body, String.class);
        assertThat(addBuddy.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addBuddy.getBody()).contains("Alice");

        // 4) Fetch the book and verify the buddy is present
        ResponseEntity<String> fetched =
                rest.getForEntity(url(bookUrl), String.class);
        assertThat(fetched.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetched.getBody()).contains("Alice").contains("555-0100");
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
