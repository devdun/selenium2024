package page_factory.homepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonOperations;

public class HerokuAppHomePage {
    private WebDriver driver;
    private CommonOperations commonOperations;

    public HerokuAppHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        commonOperations = new CommonOperations(driver);
    }

    @FindBy(linkText = HomePageXpathContents.CHEBOXES_LINK)
    private WebElement checkbox_link;

    @FindBy(linkText = HomePageXpathContents.INPUT_LINK)
    private WebElement input_link;

    public void clickCheckBoxLink(){
        checkbox_link.click();
    }

    public void clickInputLink(){
        input_link.click();
    }
}
