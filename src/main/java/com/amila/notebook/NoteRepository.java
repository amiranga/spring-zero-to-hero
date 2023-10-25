package com.amila.notebook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitle(String title);

    // Custom query
    @Query("SELECT b FROM Note b WHERE b.createdAt > :date")
    List<Note> findByCreatedDateAfter(@Param("date") LocalDate date);

}