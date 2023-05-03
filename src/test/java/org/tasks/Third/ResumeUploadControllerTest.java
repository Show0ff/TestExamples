package org.tasks.Third;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResumeUploadControllerTest {

    private static final String FILE = "file";
    private static final String PATH_TO_CHROME_DRIVER = "D:\\chromedriver.exe";
    private static final String BASE_URL = "http://localhost:8080";
    private static final String TEST_RESOURCES_PATH = "/src/test/resources/";
    private static final String REMOTE_ALLOW_ORIGINS = "--remote-allow-origins=*";
    private static final String BUTTON = "button";
    private static final String TEST_1_PNG = "Test1.png";
    private static final String INVALID_FILE_XYZ = "invalidFile.xyz";
    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String TEST_DOC_DOCX = "testDoc.docx";
    private static final String SRC_MAIN_RESOURCES_CV_TEST_1_PNG = "src/main/resources/CV/Test1.png";
    private static final String SRC_MAIN_RESOURCES_CV_TEST_DOCX = "src/main/resources/CV/testDoc.docx";


    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty(WEBDRIVER_CHROME_DRIVER, PATH_TO_CHROME_DRIVER);

        ChromeOptions options = new ChromeOptions();
        options.addArguments(REMOTE_ALLOW_ORIGINS);

        driver = new ChromeDriver(options);


    }

    @AfterEach
    public void tearDown() {
        File uploadedFile = new File(SRC_MAIN_RESOURCES_CV_TEST_1_PNG);
        File uploadedFile2 = new File(SRC_MAIN_RESOURCES_CV_TEST_DOCX);
        if (uploadedFile.exists()) {
            uploadedFile.delete();
        }
        if (uploadedFile2.exists()) {
            uploadedFile2.delete();
        }
        driver.quit();
    }

    @Test
    public void ElementsPresence() {
        driver.get(BASE_URL);
        WebElement filePathInput = driver.findElement(By.name("file"));

        WebElement submitButton = driver.findElement(By.tagName("button"));

        assertNotNull(filePathInput);
        assertNotNull(submitButton);
    }

    @Test
    public void submitWithInvalidFile() {
        driver.get(BASE_URL);
        WebElement filePathInput = driver.findElement(By.name(FILE));
        WebElement submitButton = driver.findElement(By.tagName(BUTTON));

        File testFile = new File(System.getProperty("user.dir") + TEST_RESOURCES_PATH + TEST_1_PNG);
        filePathInput.sendKeys(testFile.getAbsolutePath());
        submitButton.click();

        WebElement messageElement = driver.findElement(By.xpath("//div[contains(text(), 'Фото успешно загружено')]"));
        assertNull(messageElement);
    }


    @Test
    public void submitWithValidFile() {
        driver.get(BASE_URL);
        WebElement filePathInput = driver.findElement(By.name(FILE));
        WebElement submitButton = driver.findElement(By.tagName(BUTTON));

        File testFile = new File(System.getProperty("user.dir") + TEST_RESOURCES_PATH + TEST_DOC_DOCX);
        filePathInput.sendKeys(testFile.getAbsolutePath());
        submitButton.click();

        WebElement messageElement = driver.findElement(By.xpath("//div[contains(text(), 'Фото успешно загружено')]"));
        assertNotNull(messageElement);
    }


    @Test
    public void SubmitWithInvalidFilePath() {
        ResumeUploadController resumeUploadControllerMock = mock(ResumeUploadController.class);
        RedirectAttributes redirectAttributesMock = mock(RedirectAttributes.class);
        MultipartFile multipartFileMock = mock(MultipartFile.class);

        when(multipartFileMock.isEmpty()).thenReturn(false);
        when(multipartFileMock.getOriginalFilename()).thenReturn(INVALID_FILE_XYZ);
        doThrow(new InvalidArgumentException("Ошибка: Некорректный путь к файлу."))
                .when(resumeUploadControllerMock)
                .handleFileUpload(multipartFileMock, redirectAttributesMock);

        assertThrows(InvalidArgumentException.class, () ->
                resumeUploadControllerMock.handleFileUpload(multipartFileMock, redirectAttributesMock));

        verify(redirectAttributesMock, never())
                .addFlashAttribute("message", "Фото успешно загружено: " + multipartFileMock.getOriginalFilename());
    }





}
