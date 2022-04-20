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



    public UserNoteModel findOne(Integer noteid){
        return noteMapper.findOne(noteid);
    }

    public void deleteNote(Integer noteid){
        noteMapper.deleteNote(noteid);
    }

    public List<UserNoteModel> getNotes(Integer userid)
    {
        return noteMapper.getNotes(userid);
    }

    public void update(UserNoteModel note){
        noteMapper.updateNote(note);
    }

    public UserNoteModel findByTitleAndDesc(String notetitle, String notedescription){
        return noteMapper.findByTitleAndDesc(notetitle, notedescription);
    }

}
