package org.ytheohar.bbc.webdriver.cucumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VideoPage extends LoadableComponent<VideoPage> {

	private final WebDriver driver;
	private final String baseUrl;

	@FindBy(className = "player-wrapper")
	private WebElement player;

	@FindBy(className = "share__tool--facebook")
	private WebElement fbIcon;

	@FindBy(xpath = "//button[@title='Share this page']")
	private WebElement shareIcon;

	public VideoPage(WebDriver driver, String baseUrl) {
		this.driver = driver;
		this.baseUrl = baseUrl;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		String message = "News content page has not been loaded correctly";
		assertThat(message, player, notNullValue());
	}

	@Override
	protected void load() {
		driver.get(baseUrl);
	}

	/**
	 * Shares this page on facebook
	 * 
	 * @return the facebook page object
	 */
	public FBPage shareOnFacebook() {
		try {
			// some video pages have a facebook icon, like
			// http://www.bbc.co.uk/news/technology-34643629
			return shareViaFBIcon();
		} catch (NoSuchElementException e) {
			// while some others have a share icon, with many social network
			// options, like http://www.bbc.co.uk/programmes/p037vggh
			return shareViaShareIcon();
		}
	}

	private FBPage shareViaFBIcon() {
		fbIcon.click();
		return new FBPage(driver, baseUrl, false);
	}

	private FBPage shareViaShareIcon() {
		shareIcon.click();
		By fbOptionLocator = By.xpath("//li/a[@title='Share this with Facebook']");
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// wait until the social networks menu pops up
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//div[@id='sb-1-panel' and contains(@class,'s-b-p-open-true')]")));

		// wait until the link is clickable
		WebElement fbOption = wait.until(ExpectedConditions.elementToBeClickable(fbOptionLocator));

		new Actions(driver).moveToElement(fbOption).perform();
		fbOption.click();

		// wait until the fb window pops up. If it does not, click again
		try {
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		} catch (TimeoutException e) {
			fbOption.click();
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		}

		return new FBPage(driver, baseUrl, true);
	}

	/**
	 * Asserts this page is correctly loaded
	 */
	public void shouldBeLoaded() {
		isLoaded();
	}
}
