package com.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CacheTest {
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", 
				"/home/voalle/Downloads/ChromeDriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//Espera tudo estar carregado na página para fazer uma ação
		driver.get("http://antoniotrindade.com.br/treinoautomacao");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	//EXERCÍCIO: Navegar no browser, back e forward
	@Test
	public void testNavigationCache() {
		WebElement btnCalculadora = driver.findElement(By.linkText("Calculadora"));
		btnCalculadora.click();//Clica na aba Calculadora
		assertEquals("Desafio Automação Cálculos", driver.getTitle());//Valida se está na aba Calculadora

		WebElement btnTable = driver.findElement(By.linkText("Localizar Table"));
		btnTable.click();//Clica na aba Localizar Table
		assertEquals("Trabalhando com tables", driver.getTitle());//Valida se está na aba Localizar Table

		driver.navigate().back();//voltar
		assertEquals("Desafio Automação Cálculos", driver.getTitle());//Valida se está na aba Calculadora

		driver.navigate().back();//voltar
		assertEquals("Treino Automação de Testes", driver.getTitle());//Valida se está na Home

		driver.navigate().forward();//avançar
		assertEquals("Desafio Automação Cálculos", driver.getTitle());//Valida se está na aba Calculadora

		driver.navigate().forward();//avançar
		assertEquals("Trabalhando com tables", driver.getTitle());//Valida se está na aba Localizar Table


	}

}
