package com.udacity.jwdnd.course1.cloudstorage.allTemplatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeTemplatePage {
    @FindBy(id="logout-button")
    private WebElement logoutButton;

    public HomeTemplatePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void logout(){
        this.logoutButton.click();
    }
}
