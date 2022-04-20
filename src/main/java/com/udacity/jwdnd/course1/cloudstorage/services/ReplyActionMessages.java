package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

@Service
public class ReplyActionMessages {
    //All File Messages variable declaration
  public   String fileUploadSuccess;
    public String fileUploadFailed;
    public  String fileDeleteSuccessful;
    public  String fileDeleteNotSuccessful;
    public  String fileNameAlreadyExists;
    public  String didNotSelectAFile;
    //Action messages for Note
    public  String noteCreationSuccessful;
    public final String noteCreationNotSuccessful;
    public  String noteUpdateSuccessful;
    public String noteUpdateNotSuccessful;
    public String noteDeleteSuccessful;
    public  String noteDeleteNotSuccessful;
    public String noteAlreadyExists;

    //Action messages for Credentials
    public String credentialCreationSuccessful;
    public String credentialCreationNotSuccessful;
    public String credentialUpdateSuccessful;
    public String credentialUpdateNotSuccessful;
    public  String credentialDeletionSuccessful;
    public String credentialDeletionNotSuccessful;
    //Constructor method where all the string messages are instantiated
public ReplyActionMessages(){
  //file upload message instances
  fileUploadSuccess = "User File was successfully uploaded to the server.";
  fileUploadFailed  = "User File upload to the server failed. Please try again.";
  fileDeleteSuccessful = "User File was successfully deleted from the server.";
  fileDeleteNotSuccessful = "User File item deletion failed. Please try again.";
  fileNameAlreadyExists = "Sorry, you cannot upload two files with the same name in the database.";
  didNotSelectAFile = "Please select a file to upload.";

  //Note creation message instances
  noteCreationSuccessful = "Note was successfully created in the server.";
  noteCreationNotSuccessful= "Note item creation failed. Please try again.";
  noteUpdateSuccessful = "Note was successfully updated in the server.";
  noteUpdateNotSuccessful = "User Note item update failed. Please try again.";
  noteDeleteSuccessful = "User Note was successfully deleted from the server.";
  noteDeleteNotSuccessful= "User Note item delete failed. Please try again.";
  noteAlreadyExists = "Please try again, Note already exists.";

  //Credential creation message instances
  credentialCreationSuccessful = "User Credentials was successfully created.";
  credentialCreationNotSuccessful= "User Credentials creation failed. Please try again.";
  credentialUpdateSuccessful= "User Credentials were successfully updated.";
  credentialUpdateNotSuccessful= "User Credentials update failed. Please try again.";
  credentialDeletionSuccessful= "User Credentials were successfully deleted.";
  credentialDeletionNotSuccessful = "User Credentials delete failed. Please try again.";
}
}
