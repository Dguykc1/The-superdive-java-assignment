package com.udacity.jwdnd.course1.cloudstorage.allTemplatePages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultTemplatePage {
    @FindBy(tagName = "a")
    private WebElement homeLink;
    private final WebDriver webDriver;

    public ResultTemplatePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickLink(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.homeLink);
    }
}
