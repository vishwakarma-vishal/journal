package com.vishal.firstProject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vishal.firstProject.entity.JournalEntry;
import com.vishal.firstProject.entity.User;
import com.vishal.firstProject.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    // to create journal
    @Transactional
    public void saveEntry(JournalEntry entry, String userName) {
        User user = userService.findByUserName(userName);

        entry.setDate(LocalDateTime.now());
        JournalEntry savedEntry = journalEntryRepository.save(entry);

        user.getJournalEntries().add(savedEntry);
        userService.createUser(user);
    } 

    // to update journal
    public void saveEntry(JournalEntry entry) {
        journalEntryRepository.save(entry);
    }

    // to get all journals
    public List<JournalEntry> getAll(String userName) {
        return journalEntryRepository.findAll();
    }

    // to get one journal
    public Optional<JournalEntry> getOne(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    // to delete a journal
    public void deleteOne(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.createUser(user);
        journalEntryRepository.deleteById(id);
    }
}
