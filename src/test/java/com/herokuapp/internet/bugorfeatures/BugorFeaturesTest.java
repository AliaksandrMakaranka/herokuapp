package com.herokuapp.internet.bugorfeatures;

import com.herokuapp.internet.pages.bugorfaatures.DragAndDropPage;
import com.herokuapp.internet.pages.bugorfaatures.IFramePage;
import com.herokuapp.internet.pages.bugorfaatures.NestedFramesPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BugorFeaturesTest {
    private WebDriver driver;
    private final Logger LOG = LoggerFactory.getLogger(BugorFeaturesTest.class);

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        LOG.info("WebDriver initialized");
    }

    @Test
    public void testDragAndDrop() {
        LOG.info("Executing drag and drop test");

        DragAndDropPage dragAndDropPage = new DragAndDropPage(driver);
        dragAndDropPage.open();

        String initialColumnAHeader = dragAndDropPage.getColumnAHeader();
        String initialColumnBHeader = dragAndDropPage.getColumnBHeader();

        LOG.info("Initial headers - A: {}, B: {}", initialColumnAHeader, initialColumnBHeader);

        dragAndDropPage.dragAToB();

        String newColumnAHeader = dragAndDropPage.getColumnAHeader();
        String newColumnBHeader = dragAndDropPage.getColumnBHeader();

        LOG.info("After drag - A: {}, B: {}", newColumnAHeader, newColumnBHeader);
    }

    @Test
    public void testNestedFrames() {
        LOG.info("Executing nested frames test");

        NestedFramesPage nestedFramesPage = new NestedFramesPage(driver);
        nestedFramesPage.open();

        assertEquals("LEFT", nestedFramesPage.getFrameText("left"));
        assertEquals("MIDDLE", nestedFramesPage.getFrameText("middle"));
        assertEquals("RIGHT", nestedFramesPage.getFrameText("right"));
        assertEquals("BOTTOM", nestedFramesPage.getFrameText("bottom"));
    }

    @Test
    public void testIFrame() {
        LOG.info("Executing iframe test");

        IFramePage iFramePage = new IFramePage(driver);
        iFramePage.open();

        String initialText = iFramePage.getEditorText();
        LOG.info("Initial editor text: {}", initialText);

        String newText = "Hello, this is a test for iFrame!";
        iFramePage.setEditorText(newText);

        String updatedText = iFramePage.getEditorText();
        LOG.info("Updated editor text: {}", updatedText);

        assertEquals(newText, updatedText);
    }

    @AfterEach
    public void tearDown() {
        LOG.info("Closing WebDriver");
        if (driver != null) {
            driver.quit();
        }
    }
}