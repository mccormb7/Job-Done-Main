package selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AddPictureToProfile {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddPictureToProfile() throws Exception {
    driver.get(baseUrl + "/spring/docreateprofile");
    driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
    driver.findElement(By.linkText("Edit Profile")).click();
    new Select(driver.findElement(By.id("form-role"))).selectByVisibleText("Painter");
    driver.findElement(By.name("internetpic")).clear();
    driver.findElement(By.name("internetpic")).sendKeys("http://ia.media-imdb.com/images/M/MV5BMTI3MTc5NTc5NV5BMl5BanBnXkFtZTcwNzQzNzg3MQ@@._V1_UY317_CR4,0,214,317_AL_.jpg");
    driver.findElement(By.cssSelector("b > button.btn")).click();
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
