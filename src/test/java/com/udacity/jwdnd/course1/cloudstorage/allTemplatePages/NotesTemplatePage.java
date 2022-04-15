package com.udacity.jwdnd.course1.cloudstorage.allTemplatePages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesTemplatePage {
    @FindBy(id="nav-notes-tab")
    private WebElement noteBar;


    @FindBy(id="addNotesButton")
    private WebElement addNotesButton;

    @FindBy(id="note-title")
    private WebElement noteTitleField;

    @FindBy(id="note-description")
    private WebElement noteDescriptionField;

    @FindBy(id="submitNoteButton")
    private WebElement submitNoteButton;

    @FindBy(id="noteTitleDisplay")
    private WebElement noteTitleText;

    @FindBy(id="editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    private final WebDriver webDriver;

    public  NotesTemplatePage(WebDriver webDriver){
        this.webDriver =webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickNoteBar(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.noteBar);
    }

    public void createNote(String noteTitle, String noteDescription){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.noteBar);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.addNotesButton);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteTitle + "';", this.noteTitleField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteDescription + "';", this.noteDescriptionField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.submitNoteButton);
    }

    public String getNoteTitleText() {
        return noteTitleText.getAttribute("innerHTML");
    }

    public void editNote(String noteTitle, String noteDescription){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.noteBar);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.editNoteButton);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteTitle + "';", this.noteTitleField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteDescription + "';", this.noteDescriptionField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.submitNoteButton);
    }

    public void deleteNote(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.noteBar);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.deleteNoteButton);
    }
}
