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

    /**
     initialize Go by passing the required parameters
     @param driver WebDriver object
     @param js the JavascriptExecutor object
     @param webdriverWait the WebDriverWait object
     */
    public static void initialize(WebDriver driver, JavascriptExecutor js, WebDriverWait webdriverWait){
        webDriver = driver;
        javascriptExecutor = js;
        wait = webdriverWait;
    }

    /**
     * Clicks the target element using Actions
     * @param element the target element
     */
    public static void actionClick(WebElement element) {
        {
            new Actions(webDriver).click(element).perform();
        }
    }

    /**
     * Double-click the target element using Actions
     * @param element the target element
     */
    public static void doubleClick(WebElement element) {
        {
            new Actions(webDriver).doubleClick(element).perform();
        }
    }

    /**
     * Moves to the target element and click using Actions
     * @param element the target element
     */
    public static void moveAndClick(WebElement element) {
        {
            new Actions(webDriver).moveToElement(element).click().perform();
        }
    }

    /**
     * Moves to the target element
     * @param element the target element
     */
    public static void moveToElement(WebElement element) {
        {
            new Actions(webDriver).moveToElement(element);
        }
    }

    /**
     * Moves to the target element, then click and hold
     * @param element the target element
     */
    public static void moveAndClickAndHold(WebElement element) {
        {
            new Actions(webDriver).moveToElement(element).clickAndHold().perform();
        }
    }

    /**
     * Clicks and hold the target element
     * @param element the target element
     */
    public static void clickAndHold(WebElement element) {
        {
            new Actions(webDriver).moveToElement(element).clickAndHold().perform();
        }
    }

    /**
     * @return the system user directory based on the current OS
     */
    public static String getProperty() {
        return location = System.getProperty("user.dir");
    }

    /**
     *
     * @param javaScript script to execute
     * @param element element to execute script on it
     * @return Object
     */
    public static Object javascriptExecutor(String javaScript, WebElement element) {
        return javascriptExecutor.executeScript(javaScript, element);
    }

    /**
     * Scolls down by value of pixels
     * @param value pixels
     */
    public static void scrollDownBy(int value) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        String scrollStatement = "window.scrollBy(0," + value + ")";
        jse.executeScript(scrollStatement);
    }

    /**
     * Scrolls up by value of pixels
     * @param value pixels
     */
    public static void scrollUpBy(int value) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("window.scrollBy(0," + (-1 * value) + ")");
    }

    /**
     * Scrolls right by value of pixels
     * @param value pixels
     */
    public static void scrollRight(int value) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("window.scrollBy("+value+",0)");
    }

    /**
     * Switches to browser tab by index number starting from 0
     * @param index order of the tab starting from 0
     */
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

    /**
     * Switches back to the original tab
     */
    public static void switchBackToTab() {
        webDriver.close();
        webDriver.switchTo().window(currentTab);
    }

    /**
     * Switches back to the window of the original tab
     */
    public static void switchBackToTabWithoutCloseTab() {
        webDriver.switchTo().window(currentTab);
    }

    /**
     * Refreshes the current page
     */
    public static void refreshPage() {
        webDriver.navigate().refresh();
    }

    /**
     * Accepts the active alert
     */
    public static void alertAccept() {
        webDriver.switchTo().alert().accept();
    }

    /**
     * Scrolls to the target element
     * @param element the target element
     */
    public void scrollToElement(WebElement element) {
        Coordinates coordinate = ((Locatable) element)
                .getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();
    }

    /**
     * Scrolls down to the target element
     * @param element the target element
     */
    public static void scrollDownToElement(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scrolls up by 150 pixels until reaching the target element
     * @param element the target element
     */
    public static void scrollUpUntilReachElement(WebElement element) {
        while (!element.isDisplayed()) {
            scrollUpBy(150);
        }
    }

    /**
     * Highlights the target element
     * @param element the target element
     */
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

    /**
     * Waits until the target element becomes accessible
     * @param locator locator of the target element
     * @throws TimeoutException throws exception when not accessible after a time which set in your WebDriver configuration
     */
    public static void waitForElementToBeAccessibleBy(By locator)  throws TimeoutException{
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until the target element becomes visible
     * @param locator locator of the target element
     * @throws TimeoutException throws exception when not visible after a time which set in your WebDriver configuration
     */
    public static void waitForElementToBeVisibleBy(By locator) throws TimeoutException{
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the target element beceoms visible
     * @param element locator of the target element
     * @throws TimeoutException throws exception when not invisible after a time which set in your WebDriver configuration
     */
    public static void waitForElementToBeInVisibleBy(WebElement element)  throws TimeoutException{
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Waits until the target element becomes clickable
     * @param locator locator of the target element
     * @throws TimeoutException throws exception when not clickable after a time which set in your WebDriver configuration
     */
    public static void waitForElementToBeClickableBy(By locator)  throws TimeoutException{
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until the target element becomes clickable
     * @param element locator of the target element
     * @throws TimeoutException throws exception when not clickable after a time which set in your WebDriver configuration
     */
    public static void waitForElementToBeClickable(WebElement element)  throws TimeoutException{
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Selects item from the dropdown by text
     * @param dropDownMenu the dropdown web element
     * @param text text of the target item
     */
    public static void selectByText(WebElement dropDownMenu, String text) {
        Select select = new Select(dropDownMenu);
        select.selectByVisibleText(text);
    }

    /**
     * Keep trying click element until other element becomes visible
     * used when you struggled to click some elements
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
                        screenShot("click try "+counter+"@");
                        getByXpath(getElementXPath(element),true).click();
                    }
                }
            }
        }
    }

    /**
     * Waits until expected visible element becomes invisible
     * @param by the locator of the target element
     */
    public static void waitIfVisible(By by ){
        try{
            waitForElementToBeInVisibleBy(webDriver.findElement(by));}
        catch (Exception ex){
            System.out.println("waitIfVisible() -> "+"Element is not visible");
        }
    }

    /**
     * Snaps a screenshot as a file
     * @param name the screenshot name to be saved in screenshots folder
     * @return File
     */
    public static File screenShot(String name){
        return getShotAsFile(name);
    }

    /**
     * Clears a text from input
     * @param element the target element
     */
    public static void clearText(WebElement element) {
        element.clear();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

    /**
     * Gets the element by its xpath,
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param xpath xpath of the target element
     * @param isClickable true or false
     * @return The target web element
     */
    public static WebElement getByXpath(String xpath, boolean isClickable) {
        By byXpath = By.xpath(xpath);
        waitForElementToBeVisibleBy(byXpath);
        waitForElementToBeAccessibleBy(byXpath);
        if (isClickable) {
            waitForElementToBeClickableBy(byXpath);
        }
        return webDriver.findElement(byXpath);
    }

    /**
     * Gets the element by its id,
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param Id id of the target element
     * @param isClickable true or false
     * @return The target web element
     */
    public static WebElement getById(String Id, boolean isClickable) {
        By byId = By.id(Id);
        waitForElementToBeVisibleBy(byId);
        if (isClickable) {
            waitForElementToBeClickableBy(byId);
        }
        return webDriver.findElement(byId);
    }

    /**
     * Gets the element by its className,
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param className Class name of the target element
     * @param isClickable true or false
     * @return The target web element
     */

    public static WebElement getByClassName(String className, boolean isClickable) {
        By byClassName = By.className(className);
        waitForElementToBeVisibleBy(byClassName);
        if (isClickable) {
            waitForElementToBeClickableBy(byClassName);
        }
        return webDriver.findElement(byClassName);
    }

    /**
     * Gets the element by its cssSelector,
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param cssSelector of the target element
     * @param isClickable true or false
     * @return The target web element
     */
    public static WebElement getByCssSelector(String cssSelector, boolean isClickable) {
        By byCssSelector = By.cssSelector(cssSelector);
        waitForElementToBeVisibleBy(byCssSelector);
        if (isClickable) {
            waitForElementToBeClickableBy(byCssSelector);
        }
        return webDriver.findElement(byCssSelector);
    }

    /**
     * Gets element by tagName, which the target element located ABOVE the referenced web element
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param tagName tag name of the target element
     * @param element the reference element
     * @param isClickable true or false
     * @return The target web element
     */
    public static WebElement getByAboveElement(String tagName, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(tagName)).below(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }

    /**
     * Gets element by tagName, which the target element located BELOW the referenced web element
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param tagName tag name of the target element
     * @param element the reference element
     * @param isClickable true or false
     * @return The target web element
     */
    public static WebElement getByBelowElement(String tagName, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(tagName)).above(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }

    /**
     * Gets the current month on the form MMyy
     * @return Date as a string
     */
    public static String getCurrentDate() {
        java.sql.Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyy");
        return (simpleDateFormat.format(date));
    }

    /**
     * Gets element by tagName, which the target element located RIGHT to the referenced web element
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param tagName tag name of the target element
     * @param element the reference element
     * @param isClickable true or false
     * @return The target web element
     */
    public static WebElement getByRightElement(String tagName, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(tagName)).toLeftOf(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }

    /**
     * Gets element by tagName, which the target element located LEFT to the referenced web element
     * Waits for element to be visible and accessible
     * Waits for element to be clickable if isClickable is true
     * @param tagName tag name of the target element
     * @param element the reference element
     * @param isClickable true or false
     * @return The target web element
     */
    public static WebElement getByLeftElement(String tagName, WebElement element, boolean isClickable) {
        By relativeLocator = RelativeLocator.with(By.tagName(tagName)).toRightOf(element);
        waitForElementToBeVisibleBy(relativeLocator);
        waitForElementToBeAccessibleBy(relativeLocator);
        if (isClickable) {
            waitForElementToBeClickableBy(relativeLocator);
        }
        return webDriver.findElement(relativeLocator);
    }

    /**
     * Pasts text into input
     * @param element the target element to past text to it
     */
    public static void paste(WebElement element){
        Actions a = new Actions(webDriver);
        a.keyDown(element,Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
    }

    /**
     * Navigates back
     */
    public static void back() {
        webDriver.navigate().back();
    }

    /**
     * Waits for window for timeout milliseconds
     * @param timeout number od milliseconds to wait
     * @return Strint
     */
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
    }

    public static void getWindowHandles() {
        vars.put("window_handles", webDriver.getWindowHandles());
    }

    /**
     * Switch to window by tag
     * @param tag the tag you set before using setWindowTag(tag)
     */
    public static void switchToWindowByTag(String tag) {
        vars.put(tag, waitForWindow(2000));
        setWindowTag("root");
        webDriver.switchTo().window(vars.get(tag).toString());
    }

    /**
     * Set a tag for the current window
     * @param tag of the target window
     */
    public static void setWindowTag(String tag) {
        vars.put(tag, webDriver.getWindowHandle());
    }

    /**
     * Switches to iFrame by tag name
     * @param tagName of the target iFrame
     */
    public static void switchToFrame(String tagName) {
        webDriver.switchTo().frame(webDriver.findElement(By.tagName(tagName)));
    }


    public static void switchToFrameByIndex(int index) {
        webDriver.switchTo().frame(index);
    }

    /**
     * Switches for iFrame by its xpath
     * @param xPath of the target element
     */
    public static void switchToFrameByXpath(String xPath) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath(xPath))));
    }

    /**
     * Switches to the default page content (iFrame)
     */
    public static void switchBackToMainFrame() {
        webDriver.switchTo().defaultContent();
    }

    /**
     * Opens page Url
     * @param url the target url
     */
    public static void openUrl(String url) {
        webDriver.get(url);
    }

    /**
     * Gets all children of target element by its xpath
     * @param xpath of the target element
     * @return List<WebElement>
     */
    public static List<WebElement> getAllChildrenByXpath(String xpath){
        return Go.getByXpath(xpath,false).findElements(By.xpath("./child::*"));
    }

    /**
     * Gets full xpath of the target element
     * @param element the target element
     * @return String
     */
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

    /**
     * Gets screenshot as a file
     * @param shotName name of the image file
     * @return File
     */
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

    /**
     * Gets a screenshot as based64 format
     * @return String
     */
    public static String getShotAsBase64() {
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
    }
}