package dev.fmesnata.blocnote.service;

import dev.fmesnata.blocnote.Note;
import org.springframework.stereotype.Service;

@Service
public class BlocNoteServiceImpl implements BlocNoteService {
    private Note note = new Note("");
    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public Note updateNote(Note newNote) {
        note = newNote;
        return note;
    }
}
