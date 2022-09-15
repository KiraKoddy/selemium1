package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ZakazKartyNeg {
    WebDriver driver = new ChromeDriver();

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void negativeTestName() {
        driver.get("http://localhost:9999");
        List<WebElement> name = driver.findElements(By.className("input__control"));
        name.get(0).sendKeys("Ivanov Ivan");
        name.get(1).sendKeys("+79779992200");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().strip();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(actual, expected);

    }

    @Test
    public void negativeTestNameNull() {
        driver.get("http://localhost:9999");
        List<WebElement> name = driver.findElements(By.className("input__control"));
        name.get(0).sendKeys("");
        name.get(1).sendKeys("+79779992200");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().strip();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(actual, expected);

    }

    @Test
    public void negativeTestPhoneNull() {
        driver.get("http://localhost:9999");
        List<WebElement> name = driver.findElements(By.className("input__control"));
        name.get(0).sendKeys("Иванов Иван");
        name.get(1).sendKeys("");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().strip();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void negativeTestPhoneIncorrect() {
        driver.get("http://localhost:9999");
        List<WebElement> name = driver.findElements(By.className("input__control"));
        name.get(0).sendKeys("Иванов Иван");
        name.get(1).sendKeys("79779992200");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().strip();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void negativeTestNoCheckbox() {
        driver.get("http://localhost:9999");
        List<WebElement> name = driver.findElements(By.className("input__control"));
        name.get(0).sendKeys("Иванов Иван");
        name.get(1).sendKeys("+79779992200");
        driver.findElement(By.className("button__text")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }
}