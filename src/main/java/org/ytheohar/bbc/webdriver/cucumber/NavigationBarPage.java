package org.ytheohar.bbc.webdriver.cucumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class NavigationBarPage extends LoadableComponent<NavigationBarPage> {
	private final WebDriver driver;

	private final LoadableComponent<?> parent;

	@FindBy(xpath = "//nav/div/ul/li/a[text()='News']")
	private WebElement news;

	public NavigationBarPage(WebDriver driver, LoadableComponent<?> parent) {
		this.driver = driver;
		this.parent = parent;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		boolean valid = true;
		try {
			news.isDisplayed();
		} catch (Exception e) {
			valid = false;
		}
		assertThat("Navigation bar has not been loaded correctly", valid, is(true));
	}

	@Override
	protected void load() {
		parent.get();
	}

	/**
	 * Clicks on the 'News' link
	 * 
	 * @return the news page object
	 */
	public NewsPage selectNews() {
		news.click();
		return new NewsPage(driver, this);
	}

}
