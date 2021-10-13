package com.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TakesScreenshot;

public class DragAndDropTest {
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", 
				"/home/voalle/Downloads/ChromeDriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//Espera tudo estar carregado na página para fazer uma ação
		driver.get("https://jqueryui.com/resources/demos/droppable/default.html");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	//Drag and Drop: Valide o texto dos dois componentes disponibilizados em tela, arraste o componente 1 para o 2, valide o texto do 2.
	@Test
	public void testDragAndDrop() throws IOException {

		File scrnShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrnShot, new File("target/before.jpg"));

		WebElement origin = driver.findElement(By.id("draggable"));
		assertEquals("Drag me to my target", origin.getText());

		WebElement destiny = driver.findElement(By.id("droppable"));
		assertEquals("Drop here", destiny.getText());

		new Actions(driver).dragAndDrop(origin, destiny).perform();

		scrnShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrnShot, new File("target/after.jpg"));

		assertEquals("Dropped!", destiny.getText());
	}
}
