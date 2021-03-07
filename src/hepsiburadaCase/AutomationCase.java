// Mehmetcan Uzun - HepsiBurada CaseStudy Test Otomasyonu
// Lokalde çalıştığını gösteren video "Test Otomasyonu.mkv" adıyla paylaşılmıştır.

package hepsiburadaCase;

import static org.junit.Assert.assertEquals;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

public class AutomationCase {

	static WebDriver driver;
	
	// CHROMEDRIVER PATH
	String chromePath = "/Users/mehmetcan/Google Drive/Programming/Selenium/chromedriver";
	
	// GECKODRIVER PATH
	String geckoPath = "/Users/mehmetcan/Google Drive/Programming/Selenium/geckodriver";

	// Browser seçimi, driver initialization
	public void launchBrowser(String url, String browser) {
		if (browser.equalsIgnoreCase("c")) {
			System.setProperty("webdriver.chrome.driver", chromePath);
			driver = new ChromeDriver();
			driver.get(url);
			
		}
		else {
			System.setProperty("webdriver.gecko.driver", geckoPath);
			driver = new FirefoxDriver();
			driver.get(url);
		}		
	}
	
	// Tıklanacak elementler için wait de içeren method
	public void clickOn(String elementSel) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement toBeClicked = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elementSel)));
		toBeClicked.click();
	}
	
	// Text girilecek elementler için wait de içeren method
	public void fillText(String fieldVal, String elementSel) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement textField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elementSel)));
        textField.sendKeys(fieldVal);
	}
	
	// Dropdown seçimi için method
	public void selectDrop(String dropVal, String elementSel) {
		driver.findElement(By.cssSelector(elementSel)).click();
        driver.findElement(By.cssSelector(dropVal)).click();
	}
	
	// Mouse over ile gidilecek elemetler için method
	public void hoverOn(String elementSel) {
		Actions hover = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement toBeHovered = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elementSel)));
		hover.moveToElement(toBeHovered).perform();
	}
	
	// cssSelector ile text'i okunacak elementler için method
	public String textGetByCss(String elementSel) {
		String elementContent = driver.findElement(By.cssSelector(elementSel)).getText();
        return elementContent;
	}
	
	// Xpath ile text'i okunacak elementler için method
	public String textGetByXpath(String elementSel) {
        String elementContent = driver.findElement(By.xpath(elementSel)).getText();
        return elementContent;
	}

	
	public static void main(String[] args) {
		
		String browserChoice = "f";
		try (Scanner scan = new Scanner(System.in)) {
			System.out.print("Browser seçiniz -- Default seçenek Firefox, Chrome için c giriniz: ");  
			browserChoice = scan.nextLine();  
		}
		
		// Kayıt edilecek kullanıcı bilgileri, sitede kayıtlı hesaplarla çakışmaması için mail adresi her run'da o anki tarih saat saniye içerecek şekilde güncelleniyor
		SimpleDateFormat dateSuffix = new SimpleDateFormat("yyyyMMddHHmmss");
        dateSuffix.format(new Date());
		String email = "test"+dateSuffix.format(new Date())+"@testmcu.com";
		String name = "Testan";
		String surname = "Testinovic";
		
		AutomationCase obj = new AutomationCase();
		
		// Ana Sayfa
		obj.launchBrowser("http://automationpractice.com/", browserChoice);	// Sayfayı seçilen broser'da aç
		obj.clickOn(".login");	// "Sign in" linkine tıkla
		
		// Sign In Sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("AUTHENTICATION", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		obj.fillText(email,"#email_create");	// E-mail adresini gir
		obj.clickOn("#SubmitCreate > span:nth-child(1)");	// "Create an account" butonuna tıkla
		
		// Create Account Sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("AUTHENTICATION", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		obj.clickOn("#id_gender1");	// "Mr" radio tıkla
		obj.fillText(name, "#customer_firstname");	// "First Name" alanını doldur
		obj.fillText(surname, "#customer_lastname");	// "Last Name" alanını doldur
		obj.fillText("testpass1234", "#passwd");	// "Password" alanını doldur
		obj.selectDrop("#days > option:nth-child(28)","#days");	// "Date of Birth" gün seç
		obj.selectDrop("#months > option:nth-child(9)", "#months");	// "Date of Birth" ay seç
		obj.selectDrop("#years > option:nth-child(36)", "#years");	// "Date of Birth" yıl seç
		obj.fillText("5th Avenue 25", "#address1");	// "Address" alanını doldur
		obj.fillText("New York", "#city");	// "City" alanını doldur
		obj.selectDrop("#id_state > option:nth-child(34)", "#id_state");	// "State" seç
		obj.fillText("10110", "#postcode");	// "Zip/Postal Code" alanını doldur
		obj.fillText("+1234567890", "#phone_mobile");	// "Mobile phone" alanını doldur
		obj.clickOn("#submitAccount > span:nth-child(1)");	// "Register" butonuna tıkla
		
		// My Account sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("MY ACCOUNT", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		assertEquals(name + " " + surname, obj.textGetByCss(".account > span:nth-child(1)"));	// Kullanıcının oluştuğunun doğrulaması
		obj.hoverOn(".sf-menu > li:nth-child(2) > a:nth-child(1)");	// "Dresses" menüsüne mouse over
		obj.clickOn(".sf-menu > li:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > a:nth-child(1)");	// "Summer Dresses" tıkla
		
		// Summer Dresses kategori sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("SUMMER DRESSES ", obj.textGetByCss(".cat-name"));	// Doğru sayfa kontrolü
		String productName1 = obj.textGetByCss("li.ajax_block_product:nth-child(1) > div:nth-child(1) > div:nth-child(2) > h5:nth-child(1) > a:nth-child(1)"); // İlk ürün adı
		String productPrice1 = obj.textGetByCss("li.ajax_block_product:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > span:nth-child(1)");	// İlk ürün fiyat
		obj.hoverOn("li.ajax_block_product:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1) > img:nth-child(1)");	// Ürünün kutusuna mouse over
		obj.clickOn("li.ajax_block_product:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(4) > a:nth-child(1) > span:nth-child(1)");	// Ürünü "Add to cart"a tıklayarak sepete ekle 
		obj.clickOn(".continue > span:nth-child(1)");	// "Continue shopping" tıkla
		
		// Search
		obj.fillText("Summer", "#search_query_top");	// "Summer" kelimesini arama kutusuna gir
		obj.clickOn("button.btn:nth-child(5)");	// Arama butonuna bas
		
		// Search sonuçları
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("\"SUMMER\"", obj.textGetByCss(".lighter"));	// Doğru sayfa kontrolü
		String productName2 = obj.textGetByCss("li.ajax_block_product:nth-child(3) > div:nth-child(1) > div:nth-child(2) > h5:nth-child(1) > a:nth-child(1)"); // İkinci ürün adı
		String productPrice2 = obj.textGetByCss("li.ajax_block_product:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > span:nth-child(1)");	// İkinci ürün fiyat
		obj.hoverOn("li.ajax_block_product:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1) > img:nth-child(1)");	// Ürünün kutusuna mouse over
		obj.clickOn("li.ajax_block_product:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(4) > a:nth-child(1) > span:nth-child(1)");	// Ürünü "Add to cart"a tıklayarak sepete ekle 
		obj.clickOn("a.button-medium > span:nth-child(1)");	// Proceed to checkout tıkla
		
		// Shopping-cart summary sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("Your shopping cart", obj.textGetByCss(".navigation_page"));	// Doğru sayfa kontrolü
		assertEquals(productName1, obj.textGetByXpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[1]/td[2]/p/a"));	// İlk ürün isim kontrolü
		assertEquals(productPrice1, obj.textGetByXpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[1]/td[4]/span/span[1]"));	// İlk ürün fiyat kontrolü
		assertEquals(productName2, obj.textGetByXpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]/td[2]/p/a"));	// İkinci ürün isim kontrolü
		assertEquals(productPrice2, obj.textGetByXpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]/td[4]/span/span[1]"));	// İkinci ürün fiyat kontrolü
		obj.clickOn(".standard-checkout > span:nth-child(1)");	// Proceed to checkout tıkla
		
		// Addresses sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("ADDRESSES", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		obj.clickOn("button.button:nth-child(4) > span:nth-child(1)");	// Proceed to checkout tıkla
		
		// Shipping sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("SHIPPING", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		driver.findElement(By.cssSelector("#cgv")).click();	// "cgv" kutucuğuna tıkla
		obj.clickOn("button.button:nth-child(4) > span:nth-child(1)");	// Proceed to checkout tıkla
		
		// Payment sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("PLEASE CHOOSE YOUR PAYMENT METHOD", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		obj.clickOn(".bankwire");	// "Bankwire" ödeme seçeneğini seç
		
		// Order summary sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("ORDER SUMMARY", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		obj.clickOn("button.button-medium > span:nth-child(1)");	// I confirm my order tıkla
		
		// Order confirmation sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("ORDER CONFIRMATION", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		String orderTotal = obj.textGetByCss("span.price:nth-child(3) > strong:nth-child(1)"); // Order total değeri al
		obj.clickOn(".account > span:nth-child(1)");	// Kullanıcı menüsüne tıkla
		
		// My Account sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("MY ACCOUNT", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		obj.clickOn("div.col-sm-6:nth-child(1) > ul:nth-child(1) > li:nth-child(1) > a:nth-child(1) > span:nth-child(2)");	// Order history and details tıkla
		
		// Order history sayfası
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("ORDER HISTORY", obj.textGetByCss(".page-heading"));	// Doğru sayfa kontrolü
		assertEquals(orderTotal, obj.textGetByCss(".history_price > span:nth-child(1)"));	// Sipariş kontrolü 

		driver.quit();
	}

}
