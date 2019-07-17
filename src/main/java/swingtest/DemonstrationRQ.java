package swingtest;

import core.Log4RQ;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DemonstrationRQ {

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

    private void printWebElementInfo(WebElement elem){
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
        String CText = this.getCssValue(elem, "CText");
        String accessibleName = this.getCssValue(elem, "accessibleName");
        String fieldName = this.getCssValue(elem, "fieldName");
        String ctxAccessibleName = this.getCssValue(elem, "accessibleContext.accessibleName");
        System.out.println(
                "   ~ NAME: " + className +
                "   ~ Text: " + szText +
                "   ~ TagName: " + szTagName  +
                "   ~ Visible: " + att1 + "(" + cssVal1 + ")" +
                "   ~ point: " + pt.toString() +
                "   ~ Dimension: " + dm.toString() +
                "   ~ name: " + name +
                "   ~ CText: " + CText +
                "   ~ accessibleName: " + accessibleName +
                "   ~ fieldName: " + fieldName +
                "   ~ ctxAccessibleName: " + ctxAccessibleName);

    }

    private int printOutChildsInfo(List<WebElement> lElems){
        int i = 0;
        for(WebElement elem : lElems){
            this.printWebElementInfo(elem);
            i++;
        }
        return lElems.size();
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

        DemonstrationRQ app = new DemonstrationRQ();
        try {
            app.javaDriverDemo(szURL);
        } catch(Exception exp){
            System.out.println("Exception occurred while connecting to JavaDriver [ "
                    + exp.getMessage() + " ]");
        }
    }

    public void javaDriverDemo(String url) throws MalformedURLException {
        driver = new RemoteWebDriver(
                new URL("http://" + url),
                new DesiredCapabilities("java", "1.0", Platform.ANY)
        );

        String title = driver.getTitle();
        log("Found title is " + title);

        // Find the header element which hold the Program button
        WebElement weHeader = this.findWebElementByClass(driver,"*[type*='HeaderMenu']");

        // Find the Program button and click it
        WebElement weButton = weHeader.findElement(By.cssSelector("toggle-button[CText='Program']"));
        weButton.click();

        // Once click find the Program tab which hold the URCAps program functions
        WebElement weProgram = this.findWebElementByClass(driver,"*[type*='ProgramTab']");

        // Find the URCaps action list
        //WebElement weAction = weProgram.findElement(By.cssSelector("*[type*='AddNodeStructureView'])"));
        WebElement weUrCaps = weProgram.findElement(By.cssSelector("toggle-button[CText*='URCaps']"));
        weUrCaps.click();
        WebElement weInsertion = weProgram.findElement(By.cssSelector("button[CText*='Insertion']"));
        weInsertion.click();


        //
        //  TREEVIEW interaction
        //
        WebElement weTree = weProgram.findElement(By.cssSelector("tree"));
        List<WebElement> teElems = weTree.findElements(By.cssSelector(".::all-nodes"));

        this.printOutChildsInfo(teElems);

        for(WebElement treeElem : teElems){
            if(treeElem.getText().compareTo("Insertion") == 0){
                treeElem.click();
            }
        }

        // Click Linear
        WebElement wBtnLinear = weProgram.findElement(By.cssSelector("button[CText*='Linear']"));
        wBtnLinear.click();


        //WebElement weAction = weProgram.findElement(By.cssSelector("*[type*='AddNodeStructureView'])"));

        // Add additional element
        weInsertion = weProgram.findElement(By.cssSelector("button[CText*='Force<br/>Control']"));
        weInsertion.click();

        weInsertion = weProgram.findElement(By.cssSelector("button[CText*='Force Event']"));
        weInsertion.click();

        weInsertion = weProgram.findElement(By.cssSelector("button[CText*='Collision<br/>Detection']"));
        weInsertion.click();

        log("END TESTING");
    }

}