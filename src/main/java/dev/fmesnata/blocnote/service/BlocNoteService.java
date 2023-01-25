package dev.fmesnata.blocnote.service;

import dev.fmesnata.blocnote.Note;

public interface BlocNoteService {
    Note getNote();
    Note updateNote(Note note);
}
