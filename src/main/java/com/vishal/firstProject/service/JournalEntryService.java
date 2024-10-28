package com.vishal.firstProject.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vishal.firstProject.entity.JournalEntry;
import com.vishal.firstProject.repository.JournalEntryRepository;

@Component
public class JournalEntryService {
    
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    // to create journal
    public void saveEntry(JournalEntry entry){
        journalEntryRepository.save(entry);
    }

    // to get all journals
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    // to get one journal
    public Optional<JournalEntry> getOne(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    // to delete a journal
    public void deleteOne(ObjectId id){
        journalEntryRepository.deleteById(id);
    }
}
