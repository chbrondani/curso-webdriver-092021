package com.test;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CpfCnpjTest {
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", 
				"/home/voalle/Downloads/ChromeDriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//Espera tudo estar carregado na página para fazer uma ação
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void testValidateCpfWithMask() {
		driver.get("https://www.geradordecpf.org/");

		WebElement checkMask = driver.findElement(By.id("cbPontos"));
		checkMask.click();

		WebElement btnGenerate = driver.findElement(By.id("btn-gerar-cpf"));
		btnGenerate.click();

		WebElement textFieldCpf = driver.findElement(By.id("numero"));
		//textFieldCpf.getAttribute("value");//retorna uma string

		String cpf = textFieldCpf.getAttribute("value");
		System.out.println(cpf);

		assertTrue(cpf.matches("^\\d{3}\\.\\d{3}.\\d{3}-\\d{2}$"));//compara uma string com uma experssão regular
	}
	@Test
	public void testValidateCpfWithoutMask() {
		driver.get("https://www.geradordecpf.org/");

		WebElement btnGenerate = driver.findElement(By.id("btn-gerar-cpf"));
		btnGenerate.click();

		WebElement textFieldCpf = driver.findElement(By.id("numero"));
		//textFieldCpf.getAttribute("value");//retorna uma string

		String cpf = textFieldCpf.getAttribute("value");

		assertTrue(cpf.matches("^\\d{11}$"));//compara uma string com uma expressão regular
		//expressão gerada no site https://tools.lymas.com.br/regexp_br.php
	}

	@Test	
	public void testValidateCnpjWithMask() throws InterruptedException {
		driver.get("https://www.4devs.com.br/gerador_de_cnpj");

		WebElement checkYes = driver.findElement(By.id("pontuacao_sim"));
		if (checkYes.isSelected()) {
			checkYes.click();
		}

		WebElement btnGenerateCnpj = driver.findElement(By.id("bt_gerar_cnpj"));
		btnGenerateCnpj.click();

		WebElement labelCnpj = driver.findElement(By.id("texto_cnpj"));

		String cnpj = labelCnpj.getText();
		System.out.println(cnpj);

		assertTrue(cnpj.matches("^\\d{2}\\.\\d{3}.\\d{3}/\\d{4}-\\d{2}$"));
	}

	@Test	
	public void testValidateCnpjWithoutMask() throws InterruptedException {
		driver.get("https://www.4devs.com.br/gerador_de_cnpj");

		WebElement checkNo = driver.findElement(By.id("pontuacao_nao"));
		if (!checkNo.isSelected()) {
			checkNo.click();
		}

		WebElement btnGenerateCnpj = driver.findElement(By.id("bt_gerar_cnpj"));
		btnGenerateCnpj.click();

		//espera explícita
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.id("texto_cnpj"), "Gerando..."));

		WebElement labelCnpj = driver.findElement(By.id("texto_cnpj"));
		String cnpj = labelCnpj.getText();
		System.out.println(cnpj);

		assertTrue(cnpj.matches("^\\d{14}$"));
	}
}
