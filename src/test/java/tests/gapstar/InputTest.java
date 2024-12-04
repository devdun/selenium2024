package tests.gapstar;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import page_factory.homepage.HerokuAppHomePage;
import page_factory.input_page.HerokuAppInputPage;
import tests.Base;

public class InputTest extends Base {
    private HerokuAppHomePage herokuAppHomePage;
    private HerokuAppInputPage inputsPage;


    @Test (priority = 1 , description = "verify user navigate Input page")
    public void testHeaderText() {
        herokuAppHomePage = PageFactory.initElements(driver,HerokuAppHomePage.class);
        inputsPage = PageFactory.initElements(driver,HerokuAppInputPage.class);
        herokuAppHomePage.clickInputLink();
        String currentURl = driver.getCurrentUrl();
        Assert.assertEquals(currentURl, "https://the-internet.herokuapp.com/inputs");

        // Verify the page header
        String expectedHeader = "Inputs";
        String actualHeader = inputsPage.getHeaderText();
        Assert.assertEquals(actualHeader, expectedHeader, "Page header mismatch!");
    }

    @Test (priority = 2 , description = "verify input fields")
    public void testInputField() {
        // Test entering a valid numeric value
        inputsPage.enterValue("123");
        Assert.assertEquals(inputsPage.getInputValue(), "123", "Numeric value mismatch!");

        // Test entering a negative value
        inputsPage.enterValue("-45");
        Assert.assertEquals(inputsPage.getInputValue(), "-45", "Negative value mismatch!");

        // Test entering a string value (should handle gracefully)
        inputsPage.enterValue("abc");
        Assert.assertEquals(inputsPage.getInputValue(), "", "String input should not be accepted!");
    }
}
