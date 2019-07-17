package swingtest;

import core.Log4RQ;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class TreeViewTest {

    private RemoteWebDriver driver;

    private String getText(WebElement wElem){
        String szRet = null;
        try{
            szRet = wElem.getText();
        } catch(Exception e){

        }

        return szRet;
    }

    private String getTagName(WebElement wElem){
        String szRet = null;
        try{
            szRet = wElem.getTagName();
        } catch(Exception e){

        }

        return szRet;
    }

    private String getClass(WebElement wElem){
        Class szRet = null;
        try{
            szRet = wElem.getClass();
        } catch(Exception e){

        }

        return szRet.toString();
    }

    private String getAttribute(WebElement wElem, String attrib){
        String szRet = null;
        try{
            szRet = wElem.getAttribute(attrib);
        } catch(Exception e){

        }

        return szRet;
    }

    private String getCssValue(WebElement wElem, String s){
        String szRet = null;
        try{
            szRet = wElem.getAttribute(s);
        } catch(Exception e){
        }
        return szRet;
    }

    private Dimension getDimension(WebElement wElem){
        Dimension dimRet = null;
        try{
            dimRet = wElem.getSize();
        } catch(Exception e){
        }
        return dimRet;
    }

    private Point getLocation(WebElement wElem){
        Point ptRet = null;
        try{
            ptRet = wElem.getLocation();
        } catch(Exception e){

        }
        return ptRet;
    }

    private List<String> listComboOptions(WebElement weCombo){
        List<String> lOptions = new ArrayList<String>();

        try{
            List<WebElement> presets = weCombo.findElements(By.cssSelector(".::all-options"));
            for(WebElement preset: presets){
                lOptions.add(preset.getText());
                System.out.println(preset.getText());
            }

        } catch(Exception e){
            lOptions.clear();
        }
        //WebElement comboPresets = driver.findElement(By.cssSelector("combo-box"));


        return lOptions;
    }

    private WebElement findWebElementByClass(RemoteWebDriver driver, String szCss){

        try{
            return driver.findElement(By.cssSelector(szCss));
        } catch(Exception e){
            return null;
        }

    }

    private int printOutChildsInfo(List<WebElement> lElems){
        int i = 0;
        for(WebElement elem : lElems){
            //String className = this.getClass(tab);
            String className = "";
            String szText = this.getText(elem);
            String szTagName = this.getTagName(elem);
            Point pt = this.getLocation(elem);
            Dimension dm = this.getDimension(elem);
            String att1 = this.getAttribute(elem, "visible");
            String cssVal1 = this.getCssValue(elem, "visible");
            String name = this.getAttribute(elem, "name");
            String szType = this.getAttribute(elem, "type");
            String tag2 = this.getAttribute(elem, "parent");
            String childs = this.getCssValue(elem, "childs");
            i++;
            System.out.println("ID: " + i +
                    "   ~ NAME: " + className +
                    "   ~ Text: " + szText +
                    "   ~ TagName: " + szTagName  +
                    "   ~ Visible: " + att1 + "(" + cssVal1 + ")" +
                    "   ~ point: " + pt.toString() +
                    "   ~ Dimension: " + dm.toString() +
                    "   ~ name: " + name);
        }
        return lElems.size();
    }

    public void launchApplication(String url) throws MalformedURLException {
        driver = new RemoteWebDriver(
                new URL("http://" + url),
                new DesiredCapabilities("java", "1.0", org.openqa.selenium.Platform.ANY)
        );

        String title = driver.getTitle();
        log("Found title is " + title);

        WebElement wTree = driver.findElement(By.cssSelector("tree"));
        List<WebElement> elems = driver.findElements(By.cssSelector("*"));
//        printOutChildsInfo(elems);

        List<WebElement> el02 = driver.findElements(By.cssSelector("layered-pane"));
        List<WebElement> el03 = driver.findElements(By.cssSelector("toggle-button"));

        WebElement wElem02 = this.findWebElementByClass(driver,"*[type='com.ur.g5.polyscope.WelcomeScreen']");
        WebElement wElellm03 = this.findWebElementByClass(driver,"*[type*='WelcomeScreen']");

        Set<String> szHandles = driver.getWindowHandles();


        // Get Cobombo  elements
        List<String> options = this.listComboOptions(driver.findElement(By.cssSelector("combo-box")));


//        new Actions(driver).moveToElement(el02.get(0), 20, 5).perform();



        
    }

    private void log(String sz){
        System.out.println(Log4RQ.ANSI_BLUE + sz + Log4RQ.ANSI_RESET);
    }

    public static void main(String[] args) {
        String szURL = "127.0.0.1:44444";
        if (args.length > 0){
            System.out.println("IP to connect to is " + args[0]);
            szURL = args[0];
        }

        TreeViewTest app = new TreeViewTest();
        try {
            app.launchApplication(szURL);
        } catch(Exception exp){
            System.out.println("Exception occurred while connecting to JavaDriver [ "
                    + exp.getMessage() + " ]");
        }
    }


}
