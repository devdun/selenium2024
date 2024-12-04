package page_factory.input_page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HerokuAppInputPage {
    private WebDriver driver;

    // Constructor
    public HerokuAppInputPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locators using @FindBy annotation
    @FindBy(xpath = InputPageXpathContent.HEADER)
    private WebElement header;

    @FindBy(xpath = InputPageXpathContent.INPUT_FIELD)
    private WebElement inputField;

    // Page Actions

    /**
     * Get the text of the page header.
     *
     * @return Header text as a String.
     */
    public String getHeaderText() {
        return header.getText();
    }

    /**
     * Enter a value into the input field.
     *
     * @param value The value to be entered (string or numeric).
     */
    public void enterValue(String value) {
        inputField.clear();
        inputField.sendKeys(value);
    }

    /**
     * Get the current value from the input field.
     *
     * @return The value entered in the input field.
     */
    public String getInputValue() {
        return inputField.getAttribute("value");
    }
}

