package com.amila.notebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteMapper noteMapper;

    @GetMapping
    public List<NoteDTO> findAll() {
        return noteService.findAll().stream().map(noteMapper::noteToNoteDto).toList();
    }

    @GetMapping("/{id}")
    public NoteDTO findById(@PathVariable Long id) {
        return noteMapper.noteToNoteDto(noteService.findById(id).orElse(null));
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public NoteDTO create(@RequestBody NoteDTO note) {
        return noteMapper.noteToNoteDto(noteService.save(noteMapper.noteToNoteDto(note)));
    }

    @PutMapping
    public NoteDTO update(@RequestBody NoteDTO note) {
        return noteMapper.noteToNoteDto(noteService.save(noteMapper.noteToNoteDto(note)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        noteService.deleteById(id);
    }

    @GetMapping("/find/title/{title}")
    public List<NoteDTO> findByTitle(@PathVariable String title) {
        return noteService.findByTitle(title).stream().map(noteMapper::noteToNoteDto).collect(Collectors.toList());
    }

    @GetMapping("/find/date-after/{date}")
    public List<NoteDTO> findByCreatedDateAfter(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return noteService.findByPCreatedDateAfter(date).stream().map(noteMapper::noteToNoteDto).collect(Collectors.toList());
    }

}

