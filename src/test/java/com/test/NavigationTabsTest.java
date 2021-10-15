package com.test;

import static com.core.DriverFactory.getDriver;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.core.BaseTest;
import com.core.DriverFactory;
import com.inter.PositiveInterface;

public class NavigationTabsTest extends BaseTest{

	@Before
	public void setUp() throws Exception {
		DriverFactory.getDriver().get("http://antoniotrindade.com.br/treinoautomacao");
	}

	@Category(PositiveInterface.class)
	//Múltiplas janelas: Acessar a home da página de treinos e clique num link 
	@Test
	public void testNavigationTabs() throws InterruptedException {
		assertEquals("Treino Automação de Testes", getDriver().getTitle()); //validou o título

		WebElement linkCPF = getDriver().findElement(By.linkText("Gerador de CPF")); //mapeou o link CPF
		linkCPF.click(); //clicou no link CPF

		//criou um array com as janelas que estão abertas naquele momento
		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
		assertEquals(2, tabs.size()); //validou o tamanho do array, neste caso 2
		//navegar na nova tela aberta
		getDriver().switchTo().window(tabs.get(1)); //foco na segunda janela
		assertEquals("Gerador de CPF", getDriver().getTitle());//validou que o foco está na segunda janela

		getDriver().switchTo().window(tabs.get(0)); //voltar o foco do driver para origem (home)
		assertEquals("Treino Automação de Testes", getDriver().getTitle());//validou que o foco está na origem

		WebElement linkJQuery = getDriver().findElement(By.linkText("Drag and Drop JQuery"));
		linkJQuery.click();//clicou no link Drag and Drop JQuery

		//monta o array de novo, a cada nova janela 
		tabs = new ArrayList<String>(getDriver().getWindowHandles());
		assertEquals(3, tabs.size());//validou o tamanho do array, neste caso 3

		getDriver().switchTo().window(tabs.get(2));//foco na terceira janela
		assertEquals("jQuery UI Droppable - Default functionality", getDriver().getTitle());//validou que o foco está na segunda janela

		getDriver().switchTo().window(tabs.get(1)); //foco na segunda janela
		assertEquals("Gerador de CPF", getDriver().getTitle());//validou que o foco está na segunda janela

		getDriver().switchTo().window(tabs.get(0)); //voltar o foco do driver para origem (home)
		assertEquals("Treino Automação de Testes", getDriver().getTitle());//validou que o foco está na origem
	}
}
