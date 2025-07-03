package ui;

import org.openqa.selenium.WebDriver;
import org.pages.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static api.service.BaseApiClient.*;
import static org.testng.AssertJUnit.assertEquals;

public class CreatePostUITest {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = WebDriverFactory.getDriver();
    }

    @Test
    public void testNavigateToPublisher() {
        driver.get(BASE_URI);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(LOGIN, PASSWORD);

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
        postPage.selectStatus("REMOVED");
        postPage.clickSave();

        String status = postPage.getStatusByTitle(postTitle);
        assertEquals("Post status should be REMOVED", "REMOVED", status);
    }


    @AfterClass
    public void tearDown() {
        WebDriverFactory.quitDriver();
    }
}
