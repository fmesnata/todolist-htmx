package dev.fmesnata.blocnote;

import dev.fmesnata.blocnote.service.BlocNoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlocNoteController {

    private final BlocNoteService blocNoteService;

    public BlocNoteController(BlocNoteService blocNoteService) {
        this.blocNoteService = blocNoteService;
    }

    @GetMapping("/blocnote")
    public ModelAndView blocNotePage() {
        Note note = blocNoteService.getNote();
        ModelAndView modelAndView = new ModelAndView("blocnote");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @GetMapping(path = "/blocnote", headers = "HX-Request")
    public ModelAndView blocNotePageFragment() {
        Note note = blocNoteService.getNote();
        ModelAndView modelAndView = new ModelAndView("fragments/blocnote-fragment");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @PostMapping("/blocnote")
    public ModelAndView updateNote(String newNote) {
        Note note = new Note(newNote);
        Note noteUpdated = blocNoteService.updateNote(note);
        ModelAndView modelAndView = new ModelAndView("blocnote");
        modelAndView.addObject("note", note);
        return modelAndView;
    }
}
