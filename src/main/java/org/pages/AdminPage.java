package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage {
    private final WebDriverWait wait;
    private final By menuButtonLocator = By.cssSelector("section.sc-dmqHEX.adminjs_Box");
    private final By happyFolderLocator = By.xpath("//div[text()='Happy Folder']");
    private final By publisherLocator = By.xpath("//div[text()='Publisher']");
    private final By postLocator = By.xpath("//div[text()='Post']");

    public AdminPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public AdminPage openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButtonLocator)).click();
        return this;
    }

    public AdminPage clickHappyFolder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(happyFolderLocator)).click();
        return this;
    }

    public void clickPublisher() {
        wait.until(ExpectedConditions.elementToBeClickable(publisherLocator)).click();
    }

    public void clickPost() {
        wait.until(ExpectedConditions.elementToBeClickable(postLocator)).click();
    }
}
