package com.test;

import static org.junit.Assert.*;
import static com.core.DriverFactory.getDriver;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.core.BaseTest;
import com.inter.NegativeInterface;
import com.inter.PositiveInterface;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //roda os testes na ordem alfabética
public class WebElementsTest extends BaseTest {

	@Before
	public void setUp() throws Exception {
		
		getDriver().get("http://antoniotrindade.com.br/treinoautomacao/elementsweb.html");
	}

	@Category(PositiveInterface.class)
	@Test
	public void testValidationName() throws InterruptedException {
		WebElement textFieldBox1 = getDriver().findElement(By.name("txtbox1"));

		textFieldBox1.sendKeys("Antonio");

		//Thread.sleep(5000);

		assertEquals("Antonio", textFieldBox1.getAttribute("value"));
	}

	//Shift + Alt + L = já transforma em WebElement

	@Category(PositiveInterface.class)
	@Test
	public void testValidateTextFieldsDisabled() {
		WebElement textFieldBox1 = getDriver().findElement(By.name("txtbox1"));
		WebElement textFieldBox2 = getDriver().findElement(By.name("txtbox2"));

		assertTrue(textFieldBox1.isEnabled());
		assertFalse(textFieldBox2.isEnabled());
	}

	//EXERCÍCIOS
	//http://antoniotrindade.com.br/treinoautomacao/elementsweb.html
	//RadioButton: Clique na opção 3 e valide que está ok
	@Category(NegativeInterface.class)
	@Test
	public void testValidateRadioButton() throws InterruptedException {
		List<WebElement> radios = getDriver().findElements(By.name("radioGroup1"));

		//clicar no elemento 3 da lista
		//radios.get(2).click(); 

		//validar tamanho da lista
		assertEquals("O tamanho não está de acordo!", 4, radios.size());

		//percorre a lista printando o atributo
		for(WebElement e: radios) {
			if (e.getAttribute("value").equals("Radio Button 3 selecionado")) {
				e.click();
			}
		}
		//Thread.sleep(3000);

		//se a posição 3 está selecionada, passou no teste
		assertTrue("Posição 3 deveria estar selecionada!", radios.get(2).isSelected());
		assertFalse("Posição 4 não deveria estar selecionada!", radios.get(3).isSelected());
		assertFalse("Posição 1 não deveria estar selecionada!", radios.get(0).isSelected());
		assertFalse("Posição 2 não deveria estar selecionada!", radios.get(1).isSelected());
	}

	//CheckBox: Clique na opção 3 e na 4 e valide que está ok
	@Category(NegativeInterface.class)
	@Test
	public void testValidateCheckBox() throws InterruptedException {
		List<WebElement> listChecks = getDriver().findElements(By.name("chkbox"));

		//validar tamanho da lista
		assertEquals("Tamanho deveria ser 4!", 4, listChecks.size());

		for(WebElement el: listChecks) {
			if ((el.getAttribute("value").equals("Check Box 3 selecionado")) || 
					(el.getAttribute("value").equals("Check Box 4 selecionado"))) {
				el.click();
			}
		}

		Thread.sleep(3000);

		assertTrue("Posição 3 deveria estar selecionada!", listChecks.get(2).isSelected());
		assertTrue("Posição 4 deveria estar selecionada!", listChecks.get(3).isSelected());
		assertFalse("Posição 1 não deveria estar selecionada!", listChecks.get(0).isSelected());
		assertFalse("Posição 2 não deveria estar selecionada!", listChecks.get(1).isSelected());
	}

	//DropDown List Single: Selecionar o 7º elemento da lista e valide que está selecionado
	@Category(NegativeInterface.class)
	@Test
	public void testValidateSingleSelect() {
		WebElement dropSingle = getDriver().findElement(By.name("dropdownlist"));
		Select selectSingle = new Select (dropSingle);

		//seleciona o primeiro elemento, depois o sétimo (o primeiro não fica mais selecionado, por isso o teste passou)
		selectSingle.selectByIndex(0);
		assertEquals("Item 1", selectSingle.getFirstSelectedOption().getText());

		selectSingle.selectByVisibleText("Item 7");
		assertEquals("Item 7", selectSingle.getFirstSelectedOption().getText());
	}

	//DropDown List Multi Select: Selecionar o item 5, item 8 e item 9 simultaneamente validando que os 3 valores estão selecionados
	@Category(NegativeInterface.class)
	@Test
	public void testValidateMultiSelect() throws InterruptedException {
		WebElement dropMulti = getDriver().findElement(By.name("multiselectdropdown"));
		Select selectMulti = new Select(dropMulti);

		selectMulti.selectByVisibleText("Item 5");
		selectMulti.selectByVisibleText("Item 8");
		selectMulti.selectByVisibleText("Item 9");

		//valida se a lista tem 3 elementos selecionados
		assertEquals("Deveriam ter 3 elementos!", 3, selectMulti.getAllSelectedOptions().size());

		assertEquals("Item 5", selectMulti.getAllSelectedOptions().get(0).getText()); //pega o texto da posição 0 da lista
		assertEquals("Item 8", selectMulti.getAllSelectedOptions().get(1).getText());
		assertEquals("Item 9", selectMulti.getAllSelectedOptions().get(2).getText());

		Thread.sleep(3000);

		//deseleciona o item 8
		selectMulti.deselectByVisibleText("Item 8");
		assertEquals("Deveriam ter 2 elementos!", 2, selectMulti.getAllSelectedOptions().size());

		//o item 9 era terceiro na lista, depois de deselecionar o item 8, a lista é refeita e assim o item 9 ficou em segundo
		assertEquals("Item 5", selectMulti.getAllSelectedOptions().get(0).getText());
		assertEquals("Item 9", selectMulti.getAllSelectedOptions().get(1).getText());
	}

	//iFrames: escreva seu nome no campo de pesquisa do site da TargetTrust e valide o resultado esperado
	//@Ignore("Desabilitado teste devido ao bug...") serve para ignorar algum teste
	@Category(NegativeInterface.class)
	@Test
	public void testValidateIFrames() throws InterruptedException {
		//Acessar iframe do site da Target
		getDriver().switchTo().frame("iframe_b");

		Thread.sleep(5000);

		//aceitar os cookies
		WebElement btnAllow = getDriver().findElement(By.cssSelector(".cc-color-override-1444386161 > div > a.cc-btn.cc-ALLOW"));
		assertTrue(btnAllow.isDisplayed());
		btnAllow.click();


		getDriver().switchTo().defaultContent();

		//Acessar iframe do site do Selenium
		getDriver().switchTo().frame("iframe_d");

		//clicar no menu
		WebElement btnMenu = getDriver().findElement(By.cssSelector("nav > button"));
		btnMenu.click();

		Thread.sleep(5000);

		//identifica elemento de texto, esreve o nome e valida o resultado
		WebElement tfSelenium = getDriver().findElement(By.cssSelector("#main_navbar > div > span > input"));
		tfSelenium.sendKeys("Antonio");
		assertEquals("Antonio", tfSelenium.getAttribute("value"));
	}

	//Popups: Alerts, Confirm, Prompt. Faça as validações nos 3 botões existentes na pagina de WebElements
	@Test
	public void testValidateAlerts() throws InterruptedException {
		WebElement btnAlert = getDriver().findElement(By.name("alertbtn"));
		btnAlert.click();

		Alert alert = getDriver().switchTo().alert();
		assertEquals("Eu sou um alerta!", alert.getText());

		Thread.sleep(5000);
		alert.accept();

		WebElement btnConfirm = getDriver().findElement(By.name("confirmbtn"));
		btnConfirm.click();

		Alert alert2 = getDriver().switchTo().alert();
		assertEquals("Pressione um botão!", alert2.getText());

		Thread.sleep(5000);
		alert2.dismiss();
	}
}
