package com.udacity.jwdnd.course1.cloudstorage.model;

public class UserNoteModel {
    private Integer noteid;
    private String noteTitle;
    private String noteDescription;
    private Integer userid;

    public UserNoteModel(Integer noteid, String noteTitle, String noteDescription, Integer userid) {
        this.noteid = noteid;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteId(Integer noteId) {
        this.noteid = noteid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userId) {
        this.userid = userid;
    }
}
