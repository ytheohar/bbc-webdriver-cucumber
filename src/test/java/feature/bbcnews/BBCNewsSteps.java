package feature.bbcnews;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.ytheohar.bbc.webdriver.cucumber.FBPage;
import org.ytheohar.bbc.webdriver.cucumber.HomePage;
import org.ytheohar.bbc.webdriver.cucumber.NavigationBarPage;
import org.ytheohar.bbc.webdriver.cucumber.NewsCategoryPage;
import org.ytheohar.bbc.webdriver.cucumber.NewsPage;
import org.ytheohar.bbc.webdriver.cucumber.NewsPage.Category;
import org.ytheohar.bbc.webdriver.cucumber.VideoPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;

public class BBCNewsSteps implements En {

	private WebDriver driver;
	private HomePage homePage;
	private NewsPage newsPage;
	private NewsCategoryPage newsCategoryPage;
	private VideoPage videoPage;
	private FBPage fbPage;

	@Before
	public void setup() {
		System.setProperty("webdriver.gecko.driver","C:/Selenium/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	public BBCNewsSteps() {
		Given("^the user is landed at the home page$", () -> {
			homePage = new HomePage(driver);
			homePage.get();
		});

		When("^the user clicks 'News' on the navigation bar$", () -> {
			NavigationBarPage navbar = new NavigationBarPage(driver, homePage);
			newsPage = new NewsPage(driver, navbar);
			newsPage.get();
		});

		When("^the user clicks '(.*)' on the inner news navigation bar$",
				(String categoryLinkTitle) -> {
					Category category = Category.of(categoryLinkTitle);
					newsCategoryPage = new NewsCategoryPage(driver, newsPage,
							category);
					newsCategoryPage.get();
				});

		When("^the user clicks on the first video on the right hand 'Watch/Listen' section$",
				() -> {
					videoPage = newsCategoryPage.clickFirstVideo();
				});

		When("^the user clicks on the facebook share icon$", () -> {
			fbPage = videoPage.shareOnFacebook();
		});

		Then("^the facebook page loads having a url that includes the video page url$",
				() -> {
					fbPage.shouldBeLoaded();
				});

		When("^the user clicks on 'News headlines'$", () -> {
			newsPage = new NewsPage(driver, homePage);
			newsPage.get();
		});

		Then("^the 'News' page loads$", () -> {
			newsPage.shouldBeLoaded();
		});

	}

}
