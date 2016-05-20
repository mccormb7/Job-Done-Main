package selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreatePlan {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreatePlan() throws Exception {
    driver.get(baseUrl + "spring/jobposts");
    driver.findElement(By.xpath("//ul/ul/li/a/span")).click();
    driver.findElement(By.linkText("Create New Task")).click();
    driver.findElement(By.id("form-title")).clear();
    driver.findElement(By.id("form-title")).sendKeys("Testtstest");
    driver.findElement(By.id("form-description")).clear();
    driver.findElement(By.id("form-description")).sendKeys("te");
    driver.findElement(By.id("form-description")).clear();
    driver.findElement(By.id("form-description")).sendKeys("TesttstestTesttstestTesttstestTesttstest");
    driver.findElement(By.id("searchTextField")).clear();
    driver.findElement(By.id("searchTextField")).sendKeys("4 cre");
    driver.findElement(By.name("price")).clear();
    driver.findElement(By.name("price")).sendKeys("20");
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.linkText("View Current Tasks")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}