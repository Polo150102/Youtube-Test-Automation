package com.globant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest {
    WebDriver driver = new ChromeDriver();
    SoftAssert softAssert = new SoftAssert();


    @Test
    public void testSearchTestNGYoutube() {
        driver.get("https://www.youtube.com/ ");

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));


        List<WebElement> searchResults = searchAndGetResults("TestNG", wait);
        List<String> cantidadResultado = new ArrayList<String>();

        System.out.println(searchResults.size());

        // Validar cada resultado de búsqueda usando TestNG assertions
        for (WebElement result : searchResults) {
            String title = result.getText().trim().toLowerCase(); // Convertir a minúsculas para comparación sin distinción entre mayúsculas y minúsculas
            if (title.contains("testng")) {
                softAssert.assertTrue(title.contains("testng"), "El título del video no contiene 'TestNG'");

                cantidadResultado.add(title);
            }
        }

        //La cantidad de resultados al buscar TestNG debe ser mayor a 5(deben haber videos mayores a 5 que hablen de TestNG)
        softAssert.assertTrue(cantidadResultado.size() > 5);

        //driver.close();

        softAssert.assertAll();
    }


    @Test
    public void testSearchSeleniumYoutube() {
        driver.get("https://www.youtube.com/ ");

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        List<WebElement> searchResults = searchAndGetResults("Selenium", wait);

        List<String> cantidadResultado = new ArrayList<String>();

        // Validar cada resultado de búsqueda usando TestNG assertions
        for (WebElement result : searchResults) {
            String title = result.getText().trim().toLowerCase(); // Convertir a minúsculas para comparación sin distinción entre mayúsculas y minúsculas
            if (title.contains("selenium")) {
                //System.out.println(title);
                softAssert.assertTrue(title.contains("selenium"), "El título del video no contiene 'Selenium'");
                cantidadResultado.add(title);
            }
        }

        //La cantidad de resultados al buscar TestNG debe ser mayor a 5(deben haber videos mayores a 5 que hablen de Selenium)
        softAssert.assertTrue(cantidadResultado.size() > 5, "No hay más de 5 videos que hablen de 'Selenium'");

        //driver.close();
        softAssert.assertAll();
    }

    //Implement a new Test validating that the results
    // from the “Selenium” Search are different from the “TestNG” Search,
    // use multiple assertions on this test.
    @Test
    public void testDifferentResults()
    {
        driver.get("https://www.youtube.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        List<WebElement> seleniumResults = searchAndGetResults("Selenium", wait);

        List<String> cantidadselenium = new ArrayList<String>();


        // Validar cada resultado de búsqueda usando TestNG assertions
        for (WebElement result : seleniumResults) {
            String title = result.getText().trim().toLowerCase(); // Convertir a minúsculas para comparación sin distinción entre mayúsculas y minúsculas
            if (title.contains("selenium")) {
                softAssert.assertTrue(title.contains("selenium"), "El título del video no contiene 'Selenium'");

                cantidadselenium.add(title);
            }
        }

        List<WebElement> testNGResults = searchAndGetResults("TestNG",wait);

        List<String> cantidadTestng = new ArrayList<String>();

        // Validar cada resultado de búsqueda usando TestNG assertions
        for (WebElement result : testNGResults) {
            String title = result.getText().trim().toLowerCase(); // Convertir a minúsculas para comparación sin distinción entre mayúsculas y minúsculas
            if (title.contains("testng")) {
                softAssert.assertTrue(title.contains("testng"), "El título del video no contiene 'TestNG'");

                cantidadTestng.add(title);
            }
        }

        softAssert.assertNotEquals(cantidadselenium.size(),cantidadTestng,"Results for TestNG and Selenium are the same");
        int totalComparaciones = 15;

        // Assert that the results are different for all combinations

        // Assert that the results are different for the first 10 results
        for(int i=0;i<totalComparaciones;i++){
            String tituloSelenium = cantidadselenium.get(i).trim().toLowerCase();
            for(int j=0;j<totalComparaciones;j++)
            {
                String tituloTestNG = cantidadTestng.get(j).trim().toLowerCase();
                softAssert.assertNotEquals(tituloSelenium,tituloTestNG,"Results for TestNG and Selenium should be different");
            }
        }

        driver.close();
        softAssert.assertAll();
    }

    //Add a test which will fail, expecting the web app to launch
    // the Log in form right after searching for “Log In” on the search section.
    @Test
    public void testLoginRedirect()
    {
        driver.get("https://www.youtube.com/");

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        WebElement searchBox = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/div/ytd-masthead/div[4]/div[2]/ytd-searchbox/form/div[1]/div[1]/input"));
        searchBox.sendKeys("Log In");

        WebElement searchBtnClick = wait.until(ExpectedConditions.elementToBeClickable(By.id("search-icon-legacy")));
        searchBtnClick.click();
        //Manera tradicional de ingresar al log in


        try {

            // Si el elemento está presente, agrega afirmaciones para verificar condiciones específicas
            // Por ejemplo, verificar si el formulario de correo electrónico está visible
            WebElement emailForm = driver.findElement(By.cssSelector("#identifierId"));
            softAssert.assertTrue(emailForm.isDisplayed(),"Unexpected: No se muestra el formulario para ingresar email.");
            emailForm.sendKeys("aleodaf@gmail.com");

            WebElement logInBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#identifierNext > div > button > span")));
            softAssert.assertTrue(logInBox.isDisplayed(), "Unexpected: Password field is not displayed");
            logInBox.click();

        } catch (Exception e) {
            // Se espera que el elemento no esté presente, continuar con el resto del código...
            softAssert.fail("El formulario para iniciar sesion en Youtube no está presente después de buscar 'Log In'.");
        }
        finally {
            softAssert.assertAll();
        }
    }

    private List<WebElement> searchAndGetResults(String query, WebDriverWait wait)
    {

        WebElement searchBox = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/div/ytd-masthead/div[4]/div[2]/ytd-searchbox/form/div[1]/div[1]/input"));
        searchBox.clear();
        searchBox.sendKeys(query);

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("search-icon-legacy")));
        searchButton.click();

        // Espera explícita de 5 segundos
        try {
            Thread.sleep(5000); // Alternativamente, puedes usar TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> resultadosObtenidos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#video-title")));

        return resultadosObtenidos;
    }
}

