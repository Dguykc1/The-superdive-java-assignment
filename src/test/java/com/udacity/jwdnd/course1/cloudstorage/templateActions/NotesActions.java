package com.udacity.jwdnd.course1.cloudstorage.templateActions;
import com.udacity.jwdnd.course1.cloudstorage.allTemplatePages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotesActions {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    public String BASE_URL;
    public static final String NOTE_INFO= "Maneno";
    public static final String NOTE_INFO_EDIT= "x";

    private final String username = "uleMsee";
    private final String password = "kipassword";

    private LoginTemplatePage loginPage;
    private HomeTemplatePage homePage;
    private NotesTemplatePage notesSection;
    private ResultTemplatePage resultPage;


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        BASE_URL = "http://localhost:" + port;

        //login

        driver.get(BASE_URL + "/signup");
        SignUpTemplatePage signUpPage = new SignUpTemplatePage(driver);
        signUpPage.signup("Robert", "Lewandowski", username, password);


        driver.get(BASE_URL +"/login");
        loginPage = new LoginTemplatePage(driver);
        loginPage.login(username, password);

        driver.get(BASE_URL +"/home");

        homePage = new HomeTemplatePage(driver);
        notesSection = new NotesTemplatePage(driver);
        resultPage = new ResultTemplatePage(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(2)
    void addNote(){
        notesSection.createNote(NOTE_INFO, NOTE_INFO);

        resultPage.clickLink();
        assertEquals("Home", driver.getTitle());

        notesSection.clickNoteBar();
        assertEquals(NOTE_INFO, notesSection.getNoteTitleText());
    }


    @Test
    @Order(3)
    void editNote(){
        //create note
        notesSection.createNote(NOTE_INFO, NOTE_INFO);
        resultPage.clickLink();

        //logout
        homePage.logout();
        driver.get(BASE_URL +"/login");
        assertEquals("Login", driver.getTitle());

        //login
        loginPage.login(username, password);
        driver.get(BASE_URL +"/home");
        assertEquals("Home", driver.getTitle());

        //edit note
        notesSection.editNote(NOTE_INFO_EDIT, NOTE_INFO_EDIT);
        resultPage.clickLink();
        notesSection.clickNoteBar();
        assertEquals(NOTE_INFO_EDIT, notesSection.getNoteTitleText());
    }

    @Test
    @Order(1)
    void  deleteNote(){
        //create note
        notesSection.createNote(NOTE_INFO, NOTE_INFO);
        resultPage.clickLink();

        //logout
        homePage.logout();
        driver.get(BASE_URL +"/login");
        assertEquals("Login", driver.getTitle());

        //login
        loginPage.login(username, password);
        driver.get(BASE_URL +"/home");
        assertEquals("Home", driver.getTitle());

        //delete
        notesSection.deleteNote();
        resultPage.clickLink();
        notesSection.clickNoteBar();
        assertThrows(NoSuchElementException.class, notesSection::getNoteTitleText);
    }

}
