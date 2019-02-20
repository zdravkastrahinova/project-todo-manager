package seleniumTests;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MyAccountTests {
    private WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("http://practice.automationtesting.in/my-account/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginFormWithEmptyFieldsReturnsErrorForUsername() {
        WebElement loginButtonElement = driver.findElement(By.className("woocommerce-Button"));
        loginButtonElement.click();

        WebElement errorsListElement = driver.findElement(By.className("woocommerce-error"));
        WebElement error = errorsListElement.findElements(By.tagName("li")).get(0);

        Assert.assertEquals("Error: Username is required.", error.getText());
    }

    @Test
    public void testLoginFormWithEmptyPasswordFieldReturnsErrorForPassword() {
        WebElement usernameInputElement = driver.findElement(By.id("username"));
        usernameInputElement.sendKeys("test");

        WebElement loginButtonElement = driver.findElement(By.className("woocommerce-Button"));
        loginButtonElement.click();

        WebElement errorsListElement = driver.findElement(By.className("woocommerce-error"));
        WebElement error = errorsListElement.findElements(By.tagName("li")).get(0);

        Assert.assertEquals("Error: Password is required.", error.getText());
    }

    @Test
    public void testLoginFormWithPopulatedInvalidCredentialsReturnsGeneralError() {
        WebElement usernameInputElement = driver.findElement(By.id("username"));
        usernameInputElement.sendKeys("test");

        WebElement passwordInputElement = driver.findElement(By.id("password"));
        passwordInputElement.sendKeys("test");

        WebElement loginButtonElement = driver.findElement(By.className("woocommerce-Button"));
        loginButtonElement.click();

        WebElement errorsListElement = driver.findElement(By.className("woocommerce-error"));
        WebElement error = errorsListElement.findElements(By.tagName("li")).get(0);

        Assert.assertEquals("ERROR: The password you entered for the username test is incorrect. Lost your password?", error.getText());
    }

    @Test
    public void testLoginFormWithValidCredentialsLogsIn() {
        WebElement usernameInputElement = driver.findElement(By.id("username"));
        usernameInputElement.sendKeys("hcfand+8178joafea1tc@auti.st");

        WebElement passwordInputElement = driver.findElement(By.id("password"));
        passwordInputElement.sendKeys("@Wizard.com!201");

        WebElement loginButtonElement = driver.findElement(By.className("woocommerce-Button"));
        loginButtonElement.click();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        WebElement myAccountAfterLoginElement = driver.findElement(By.className("woocommerce-MyAccount-content")).findElements(By.tagName("p")).get(0);
        WebElement logoutLinkElement = myAccountAfterLoginElement.findElement(By.tagName("a"));

        Assert.assertEquals("hcfand8178joafea1tc", myAccountAfterLoginElement.findElement(By.tagName("strong")).getText());
        Assert.assertNotNull(logoutLinkElement);
        Assert.assertEquals("Sign out", logoutLinkElement.getText());
    }

    @Test
    public void testLogoutAfterSuccessfulLogin() {
        WebElement usernameInputElement = driver.findElement(By.id("username"));
        usernameInputElement.sendKeys("hcfand+8178joafea1tc@auti.st");

        WebElement passwordInputElement = driver.findElement(By.id("password"));
        passwordInputElement.sendKeys("@Wizard.com!201");

        WebElement loginButtonElement = driver.findElement(By.className("woocommerce-Button"));
        loginButtonElement.click();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        WebElement myAccountAfterLoginElement = driver.findElement(By.className("woocommerce-MyAccount-content")).findElements(By.tagName("p")).get(0);
        WebElement logoutLinkElement = myAccountAfterLoginElement.findElement(By.tagName("a"));

        logoutLinkElement.click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        WebElement loginAndRegisterFormElements = driver.findElement(By.id("customer_login"));

        WebElement loginForm = loginAndRegisterFormElements.findElements(By.tagName("div")).get(0);
        Assert.assertEquals("Login", loginForm.findElement(By.tagName("h2")).getText());
        Assert.assertNotNull(loginForm.findElement(By.className("login")));

        WebElement registerForm = loginAndRegisterFormElements.findElements(By.tagName("div")).get(1);
        Assert.assertEquals("Register", registerForm.findElement(By.tagName("h2")).getText());
        Assert.assertNotNull(registerForm.findElement(By.className("register")));
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(2000);
        driver.close();
    }
}
