package org.ytheohar.bbc.webdriver.cucumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class NewsPage extends LoadableComponent<NewsPage> {

	private final WebDriver driver;
	private final static String baseUrl = "http://www.bbc.co.uk/news";
	private final LoadableComponent<?> parent;

	private static final String UK_LINK_TEXT = "UK";
	private static final String WORLD_LINK_TEXT = "World";
	private static final String BUSINESS_LINK_TEXT = "Business";
	private static final String POLITICS_LINK_TEXT = "Politics";
	private static final String TECH_LINK_TEXT = "Tech";
	private static final String SCIENCE_LINK_TEXT = "Science";
	private static final String HEALTH_LINK_TEXT = "Health";
	private static final String EDUCATION_LINK_TEXT = "Education";
	private static final String ENTERTAINMENT_ARTS_LINK_TEXT = "Entertainment & Arts";

	@FindBy(linkText = UK_LINK_TEXT)
	private static WebElement uk;

	@FindBy(linkText = WORLD_LINK_TEXT)
	private static WebElement world;

	@FindBy(linkText = BUSINESS_LINK_TEXT)
	private static WebElement business;

	@FindBy(linkText = POLITICS_LINK_TEXT)
	private static WebElement politics;

	@FindBy(linkText = TECH_LINK_TEXT)
	private static WebElement tech;

	@FindBy(linkText = SCIENCE_LINK_TEXT)
	private static WebElement science;

	@FindBy(linkText = HEALTH_LINK_TEXT)
	private static WebElement health;

	@FindBy(linkText = EDUCATION_LINK_TEXT)
	private static WebElement education;

	@FindBy(linkText = ENTERTAINMENT_ARTS_LINK_TEXT)
	private static WebElement entertainmentAndArts;

	public NewsPage(WebDriver driver, LoadableComponent<?> parent) {
		this.driver = driver;
		this.parent = parent;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		assertThat("Home page has not been loaded correctly", url, equalTo(baseUrl));
	}

	@Override
	protected void load() {
		parent.get();
		if (parent instanceof HomePage) {
			((HomePage) parent).clickLatestNews();
		} else if (parent instanceof NavigationBarPage) {
			((NavigationBarPage) parent).selectNews();
		}
	}

	/**
	 * Selects a category by clicking on the relevant link on the inner
	 * navigation bar
	 * 
	 * @param cat
	 *            the category to be selected
	 * @return the selected news category page object
	 */
	public NewsCategoryPage selectCategory(Category cat) {
		switch (cat) {
		case UK:
			uk.click();
			break;
		case World:
			world.click();
			break;
		case Business:
			business.click();
			break;
		case Politics:
			politics.click();
			break;
		case Tech:
			tech.click();
			break;
		case Science:
			science.click();
			break;
		case Health:
			health.click();
			break;
		case Education:
			education.click();
			break;
		case EntertainmentAndArts:
			entertainmentAndArts.click();
			break;
		default:
			throw new RuntimeException("Unsopported category: " + cat);
		}

		return new NewsCategoryPage(driver, this, cat);
	}

	/**
	 * Asserts this pages is correctly loaded
	 */
	public void shouldBeLoaded() {
		isLoaded();
	}

	public enum Category {
		UK("UK", "/uk"),
		World(WORLD_LINK_TEXT, "/world"),
		Business(BUSINESS_LINK_TEXT, "/business"),
		Politics("UK Politics", "/politics"),
		Tech("Technology", "/technology"),
		Science("Science & Environment", "/science_and_environment"),
		Health(HEALTH_LINK_TEXT, "/health"),
		Education("Education & Family", "/education"),
		EntertainmentAndArts(ENTERTAINMENT_ARTS_LINK_TEXT, "/entertainment_and_arts");

		private final String title;
		private final String path;

		private Category(String title, String path) {
			this.title = title;
			this.path = path;
		}

		public String getPath() {
			return path;
		}

		public String getTitle() {
			return title;
		}

		/**
		 * Returns the enum value corresponding to the specified category link
		 * title
		 * 
		 * @param categoryLinkTitle
		 *            the name of the category
		 * @return the related Category
		 */
		public static Category of(String categoryLinkTitle) {
			switch (categoryLinkTitle) {
			case UK_LINK_TEXT:
				return UK;
			case BUSINESS_LINK_TEXT:
				return Business;
			case POLITICS_LINK_TEXT:
				return Politics;
			case TECH_LINK_TEXT:
				return Tech;
			case SCIENCE_LINK_TEXT:
				return Science;
			case HEALTH_LINK_TEXT:
				return Health;
			case EDUCATION_LINK_TEXT:
				return Education;
			case ENTERTAINMENT_ARTS_LINK_TEXT:
				return EntertainmentAndArts;
			default:
				throw new RuntimeException("No Such News Category: " + categoryLinkTitle);
			}
		}
	}

}
