package org.ytheohar.bbc.webdriver.cucumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.ytheohar.bbc.webdriver.cucumber.NewsPage.Category;

public class NewsCategoryPage extends LoadableComponent<NewsCategoryPage> {

	private final WebDriver driver;
	private final LoadableComponent<NewsPage> parent;
	private final String baseUrl;
	private final Category cat;

	@FindBy(id = "comp-index-title")
	private WebElement titleElem;

	@FindBy(xpath = "//div[contains(@class,'column--secondary')]//div[@class='condor-item faux-block-link'][1]//a[contains(@class,'title-link')]")
	private WebElement video;

	public NewsCategoryPage(WebDriver driver, LoadableComponent<NewsPage> parent, Category cat) {
		this.driver = driver;
		this.parent = parent;
		this.cat = cat;
		baseUrl = "http://www.bbc.co.uk/news" + cat.getPath();
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		String message = "News content page has not been loaded correctly";
		assertThat(message, url, equalTo(baseUrl));

		String actualTitle = titleElem.getText();
		assertThat(message, actualTitle, equalTo(cat.getTitle()));
	}

	@Override
	protected void load() {
		parent.get().selectCategory(cat);
	}

	/**
	 * Clicks on the first video page link on the right hand 'Watch/Listen'
	 * section
	 * 
	 * @return the video page object
	 */
	public VideoPage clickFirstVideo() {
		String path = video.getAttribute("href");
		video.click();
		return new VideoPage(driver, path);
	}

	/**
	 * Asserts this page is correctly loaded
	 */
	public void shouldBeLoaded() {
		isLoaded();
	}
}
