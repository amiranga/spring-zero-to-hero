package com.amila;

import com.amila.notebook.Note;
import com.amila.notebook.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class NoteControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASEURI;

    @Autowired
    NoteRepository bookRepository;

    // static, all tests share this postgres container
    @Container
    @ServiceConnection
    static MySQLContainer<?> postgres = new MySQLContainer<>(
            "mysql:8.0-debian"
    );

//    @BeforeEach
//    void testSetUp() {
//
//        BASEURI = "http://localhost:" + port;
//
//        bookRepository.deleteAll();
//
//        Note b1 = new Note("Book A",
//                BigDecimal.valueOf(9.99),
//                LocalDate.of(2023, 8, 31));
//        Note b2 = new Note("Book B",
//                BigDecimal.valueOf(19.99),
//                LocalDate.of(2023, 7, 31));
//        Note b3 = new Note("Book C",
//                BigDecimal.valueOf(29.99),
//                LocalDate.of(2023, 6, 10));
//        Note b4 = new Note("Book D",
//                BigDecimal.valueOf(39.99),
//                LocalDate.of(2023, 5, 5));
//
//        bookRepository.saveAll(List.of(b1, b2, b3, b4));
//    }

//    @Test
//    void testFindAll() {
//
//        // ResponseEntity<List> response = restTemplate.getForEntity(BASEURI + "/books", List.class);
//
//        // find all books and return List<Book>
//        ParameterizedTypeReference<List<Note>> typeRef = new ParameterizedTypeReference<>() {
//        };
//        ResponseEntity<List<Note>> response = restTemplate.exchange(
//                BASEURI + "/books",
//                HttpMethod.GET,
//                null,
//                typeRef
//        );
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(4, response.getBody().size());
//
//    }
//
//    @Test
//    void testFindByTitle() {
//        String title = "Book C";
//        ParameterizedTypeReference<List<Note>> typeRef = new ParameterizedTypeReference<>() {
//        };
//
//        // find Book C
//        ResponseEntity<List<Note>> response = restTemplate.exchange(
//                BASEURI + "/books/find/title/" + title,
//                HttpMethod.GET,
//                null,
//                typeRef
//        );
//
//        // test response code
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        List<Note> list = response.getBody();
//        assert list != null;
//
//        assertEquals(1, list.size());
//
//        // Test Book C details
//        Note note = list.get(0);
//        assertEquals("Book C", note.getTitle());
//        assertEquals(BigDecimal.valueOf(29.99), note.getPrice());
//        assertEquals(LocalDate.of(2023, 6, 10), note.getPublishDate());
//
//    }
//
//    @Test
//    void testFindByPublishedDateAfter() {
//
//        String date = "2023-07-01";
//        ParameterizedTypeReference<List<Note>> typeRef = new ParameterizedTypeReference<>() {
//        };
//
//        // find Book C
//        ResponseEntity<List<Note>> response = restTemplate.exchange(
//                BASEURI + "/books/find/date-after/" + date,
//                HttpMethod.GET,
//                null,
//                typeRef
//        );
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        // test list of objects
//        List<Note> result = response.getBody();
//        assert result != null;
//
//        assertEquals(2, result.size());
//
//        assertThat(result).extracting(Note::getTitle)
//                .containsExactlyInAnyOrder(
//                        "Book A", "Book B");
//        assertThat(result).extracting(Note::getPrice)
//                .containsExactlyInAnyOrder(
//                        new BigDecimal("9.99"), new BigDecimal("19.99"));
//        assertThat(result).extracting(Note::getPublishDate)
//                .containsExactlyInAnyOrder
//                        (LocalDate.parse("2023-08-31"), LocalDate.parse("2023-07-31"));
//    }
//
//    @Test
//    public void testDeleteById() {
//
//        List<Note> list = bookRepository.findByTitle("Book A");
//        Note noteA = list.get(0);
//
//        // get Book A id
//        Long id = noteA.getId();
//
//        // delete by id
//        ResponseEntity<Void> response = restTemplate.exchange(
//                BASEURI + "/books/" + id,
//                HttpMethod.DELETE,
//                null,
//                Void.class
//        );
//
//        // test 204
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//        // find Book A again, ensure no result
//        List<Note> listAgain = bookRepository.findByTitle("Book A");
//        assertEquals(0, listAgain.size());
//
//    }
//
//    @Test
//    public void testCreate() {
//
//        // Create a new Book E
//        Note newNote = new Note("Book E", new BigDecimal("9.99"), LocalDate.parse("2023-09-14"));
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        HttpEntity<Note> request = new HttpEntity<>(newNote, headers);
//
//        // test POST save
//        ResponseEntity<Note> responseEntity =
//                restTemplate.postForEntity(BASEURI + "/books", request, Note.class);
//
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//
//        // find Book E
//        List<Note> list = bookRepository.findByTitle("Book E");
//
//        // Test Book E details
//        Note note = list.get(0);
//        assertEquals("Book E", note.getTitle());
//        assertEquals(BigDecimal.valueOf(9.99), note.getPrice());
//        assertEquals(LocalDate.of(2023, 9, 14), note.getPublishDate());
//
//    }
//
//    /**
//     * Book b4 = new Book("Book D",
//     * BigDecimal.valueOf(39.99),
//     * LocalDate.of(2023, 5, 5));
//     */
//    @Test
//    public void testUpdate() {
//        // Find Book D
//        Note noteD = bookRepository.findByTitle("Book D").get(0);
//        Long id = noteD.getId();
//
//        // Update the book details
//        noteD.setTitle("Book DDD");
//        noteD.setPrice(new BigDecimal("199.99"));
//        noteD.setPublishDate(LocalDate.of(2024, 1, 31));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//
//        // put the updated book in HttpEntity
//        HttpEntity<Note> request = new HttpEntity<>(noteD, headers);
//
//        // Perform the PUT request to update the book
//        ResponseEntity<Note> responseEntity = restTemplate.exchange(
//                "http://localhost:" + port + "/books",
//                HttpMethod.PUT,
//                request,
//                Note.class
//        );
//
//        // ensure OK
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        // verify the updated book
//        Note updatedNote = bookRepository.findById(id).orElseThrow();
//
//        assertEquals(id, updatedNote.getId());
//        assertEquals("Book DDD", updatedNote.getTitle());
//        assertEquals(BigDecimal.valueOf(199.99), updatedNote.getPrice());
//        assertEquals(LocalDate.of(2024, 1, 31), updatedNote.getPublishDate());
//
//    }

}