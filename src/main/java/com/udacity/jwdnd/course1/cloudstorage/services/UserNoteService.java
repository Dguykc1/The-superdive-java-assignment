package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserNoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNoteService {
    private final UserNoteMapper noteMapper;

    public UserNoteService(UserNoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(UserNoteModel note) {
        return noteMapper.insert(note);
    }

    public List<UserNoteModel> getNotes(Integer userId)
    {
        return noteMapper.getNotes(userId);
    }

    public void update(UserNoteModel note){
        noteMapper.updateNote(note);
    }

    public UserNoteModel findOne(Integer noteId){
        return noteMapper.findOne(noteId);
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }

    public UserNoteModel findByTitleAndDesc(String noteTitle, String noteDescription){
        return noteMapper.findByTitleAndDesc(noteTitle, noteDescription);
    }

}
