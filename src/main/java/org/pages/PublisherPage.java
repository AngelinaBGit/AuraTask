package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PublisherPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By createNewButton = By.cssSelector("a[data-testid='action-new']");
    private final By nameInput = By.id("name");
    private final By emailInput = By.id("email");
    private final By saveButton = By.cssSelector("button[data-testid='button-save']");

    public PublisherPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PublisherPage clickCreateNew() {
        wait.until(ExpectedConditions.elementToBeClickable(createNewButton)).click();
        return this;
    }

    public PublisherPage enterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).clear();
        driver.findElement(nameInput).sendKeys(name);
        return this;
    }

    public PublisherPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
        return this;
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void createNewPublisher(String name, String email) {
        clickCreateNew()
                .enterName(name)
                .enterEmail(email)
                .clickSave();
    }
}
