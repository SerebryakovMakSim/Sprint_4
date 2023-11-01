package ru.yandex.praktikum.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
    private final By buttonOrderTop = By.xpath(".//div[starts-with(@class,'Header_Nav')]//button[text()='Заказать']"); // Локатор верх кнопки заказать.
    private final By buttonOrderBottom = By.xpath(".//div[contains(@class,'FinishButton')]//button[text()='Заказать']"); // Локатор нижней кнопки заказать.
    private final By sectionFaq = By.xpath(".//div[starts-with(@class,'Home_FAQ')]"); // "Вопросы о важном".
    private final By accordionItem = By.className("accordion__item"); // Элемент "Вопросы о важном".
    private final By accordionButton = By.className("accordion__button"); // Кнопка с вопросом.
    private final By accordionPanel = By.className("accordion__panel"); // Панель с ответом.
    private final By imageScooter = By.xpath(".//img[@alt = 'Scooter blueprint']");
    private final By buttonAcceptCookie = By.id("rcc-confirm-button");

    // Конструктор класса.
    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    // Ожидание загрузки вопросов о важном.
    public void waitForLoadFaq() {
        WebElement faqElement = driver.findElement(sectionFaq);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(sectionFaq));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", faqElement);
    }

    public void waitForLoadPage() {
        WebElement imageElement = driver.findElement(imageScooter);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(imageScooter));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", imageElement);
    }

    public boolean isElementExist(By locatorBy) {
        try {
            driver.findElement(locatorBy);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    // возвращает список всех вопросов-ответов
    public List<WebElement> getFaqItems(){
        return driver.findElements(accordionItem);
    }

    public boolean isButtonClickable(WebElement faqElement) {
        // Ожидание, что элемент станет кликабельным в течение 2 секунд
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(faqElement.findElement(accordionButton)));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public String getQuestion(WebElement faqElement) {
        return faqElement.findElement(accordionButton).getText();
        // Получение текста из элемента, представляющего заголовок вопроса
    }

    public String getAnswer(WebElement faqElement) {
        return faqElement.findElement(accordionPanel).getText();
        // Получение текста из элемента, представляющего ответ на вопрос
    }

    public void clickOrder(int indexButton) {
        switch (indexButton) {
            case 0:
                // Клик по кнопке заказа, вверху страницы
                driver.findElement(buttonOrderTop).click();
                break;
            case 1:
                // Прокрутка страницы вниз для отображения кнопки заказа, которая находится внизу страницы
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                WebElement buttonOrder = driver.findElement(buttonOrderBottom);
                // Ожидание, что кнопка заказа отобразится в течение 10 секунд
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (buttonOrder.isDisplayed()));
                // Клик по кнопке заказа, внизу страницы
                buttonOrder.click();
                break;
        }
    }

    public void clickGetCookie() {
        if (isElementExist(buttonAcceptCookie))
            // Клик по кнопке принятия cookie, если она существует
            driver.findElement(buttonAcceptCookie).click();
    }

}