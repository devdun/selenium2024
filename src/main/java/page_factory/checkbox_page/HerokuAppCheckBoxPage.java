package page_factory.checkbox_page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import page_factory.homepage.HomePageXpathContents;
import utils.CommonOperations;

public class HerokuAppCheckBoxPage {
    private WebDriver driver;
    private CommonOperations commonOperations;

    public HerokuAppCheckBoxPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        commonOperations = new CommonOperations(driver);
    }

    @FindBy(xpath = CheckboxXpathContent.HEADER)
    private WebElement header;

    @FindBy(xpath = CheckboxXpathContent.CHECKBOX1)
    private WebElement checkbox1;

    @FindBy(xpath = CheckboxXpathContent.CHECKBOX2)
    private WebElement checkbox2;


// Page Actions

    /**
     * Get the text of the header on the page.
     *
     * @return Header text as a String.
     */
    public String getHeaderText() {
        return header.getText();
    }

    /**
     * Check the first checkbox if it is not already selected.
     */
    public void checkFirstCheckbox() {
        if (!checkbox1.isSelected()) {
            checkbox1.click();
        }
    }

    /**
     * Uncheck the second checkbox if it is already selected.
     */
    public void uncheckSecondCheckbox() {
        if (checkbox2.isSelected()) {
            checkbox2.click();
        }
    }

    /**
     * Verify if the first checkbox is selected.
     *
     * @return true if selected, false otherwise.
     */
    public boolean isFirstCheckboxSelected() {
        return checkbox1.isSelected();
    }

    /**
     * Verify if the second checkbox is selected.
     *
     * @return true if selected, false otherwise.
     */
    public boolean isSecondCheckboxSelected() {
        return checkbox2.isSelected();
    }
}
