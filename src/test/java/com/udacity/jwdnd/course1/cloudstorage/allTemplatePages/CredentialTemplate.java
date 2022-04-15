package com.udacity.jwdnd.course1.cloudstorage.allTemplatePages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialTemplate {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialBar;
    @FindBy(id = "addCredentialsButton")
    private WebElement addCredentialButton;

    @FindBy(id ="credential-url" )
    private WebElement urlField;

    @FindBy(id ="credential-username" )
    private WebElement usernameField;

    @FindBy(id ="credential-password" )
    private WebElement passwordField;

    @FindBy(id ="credentialSubmit" )
    private WebElement submitCredentialsButton;

    @FindBy(id ="credentialUrlText" )
    private WebElement credentialUrlText;

    @FindBy(id = "editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(id = "deleteCredentialButton")
    private WebElement deleteCredentialButton;

    private final WebDriver webDriver;

    public CredentialTemplate(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickCredentialBar(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.credentialBar);
    }

    public void createCredential(String url, String username, String password){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.credentialBar);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.addCredentialButton);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + url + "';", this.urlField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';", this.usernameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + password + "';", this.passwordField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.submitCredentialsButton);
    }

    public String getUrlText(){
        return credentialUrlText.getAttribute("innerHTML");
    }

    public void updateCredential(String url, String username, String password){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.credentialBar);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.editCredentialButton);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + url + "';", this.urlField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';", this.usernameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + password + "';", this.passwordField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.submitCredentialsButton);
    }

    public void deleteCredential(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.credentialBar);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.deleteCredentialButton);
    }
}
