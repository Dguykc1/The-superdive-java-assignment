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
public class CredentialActions {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    public String BASE_URL;
    public static final String CREDENTIAL_INFO= "Maneno";
    public static final String CREDENTIAL_INFO_EDIT= "x";

    private final String username = "uleMsee";
    private final String password = "kipassword";

    private LoginTemplatePage loginPage;
    private HomeTemplatePage homePage;
    private CredentialTemplate credentialSection;
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

        credentialSection = new CredentialTemplate(driver);
        resultPage = new ResultTemplatePage(driver);
        homePage = new HomeTemplatePage(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(2)
    void addCredential(){
        credentialSection.createCredential(CREDENTIAL_INFO, CREDENTIAL_INFO, CREDENTIAL_INFO);

        resultPage.clickLink();
        assertEquals("Home", driver.getTitle());

        credentialSection.clickCredentialBar();
        assertEquals(CREDENTIAL_INFO, credentialSection.getUrlText());
    }

    @Test
    @Order(3)
    void updateCredential(){
        //create credential
        credentialSection.createCredential(CREDENTIAL_INFO,CREDENTIAL_INFO, CREDENTIAL_INFO);
        resultPage.clickLink();

        //logout
        HomeTemplatePage homePage = new HomeTemplatePage(driver);
        homePage.logout();
        driver.get(BASE_URL +"/login");
        assertEquals("Login", driver.getTitle());

        //login for existing user
        loginPage.login(username, password);
        driver.get(BASE_URL +"/home");
        assertEquals("Home", driver.getTitle());

        //edit credential
        credentialSection.updateCredential(CREDENTIAL_INFO_EDIT, CREDENTIAL_INFO_EDIT, CREDENTIAL_INFO_EDIT);
        resultPage.clickLink();
        credentialSection.clickCredentialBar();
        assertEquals(CREDENTIAL_INFO_EDIT, credentialSection.getUrlText());
    }

    @Test
    @Order(1)
    void  deleteCredential(){
        //create credential
        credentialSection.createCredential(CREDENTIAL_INFO,CREDENTIAL_INFO, CREDENTIAL_INFO);
        resultPage.clickLink();

        //logout
        homePage.logout();
        driver.get(BASE_URL +"/login");
        assertEquals("Login", driver.getTitle());


        //login for existing user
        loginPage.login(username, password);
        driver.get(BASE_URL +"/home");
        assertEquals("Home", driver.getTitle());

        //delete credential
        credentialSection.deleteCredential();
        resultPage.clickLink();
        credentialSection.clickCredentialBar();
        assertThrows(NoSuchElementException.class, credentialSection::getUrlText);
    }
}
