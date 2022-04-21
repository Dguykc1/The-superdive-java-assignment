package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private static String baseUrl;
	private final String  firstName="Christian";
	private final String lastName ="Kelechi";
	private final String  userName="kc";
	private final String  password="1999";

	//The UserNote
	private final String noteTitleOne="Thermodynamics";
	private final String noteTitleTwo="FluidMechanics";
	private final String noteDescriptionOne ="Thermodynamics is the study of matter";
	private final String noteDescriptionTwo="FluidMechanics is the study of fluid in motion or static";

	//The Credential
	private final String[] urls= new String[]{"one.com", "two.com", "three.com"};
	private final String[] userNames = new String[]{"kc","solomon","joboson"};
	private final String[] passwords = new String[]{"1999","2000","2001"};
	private final String[] urlsTwo= new String[]{"oneone.com", "twotwo.com", "threethree.com"};
	private final String[] userNamesTwo = new String[]{"kc2","solomon2","joboson2"};
	private final String[] passwordsTwo = new String[]{"2002","2004","2001"};

	@Autowired
	private EncryptionService encryptionService;
	@Autowired
	private UserCredentialService userCredentialService;

	private Logger logger= LoggerFactory.getLogger(CloudStorageApplicationTests.class);

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
	}
	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
	}
	@BeforeEach
	public void beforeEach() throws InterruptedException {
		//this.driver = new ChromeDriver();
		baseUrl="http://localhost:" + this.port;
		sleep(2000);
	}

	@AfterEach
	public void afterEach() throws InterruptedException {
		sleep(4000);
	}

	@Test
	@Order(1)
	public void authorizeGetLoginPage() throws InterruptedException {
		//driver.get("http://localhost:" + this.port + "/login");
		//Assertions.assertEquals("Login", driver.getTitle());
		logger.error("test 1 -accessibility and security");

		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		sleep(3000);


		driver.get(baseUrl+"/signup");
		Assertions.assertEquals("Sign Up",driver.getTitle());
		sleep(3000);

		driver.get(baseUrl+"/home");
		Assertions.assertNotEquals("Home",driver.getTitle());
	}
	@Test
	@Order(2)
	//Write a test that signs up a new user, logs in, verifies that the home page is accessible,
	// logs out, and verifies that the home page is no longer accessible
	public void secondSignupSuccess() throws InterruptedException {
		logger.error("test 2 signup successful");
		driver.get(baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUpNow(firstName, lastName, userName, password);
		//now we should have been redirected to login page with successful msg
		//driver.navigate().to(baseUrl +"/login");
		//driver.switchTo().window("/login");
		sleep(4000);
		assertEquals("Login", driver.getTitle());
		//driver.findElement(By.id("signup-success-msg"));
		//driver.get(baseUrl + "/login");
		//LoginPage loginPage=new LoginPage(driver);
		//sleep(5000);
		//Assertions.assertTrue(driver..sigupOkMsgDisplayed());
		//Assertions.assertTrue(signupPage.sigupOkMsgDisplayed());
	}
	@Test
	@Order(3)
	public void threeLoginSuccess() throws Exception{
		logger.error("test 3 -login-logout-login again");
		driver.get(baseUrl + "/login");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.LoginNow(userName,password);
		sleep(1000);
		//Assert page redirected to home so login is successful
		Assertions.assertEquals("Home",driver.getTitle());

		driver.get(baseUrl + "/home");
		HomePage homePage=new HomePage(driver);
		homePage.clickLogoutBtn();
		sleep(3000);
		//Assert home page is not accessible after logout
		Assertions.assertNotEquals("Home",driver.getTitle());

		//now it is on login page actually, login again for other tests to continue
		driver.get(baseUrl + "/login");
		loginPage.LoginNow(userName,password);
		sleep(3000);
		//Assert page redirected to home so login is successful
		Assertions.assertEquals("Home",driver.getTitle());

	}
	public void waitForVisibility(String id){
		WebDriverWait wait = new WebDriverWait(driver, 4000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

	}

	@Test
	@Order(4)
	public void testNotes() throws Exception{
		logger.error("test 4 -Notes");
		//c_loginSuccess();
		driver.get(baseUrl+"/home");
		NotePage notePage=new NotePage(driver);

		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();

		waitForVisibility(notePage.getAddNoteBtnId());
		notePage.clickAddNoteBtn();

		//Create New Note and verify//
		//now the Modal is there, input values
		//waitForVisibility(notePage.getNoteSubmitBtnId());
		notePage.inputNoteTitle(noteTitleOne);
		notePage.inputNoteDescription(noteDescriptionOne);
		sleep(2000);
		notePage.submitNote();
		//go back to notTab
		sleep(2000);
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		//verify new note is added and displayed as expected
		Assertions.assertEquals(notePage.getNoteTitleDisplay(),noteTitleOne);
		Assertions.assertEquals(notePage.getNoteDesDisplay(),noteDescriptionOne);

		sleep(3000);

		//Edit the newly created Note and verify//
		notePage.clickNoteEditBtn();
		sleep(1000);
		//waitForVisibility(notePage.getNoteSubmitBtnId());
		//edit the note
		notePage.inputNoteTitle(noteTitleTwo);
		notePage.inputNoteDescription(noteDescriptionTwo);
		sleep(2000);
		notePage.submitNote();
		//go back to notTab
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		sleep(1000);
		//verify  note is edited and displayed as expected
		Assertions.assertEquals(notePage.getNoteTitleDisplay(),noteTitleTwo);
		Assertions.assertEquals(notePage.getNoteDesDisplay(),noteDescriptionTwo);

		//Delete the newly edited Note and verify//
		//go back to notTab
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		//delete the Note and verify it is not there
		notePage.clickNoteDeleteBtn();
		//Assertions.assertNull(notePage.getNoteTitleDisplay());
		//Assertions.assertThrows(Exception.class,null);

		sleep(2000);
		//go back to notTab, visually see note is deleted and let's assert it
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		sleep(1000);
		Assertions.assertEquals(0,notePage.getNoteEditBtns().size());

	}
	@Test
	@Order(5)
	public void testCredentials() throws Exception {
		logger.error("test 5 - Credentials");
		driver.get(baseUrl + "/home");
		CredentialPage credentialPage = new CredentialPage(driver);

		//////Create new Credentials and verify//////
		int total=3;
		//create number of "total" credentials
		for(int pos=0;pos<total;pos++){
			//wait for Credential page is visible
			waitForVisibility(credentialPage.getCrenTabId());
			credentialPage.clickCrenTab();
			sleep(1000);
			//click add new credential button
			waitForVisibility(credentialPage.getAddCrenBtnId());
			credentialPage.clickAddCrenBtn();
			//now the modal is there, input values
			credentialPage.inputUrl(urls[pos]);
			credentialPage.inputUserName(userNames[pos]);
			credentialPage.inputPasswd(passwords[pos]);
			sleep(2000);
			credentialPage.clickCrenSubmitBtn();
			//this sleep is important, otherwise at the beginning of the next loop,
			//tab may not available when visibility is being checked which causing waiting forever
			sleep(3000);
		}

		//go back to Credential tab
		waitForVisibility(credentialPage.getCrenTabId());
		credentialPage.clickCrenTab();
		sleep(1000);
		//verify the displayed credentials are expected and their passwds are encrypted
		//encryptionService = new EncryptionService();
		for(int pos=0;pos<total;pos++){
			String displayedUrl=credentialPage.getUrl(pos);
			String displayedUname=credentialPage.getUname(pos);

			String displayedPwd = credentialPage.getPw(pos);
			//decrypt pwd
			String key=userCredentialService.getKeyById(pos+1);
			displayedPwd= encryptionService.decryptValue(displayedPwd,key);

			Assertions.assertEquals(displayedUrl,urls[pos]);
			Assertions.assertEquals(displayedUname,userNames[pos]);
			Assertions.assertEquals(displayedPwd,passwords[pos]);
		}


		////Edit Credentials and Verify the updated results////
		////Same time verified in the Edit window the password is decrypted////
		for(int pos=0;pos<total;pos++) {
			//wait for Credential page is visible
			waitForVisibility(credentialPage.getCrenTabId());
			credentialPage.clickCrenTab();
			sleep(1000);
			credentialPage.clickEditCredBtn(pos);
			sleep(1000);
			credentialPage.inputUrl(urlsTwo[pos]);
			credentialPage.inputUserName(userNamesTwo[pos]);
			//before update pwd, first verify it is decrypted
			Assertions.assertEquals(credentialPage.getPasswdInModal(),passwords[pos]);
			credentialPage.inputPasswd(passwordsTwo[pos]);
			sleep(3000);
			credentialPage.clickCrenSubmitBtn();
			//this sleep is important, otherwise at the beginning of the next loop,
			//tab may not available when visibility is being checked which causing waiting forever
			sleep(3000);
		}

		//go back to Credential tab
		waitForVisibility(credentialPage.getCrenTabId());
		credentialPage.clickCrenTab();
		sleep(1000);
		//verify after update, the displayed credentials are expected and their passwds are encrypted
		for(int pos=0;pos<total;pos++){
			String displayedUrl=credentialPage.getUrl(pos);
			String displayedUname=credentialPage.getUname(pos);

			String displayedPwd = credentialPage.getPw(pos);
			//decrypt pwd
			String key=userCredentialService.getKeyById(pos+1);
			displayedPwd= encryptionService.decryptValue(displayedPwd,key);

			Assertions.assertEquals(displayedUrl,urlsTwo[pos]);
			Assertions.assertEquals(displayedUname,userNamesTwo[pos]);
			Assertions.assertEquals(displayedPwd,passwordsTwo[pos]);
		}

		///Verify deleting credentials is working as expected
		for(int pos=0;pos<total;pos++){
			waitForVisibility(credentialPage.getCrenTabId());
			credentialPage.clickCrenTab();
			sleep(1000);

			//note after each deletion, the next one we want to delete is always at position 0
			credentialPage.clickDeleteCredBtn(0);
			sleep(3000);
		}

		//go back to Credential tab, visually all credentials are deleted and let's assert it
		waitForVisibility(credentialPage.getCrenTabId());
		credentialPage.clickCrenTab();
		Assertions.assertEquals(0,credentialPage.getEditBtns().size());
		sleep(2000);



	}
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
