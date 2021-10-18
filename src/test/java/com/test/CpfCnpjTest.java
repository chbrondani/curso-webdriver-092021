package com.test;

import static com.core.DriverFactory.getDriver;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.BaseTest;

public class CpfCnpjTest extends BaseTest {

	/*@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", 
				"/home/voalle/Downloads/ChromeDriver/chromedriver");
		getDriver() = new ChromeDriver();
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//Espera tudo estar carregado na página para fazer uma ação
	}*/

	@Test
	public void testValidateCpfWithMask() {
		getDriver().get("https://www.geradordecpf.org/");

		WebElement checkMask = getDriver().findElement(By.id("cbPontos"));
		checkMask.click();

		WebElement btnGenerate = getDriver().findElement(By.id("btn-gerar-cpf"));
		btnGenerate.click();

		WebElement textFieldCpf = getDriver().findElement(By.id("numero"));
		//textFieldCpf.getAttribute("value");//retorna uma string

		String cpf = textFieldCpf.getAttribute("value");
		System.out.println(cpf);

		assertTrue(cpf.matches("^\\d{3}\\.\\d{3}.\\d{3}-\\d{2}$"));//compara uma string com uma experssão regular
	}
	@Test
	public void testValidateCpfWithoutMask() {
		getDriver().get("https://www.geradordecpf.org/");

		WebElement btnGenerate = getDriver().findElement(By.id("btn-gerar-cpf"));
		btnGenerate.click();

		WebElement textFieldCpf = getDriver().findElement(By.id("numero"));
		//textFieldCpf.getAttribute("value");//retorna uma string

		String cpf = textFieldCpf.getAttribute("value");

		assertTrue(cpf.matches("^\\d{11}$"));//compara uma string com uma expressão regular
		//expressão gerada no site https://tools.lymas.com.br/regexp_br.php
	}

	@Test	
	public void testValidateCnpjWithMask() throws InterruptedException {
		getDriver().get("https://www.4devs.com.br/gerador_de_cnpj");

		WebElement checkYes = getDriver().findElement(By.id("pontuacao_sim"));
		if (checkYes.isSelected()) {
			checkYes.click();
		}

		WebElement btnGenerateCnpj = getDriver().findElement(By.id("bt_gerar_cnpj"));
		btnGenerateCnpj.click();

		WebElement labelCnpj = getDriver().findElement(By.id("texto_cnpj"));

		String cnpj = labelCnpj.getText();
		System.out.println(cnpj);

		assertTrue(cnpj.matches("^\\d{2}\\.\\d{3}.\\d{3}/\\d{4}-\\d{2}$"));
	}

	@Test	
	public void testValidateCnpjWithoutMask() throws InterruptedException {
		getDriver().get("https://www.4devs.com.br/gerador_de_cnpj");

		WebElement checkNo = getDriver().findElement(By.id("pontuacao_nao"));
		if (!checkNo.isSelected()) {
			checkNo.click();
		}

		WebElement btnGenerateCnpj = getDriver().findElement(By.id("bt_gerar_cnpj"));
		btnGenerateCnpj.click();

		//espera explícita
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.id("texto_cnpj"), "Gerando..."));

		WebElement labelCnpj = getDriver().findElement(By.id("texto_cnpj"));
		String cnpj = labelCnpj.getText();
		System.out.println(cnpj);

		assertTrue(cnpj.matches("^\\d{14}$"));
	}
}
