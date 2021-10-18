package com.test;

import static com.core.DriverFactory.getDriver;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.core.BaseTest;
import com.inter.NegativeInterface;
import com.inter.PositiveInterface;

public class CacheTest extends BaseTest{

	@Before
	public void setUp() throws Exception {
		getDriver().get("http://antoniotrindade.com.br/treinoautomacao");
	}

	//EXERCÍCIO: Navegar no browser, back e forward
	@Test
	@Category({PositiveInterface.class, NegativeInterface.class})//classificado como positivo e negativo ao mesmo tempo
	public void testNavigationCache() {
		WebElement btnCalculadora = getDriver().findElement(By.linkText("Calculadora"));
		btnCalculadora.click();//Clica na aba Calculadora
		assertEquals("Desafio Automação Cálculos", getDriver().getTitle());//Valida se está na aba Calculadora

		WebElement btnTable = getDriver().findElement(By.linkText("Localizar Table"));
		btnTable.click();//Clica na aba Localizar Table
		assertEquals("Trabalhando com tables", getDriver().getTitle());//Valida se está na aba Localizar Table

		getDriver().navigate().back();//voltar
		assertEquals("Desafio Automação Cálculos", getDriver().getTitle());//Valida se está na aba Calculadora

		getDriver().navigate().back();//voltar
		assertEquals("Treino Automação de Testes", getDriver().getTitle());//Valida se está na Home

		getDriver().navigate().forward();//avançar
		assertEquals("Desafio Automação Cálculos", getDriver().getTitle());//Valida se está na aba Calculadora

		getDriver().navigate().forward();//avançar
		assertEquals("Trabalhando com tables", getDriver().getTitle());//Valida se está na aba Localizar Table
	}

}
