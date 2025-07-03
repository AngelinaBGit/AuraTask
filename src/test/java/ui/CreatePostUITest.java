package ui;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pages.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

public class CreatePostUITest {
    private WebDriver driver;
    private final String baseUrl = "http://localhost:3000/admin";

    @BeforeClass
    public void setup() {
        driver = WebDriverFactory.getDriver();
    }
    @Test
    public void testNavigateToPublisher() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("admin@example.com", "password");

        AdminPage adminPage = new AdminPage(driver);
        adminPage.openMenu()
                .clickHappyFolder()
                .clickPublisher();
        PublisherPage publisherPage = new PublisherPage(driver);

        String uniqueName = "Publisher_" + System.currentTimeMillis();
        String uniqueEmail = "email" + System.currentTimeMillis() + "@mail.com";
        System.out.println(uniqueEmail);

        publisherPage.createNewPublisher(uniqueName, uniqueEmail);

        adminPage.openMenu().clickPost();

        PostPage postPage = new PostPage(driver);

        String postTitle = "Post title " + System.currentTimeMillis();
        String postContent = "Post content example";
        postPage.createNewPost(postTitle, postContent, "ACTIVE", uniqueEmail);

        postPage.openPostByTitle(postTitle);
        postPage.clickEditButton();
        postPage.changeStatusToRemoved();
        postPage.clickSave();

        String status = postPage.getStatusByTitle(postTitle);
        assertEquals(status, "REMOVED", "Post status should be REMOVED");

    }


//    @AfterClass
//    public void tearDown() {
//        WebDriverFactory.quitDriver();
//    }
}
