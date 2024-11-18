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
import com.vishal.firstProject.entity.User;
import com.vishal.firstProject.service.JournalEntryService;
import com.vishal.firstProject.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // to create an entry url(post) -> /journal/create
    @PostMapping("/{userName}/create")
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry newentry,
            @PathVariable String userName) {

        try {
            journalEntryService.saveEntry(newentry, userName);

            return new ResponseEntity<>(newentry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // to get all the entry url(get) -> /journal/{userName}
    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);

        // Check if user is null
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<JournalEntry> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        // If user exists but has no journal entries
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    @DeleteMapping("/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId, @PathVariable String userName) {
        try {
            journalEntryService.deleteOne(myId, userName);
            return new ResponseEntity<>("Journal is deleted succesfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_GATEWAY);
        }
    }

    // update an entry by id url(put) -> /journal/id/2
    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateEntry(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry updates,
            @PathVariable String userName) {

        JournalEntry oldEntry = journalEntryService.getOne(myId).orElse(null);

        if (oldEntry != null) {
            oldEntry.setTitle(updates.getTitle() != null &&
                    !updates.getTitle().equals("") ? updates.getTitle()
                            : oldEntry.getTitle());

            oldEntry.setContent(updates.getContent() != null &&
                    !updates.getContent().equals("") ? updates.getContent()
                            : oldEntry.getContent());

            journalEntryService.saveEntry(oldEntry);

            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
