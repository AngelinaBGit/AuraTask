package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class PostPage {
    private final WebDriverWait wait;
    private final WebDriver driver;
    private final By createNewButton = By.cssSelector("a[data-testid='action-new']");
    private final By titleInput = By.id("title");
    private final By contentInput = By.id("content");
    private final By publishedLabel = By.cssSelector("label[for='published']");
    private final By statusSelectInput = By.xpath("//label[contains(text(),'Status')]/following-sibling::div//input[contains(@id, 'react-select')]");
    private final By publisherSelectInput = By.xpath("//label[contains(text(),'Publisher')]/following-sibling::div//input[contains(@id, 'react-select')]");


    public PostPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public PostPage clickCreateNew() {
        wait.until(ExpectedConditions.elementToBeClickable(createNewButton)).click();
        return this;
    }

    public PostPage enterTitle(String title) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput));
        input.clear();
        input.sendKeys(title);
        return this;
    }

    public PostPage enterContent(String content) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(contentInput));
        input.clear();
        input.sendKeys(content);
        return this;
    }

    public PostPage selectStatus(String status) {
        WebElement statusInput = wait.until(ExpectedConditions.visibilityOfElementLocated(statusSelectInput));

        statusInput.click();
        statusInput.sendKeys(status);
        statusInput.sendKeys(Keys.ENTER);
        return this;
    }

    public PostPage clickPublishedLabel() {
        wait.until(ExpectedConditions.elementToBeClickable(publishedLabel)).click();
        return this;
    }

    public void selectPublisherByEmail() {
        WebElement publisherInput = wait.until(ExpectedConditions.visibilityOfElementLocated(publisherSelectInput));
        publisherInput.click();
        publisherInput.sendKeys(Keys.SPACE);
    }

    public void clickSave() {
        By saveButton = By.cssSelector("button[data-testid='button-save']");
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void createNewPost(String title, String content, String status, String publisherEmail) {
        clickCreateNew()
                .enterTitle(title)
                .enterContent(content)
                .selectStatus(status)
                .clickPublishedLabel()
                .selectPublisherByEmail();
        fillAdditionalPostFields();
        clickSave();
    }

    public void openPostByTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td[data-property-name='title']")));

        List<WebElement> titles = driver.findElements(By.cssSelector("td[data-property-name='title'] > section[data-testid='property-list-title']"));
        for (WebElement t : titles) {
            String actualTitle = t.getText().trim();
            if (actualTitle.equalsIgnoreCase(title.trim())) {
                t.click();
                return;
            }
        }
        throw new NoSuchElementException("Post with title '" + title + "' not found");
    }

    public void clickEditButton() {
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-testid='action-edit']")));
        editBtn.click();
    }

    public void fillAdditionalPostFields() {
        WebElement addItemButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-testid='someJson-add']")));
        addItemButton.click();

        WebElement numberField = wait.until(ExpectedConditions.elementToBeClickable(
                By.name("someJson.0.number")));
        numberField.click();
        numberField.sendKeys("123");

    }

    public String getStatusByTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table")));

        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));
        for (WebElement row : rows) {
            WebElement titleCell = row.findElement(By.cssSelector("td[data-property-name='title'] > section"));
            if (titleCell.getText().equals(title)) {
                WebElement statusCell = row.findElement(By.cssSelector("td[data-property-name='status'] > section"));
                return statusCell.getText();
            }
        }
        throw new NoSuchElementException("Post with title '" + title + "' not found");
    }
}
