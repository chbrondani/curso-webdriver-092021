package com.test;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.inter.PositiveInterface;

public class NavigationTabsTest {
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

	@Category(PositiveInterface.class)
	//Múltiplas janelas: Acessar a home da página de treinos e clique num link 
	@Test
	public void testNavigationTabs() throws InterruptedException {
		assertEquals("Treino Automação de Testes", driver.getTitle()); //validou o título

		WebElement linkCPF = driver.findElement(By.linkText("Gerador de CPF")); //mapeou o link CPF
		linkCPF.click(); //clicou no link CPF

		//criou um array com as janelas que estão abertas naquele momento
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		assertEquals(2, tabs.size()); //validou o tamanho do array, neste caso 2
		//navegar na nova tela aberta
		driver.switchTo().window(tabs.get(1)); //foco na segunda janela
		assertEquals("Gerador de CPF", driver.getTitle());//validou que o foco está na segunda janela

		driver.switchTo().window(tabs.get(0)); //voltar o foco do driver para origem (home)
		assertEquals("Treino Automação de Testes", driver.getTitle());//validou que o foco está na origem

		WebElement linkJQuery = driver.findElement(By.linkText("Drag and Drop JQuery"));
		linkJQuery.click();//clicou no link Drag and Drop JQuery

		//monta o array de novo, a cada nova janela 
		tabs = new ArrayList<String>(driver.getWindowHandles());
		assertEquals(3, tabs.size());//validou o tamanho do array, neste caso 3

		driver.switchTo().window(tabs.get(2));//foco na terceira janela
		assertEquals("jQuery UI Droppable - Default functionality", driver.getTitle());//validou que o foco está na segunda janela

		driver.switchTo().window(tabs.get(1)); //foco na segunda janela
		assertEquals("Gerador de CPF", driver.getTitle());//validou que o foco está na segunda janela

		driver.switchTo().window(tabs.get(0)); //voltar o foco do driver para origem (home)
		assertEquals("Treino Automação de Testes", driver.getTitle());//validou que o foco está na origem
	}

}
