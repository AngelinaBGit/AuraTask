package org.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    // Поле email
    @FindBy(name = "email")
    private WebElement emailInput;

    // Поле пароль
    @FindBy(name = "password")
    private WebElement passwordInput;

    // Кнопка Login
    @FindBy(xpath = "//button[contains(text(),'Login')]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Ввод email
    public LoginPage enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    // Ввод пароля
    public LoginPage enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    // Нажать кнопку Login
    public void clickLogin() {
        loginButton.click();
    }

    // Полный метод логина для удобства
    public void loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}