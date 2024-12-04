package tests.gapstar;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import page_factory.checkbox_page.HerokuAppCheckBoxPage;
import page_factory.homepage.HerokuAppHomePage;
import tests.Base;

public class CheckBoxTest extends Base {
    private HerokuAppHomePage herokuAppHomePage;
    private HerokuAppCheckBoxPage herokuAppCheckBoxPage;


    @Test(priority = 1 , description = "verify user navigate to checkbox page")
    public void verifyNavigation(){
        herokuAppHomePage = PageFactory.initElements(driver,HerokuAppHomePage.class);

        herokuAppHomePage.clickCheckBoxLink();
        String currentURl = driver.getCurrentUrl();
        Assert.assertEquals(currentURl, "https://the-internet.herokuapp.com/checkboxes");
    }

    @Test(priority = 2 , description = "Test checkboxes")
    public void testCheckboxes() {
        herokuAppCheckBoxPage = PageFactory.initElements(driver,HerokuAppCheckBoxPage.class);
        // Verify the page header
        String expectedHeader = "Checkboxes";
        String actualHeader = herokuAppCheckBoxPage.getHeaderText();
        Assert.assertEquals(actualHeader, expectedHeader, "Page header does not match!");

        // Check the first checkbox
        herokuAppCheckBoxPage.checkFirstCheckbox();
        Assert.assertTrue(herokuAppCheckBoxPage.isFirstCheckboxSelected(), "Checkbox 1 should be checked!");

        // Uncheck the second checkbox
        herokuAppCheckBoxPage.uncheckSecondCheckbox();
        Assert.assertFalse(herokuAppCheckBoxPage.isSecondCheckboxSelected(), "Checkbox 2 should be unchecked!");
    }

}
