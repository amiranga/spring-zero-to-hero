package com.amila.notebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private Formatter fooFormatter;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> findByTitle(String title) {
        return noteRepository.findByTitle(title);
    }

    public List<Note> findByPCreatedDateAfter(LocalDate date) {
        return noteRepository.findByCreatedDateAfter(date);
    }
}
