package com.amila.notebook;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping
    NoteDTO noteToNoteDto(Note note);

    @Mapping
    Note noteToNoteDto(NoteDTO noteDTO);
}
