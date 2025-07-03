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
    private WebDriver driver;


    private final By createNewButton = By.cssSelector("a[data-testid='action-new']");
    private final By titleInput = By.id("title");
    private final By contentInput = By.id("content");
    private final By publishedLabel = By.cssSelector("label[for='published']");
    private final By statusSelectInput = By.xpath("//label[contains(text(),'Status')]/following-sibling::div//input[contains(@id, 'react-select')]");
    private final By publisherSelectInput = By.xpath("//label[contains(text(),'Publisher')]/following-sibling::div//input[contains(@id, 'react-select')]");


    public PostPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public PostPage selectPublisherByEmail(String email) {
        WebElement publisherInput = wait.until(ExpectedConditions.visibilityOfElementLocated(publisherSelectInput));

        publisherInput.click();
        publisherInput.sendKeys(email);
        publisherInput.sendKeys(Keys.ENTER);

//// 1. Найти контейнер селекта (в котором поле ввода)
//        WebElement publisherSelect = driver.findElement(By.cssSelector("section[data-testid='property-edit-publisher'] div.adminjs_Select"));
//
//// 2. Кликнуть, чтобы раскрыть меню
//        publisherSelect.click();
//
//// 3. Найти input, чтобы ввести email (если нужно вводить, иначе можно пропустить)
//        WebElement input = publisherSelect.findElement(By.cssSelector("input[role='combobox']"));
//        input.sendKeys(email);  // если нужно фильтровать список
//
//// 4. Ждать появления выпадающего меню
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.css-eglc4o-menu")));
//
//// 5. Найти элемент с нужным email внутри меню и кликнуть по нему
//        WebElement option = dropdownMenu.findElements(By.cssSelector("div.css-1wrbua2-option"))
//                .stream()
//                .filter(e -> e.getText().equals(email))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("Option with email " + email + " not found"));
//
//        option.click();
        return this;
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
                .selectPublisherByEmail(publisherEmail)
                .clickSave();
    }
    // Найти и кликнуть по записи с нужным title
    public void openPostByTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td[data-property-name='title']")));

        List<WebElement> titles = driver.findElements(By.cssSelector("td[data-property-name='title'] > section[data-testid='property-list-title']"));
        for (WebElement t : titles) {
            if (t.getText().equals(title)) {
                t.click();
                return;
            }
        }
        throw new NoSuchElementException("Post with title '" + title + "' not found");
    }

    // Нажать кнопку Edit
    public void clickEditButton() {
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-testid='action-edit']")));
        editBtn.click();
    }

    // Изменить статус на Removed
    public void changeStatusToRemoved() {
        // Найти выпадающий список Status
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-haspopup='listbox']")));

        statusDropdown.click();

        // В выпадающем списке выбрать "Removed"
        // Вариант 1: найти элемент по тексту
        WebElement removedOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'css-')]//div[text()='Removed']")));
        removedOption.click();
    }


    // Получить статус записи в списке по title
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
