package utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitForProxyElement implements ExpectedCondition<WebElement> {

    private final WebElement proxy;

    public WaitForProxyElement(WebElement proxy) {
        this.proxy = proxy;
    }

    @Override
    public WebElement apply(WebDriver d) {
        try {
            proxy.isDisplayed();
        } catch (NoSuchElementException e) {
            return null;
        }
        return proxy;
    }

}

