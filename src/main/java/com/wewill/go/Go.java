package com.wewill.go;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Go {
    public static String testRunId;
    public static String location;
    public static String currentTab;
    private static Map<String, Object> vars;
    private static WebDriver webDriver;
    private static JavascriptExecutor javascriptExecutor;
    private static WebDriverWait wait;
    public static void initialize(WebDriver driver, JavascriptExecutor js, WebDriverWait webdriverWait){
        webDriver = driver;
        javascriptExecutor = js;
        wait = webdriverWait;
    }
    public static void click(WebElement element) {
        {
            new Actions(webDriver).click(element).perform();
        }
    }


    public static void doubleClick(WebDriver driver, WebElement element) {
        {
            new Actions(driver).doubleClick(element).perform();
        }
    }

    public static void moveAndClick(WebElement element) {
        {
            new Actions(webDriver).moveToElement(element).click().perform();
        }
    }

    public static void moveToElement(WebElement element) {
        {
            new Actions(webDriver).moveToElement(element);
        }
    }

    public static void moveAndClickAndHold(WebDriver driver, WebElement element) {
        {
            new Actions(driver).moveToElement(element).clickAndHold().perform();
        }
    }

    public static String getProperty() {
        return location = System.getProperty("user.dir");
    }

    public static Object javascriptExecutor(String s, WebElement usedProductAllMenuItem) {
        return javascriptExecutor.executeScript(s, usedProductAllMenuItem);
    }
    public static void scrollDownBy(int value) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        String scrollStatement = "window.scrollBy(0," + value + ")";
        jse.executeScript(scrollStatement);
    }

    public static void scrollUpBy(int value) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("window.scrollBy(0," + (-1 * value) + ")");
    }

    public static void scrollRight(int value) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("window.scrollBy("+value+",0)");
    }

    public static void switchToTabByIndex(int index) {
        currentTab = webDriver.getWindowHandle();
        String targetTab=currentTab;
        Set<String> handlers = webDriver.getWindowHandles();
        Iterator iterator = handlers.iterator();
        for(int i=0;i<index;i++) {
            targetTab= iterator.next().toString();
        }
        webDriver.switchTo().window(targetTab);
    }

    public static void switchBackToTab() {
        webDriver.close();
        webDriver.switchTo().window(currentTab);
    }

    public static void switchBackToTabWithoutCloseTab() {
        webDriver.switchTo().window(currentTab);
    }

    public static void refreshPage() {
        webDriver.navigate().refresh();
    }

    public static void alertAccept() {
        webDriver.switchTo().alert().accept();
    }

    public void scrollToElement(WebElement element) {
        Coordinates coordinate = ((Locatable) element)
                .getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();
    }

    public static void scrollDownToElement(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

//method to highlight

    public static void scrollUpUntilReachElement(WebElement element) {
        while (!element.isDisplayed()) {
            scrollUpBy(150);
        }
    }

    public static void highlightElement(WebElement element) {
        for (int i = 0; i < 4; i++) {
            javascriptExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                    element, "color: yellow; border: 4px solid blue;");
            javascriptExecutor.executeScript("arguments[0].setAttribute('style’' arguments[1]);",
                    element, "");
        }
    }


    /**
     * @return focused element
     */
    public static WebElement getFocusedElement() {
        return webDriver.switchTo().activeElement();
    }

    public static void waitForElementToBeAccessibleBy(By locator)  throws TimeoutException{
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForElementToBeVisibleBy(By locator) throws TimeoutException{
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementToBeInVisibleBy(WebElement element)  throws TimeoutException{
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForElementToBeClickableBy(By locator)  throws TimeoutException{
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    public static void waitForElementToBeClickable(WebElement element)  throws TimeoutException{
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    public static void selectByText(WebElement dropDownMenu, String text) {
        Select select = new Select(dropDownMenu);
        select.selectByVisibleText(text);
    }

    /**
     * Keep trying click element until other element becomes visible
     *
     * @param element the target element to click
     * @param locatorToBeVisible locator of the element to be visible
     */

    public static void clickUntilVisibilityOfBy(@NotNull WebElement element, @NotNull By locatorToBeVisible) {
        int counter=1;
        try {
            element.click();
            System.out.println("Tries ="+counter);
            waitForElementToBeVisibleBy(locatorToBeVisible);
        } catch (TimeoutException exception){
            screenShot("click try "+counter+++"@");
            Actions action= new Actions(webDriver);
            action.click(element).perform();
            try{
                System.out.println("Tries ="+counter);
                waitForElementToBeVisibleBy(locatorToBeVisible);
            } catch (TimeoutException exception1){
                screenShot("click try "+counter+++"@");
                Go.javascriptExecutor("arguments[0].click();",element);
                try{
                    System.out.println("Tries ="+counter);
                    waitForElementToBeVisibleBy(locatorToBeVisible);
                } catch (TimeoutException exception2){
                    screenShot("click try "+counter+++"@");
                    Go.javascriptExecutor("arguments[0].scrollIntoView(true);",element);
                    Go.javascriptExecutor("arguments[0].click();",element);
                    try{
                        System.out.println("Tries ="+counter);
                        waitForElementToBeVisibleBy(locatorToBeVisible);
                    } catch (Exception exception3){
                        screenShot("click try "+counter+++"@");
                        getByXpath(getElementXPath(element),true).click();
                    }
                }
            }
        }
    }

    public static void waitIfVisible(By by ){
        try{
            waitForElementToBeInVisibleBy(webDriver.findElement(by));}
        catch (Exception ex){
            System.out.println("waitIfVisible() -> "+"Element is not visible");
        }
    }
    public static File screenShot(String name){
        return getShotAsFile(name);
    }
    public static void clearText(WebElement element) {
        element.clear();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

    public static WebElement getByXpath(String xpath, boolean isClickable) {
        By byXpath = By.xpath(xpath);
        waitForElementToBeVisibleBy(byXpath);
        waitForElementToBeAccessibleBy(byXpath);
        if (isClickable) {
            waitForElementToBeClickableBy(byXpath);
        }
        return webDriver.findElement(byXpath);
    }

    public static WebElement getById(String Id, boolean isClickable) {
        By byId = By.id(Id);
        waitForElementToBeVisibleBy(byId);
        if (isClickable) {
            waitForElementToBeClickableBy(byId);
        }
        return webDriver.findElement(byId);
    }

    public static WebElement getByClassName(String className, boolean isClickable) {
        By byClassName = By.className(className);
        waitForElementToBeVisibleBy(byClassName);
        if (isClickable) {
            waitForElementToBeClickableBy(byClassName);
        }
        return webDriver.findElement(byClassName);
    }

    public static WebElement getByCssSelector(String cssSelector, boolean isClickable) {
        By byCssSelector = By.cssSelector(cssSelector);
        waitForElementToBeVisibleBy(byCssSelector);
        if (isClickable) {
            waitForElementToBeClickableBy(byCssSelector);
        }
        return webDriver.findElement(byCssSelector);
    }

    public static WebElement getByAboveElement(String tagName, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(tagName)).below(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }

    public static WebElement getByBelowElement(String targetTag, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(targetTag)).above(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }

    public static String getCurrentDate() {
        java.sql.Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyy");
        return (simpleDateFormat.format(date));
    }

    public static WebElement getByRightElement(String tagName, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(tagName)).toLeftOf(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }

    public static WebElement getByLeftElement(String tagName, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(tagName)).toRightOf(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }
    public static void paste(WebElement element){
        Actions a = new Actions(webDriver);
        a.keyDown(element,Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
    }

    public static void back() {
        webDriver.navigate().back();
    }

    public static String waitForWindow(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<String> whNow = webDriver.getWindowHandles();
        Set<String> whThen = (Set<String>) vars.get("window_handles");
        if (whNow.size() > whThen.size()) {
            whNow.removeAll(whThen);
        }
        return whNow.iterator().next();
    }


    public static void switchToTab(){
        //Store the ID of the original window
        String originalWindow = webDriver.getWindowHandle();

        //Loop through until we find a new window handle
        for (String windowHandle : webDriver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                webDriver.switchTo().window(windowHandle);
                break;
            }
        }
        //Wait for the new tab to finish loading content
        //wait.until(titleIs("XXX"));
    }

    public static void getWindowHandles() {
        vars.put("window_handles", webDriver.getWindowHandles());
    }

    public static void switchToWindowByTag(String tag) {
        vars.put(tag, waitForWindow(2000));
        setWindowTag("root");
        webDriver.switchTo().window(vars.get(tag).toString());
    }

    public static void setWindowTag(String tag) {
        vars.put(tag, webDriver.getWindowHandle());
    }

    public static void switchToFrame(String frame) {
        webDriver.switchTo().frame(webDriver.findElement(By.tagName(frame)));
    }


    public static void switchToFrameByIndex(int index) {
        webDriver.switchTo().frame(index);
    }

    public static void switchToFrameByXpath(String xPath) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath(xPath))));
    }

    public static void switchBackToMainFrame() {
        webDriver.switchTo().defaultContent();
    }

    public static void openUrl(String url) {
        webDriver.get(url);
    }
    public static List<WebElement> getAllChildrenByXpath(String xpath){
        return Go.getByXpath(xpath,false).findElements(By.xpath("./child::*"));
    }

    public static String getElementXPath(WebElement element) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "function getXPath(elt){" +
                        "var path = '';" +
                        "for (; elt && elt.nodeType == 1; elt = elt.parentNode) {" +
                        "idx = getElementIdx(elt);" +
                        "xname = elt.tagName;" +
                        "if (idx > 1) xname += '[' + idx + ']';" +
                        "path = '/' + xname + path;" +
                        "}" +
                        "return path;" +
                        "}" +
                        "function getElementIdx(elt){" +
                        "var count = 1;" +
                        "for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling) {" +
                        "if(sib.nodeType == 1 && sib.tagName == elt.tagName) count++;" +
                        "}" +
                        "return count;" +
                        "}" +
                        "return getXPath(arguments[0]).toLowerCase();", element);
    }

    public static File getShotAsFile(String shotName) {
        final String folder ="screenshots/";
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String currentTime = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss-S").format(new Date(System.currentTimeMillis()));
        File shot= new File(folder+shotName+currentTime +".png");
        try {
            FileUtils.copyFile(scrFile, shot);
        } catch (IOException ex) {
            Logger.getLogger(Go.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shot;
    }

    public static String getShotAsBase64() {
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
    }
}