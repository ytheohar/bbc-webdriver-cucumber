package org.ytheohar.bbc.webdriver.cucumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends LoadableComponent<HomePage> {

	private final WebDriver driver;
	private final static String baseUrl = "http://www.bbc.co.uk/";

	@FindBy(linkText = "News headlines")
	private WebElement latestNews;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		assertThat("Home page has not been loaded correctly", url, equalTo(baseUrl));
	}

	@Override
	protected void load() {
		driver.get(baseUrl);
	}

	/**
	 * Clicks on the 'Latest news'
	 * 
	 * @return the news page object
	 */
	public NewsPage clickLatestNews() {
		latestNews.click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(NewsPage.BASE_URL));
		return new NewsPage(driver, this);
	}
}
