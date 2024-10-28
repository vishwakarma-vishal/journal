package com.vishal.firstProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vishal.firstProject.entity.JournalEntry;
import com.vishal.firstProject.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    // to create an entry url(post) -> /journal/create
    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry newentry) {
        try {
            journalEntryService.saveEntry(newentry);
            return new ResponseEntity<>(newentry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // to get all the entry url(get) -> /journal
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> all = journalEntryService.getAll();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // get one journal by id url(get) -> /journal/id/2
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getOne(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete one journal by id url(delete) -> /journal/id/2
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId) {
        journalEntryService.deleteOne(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // update an entry by id url(put) -> /journal/id/2
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry updates) {
        JournalEntry oldEntry = journalEntryService.getOne(myId).orElse(null);

        if (oldEntry != null) {
            oldEntry.setTitle(updates.getTitle() != null && !updates.getTitle().equals("") ? updates.getTitle()
                    : oldEntry.getTitle());

            oldEntry.setContent(updates.getContent() != null && !updates.getContent().equals("") ? updates.getContent()
                    : oldEntry.getContent());
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        journalEntryService.saveEntry(oldEntry);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
