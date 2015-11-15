package org.ytheohar.bbc.webdriver.cucumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class FBPage extends LoadableComponent<FBPage> {

	private final WebDriver driver;
	private final String baseUrl;
	private final String encodedLink;
	private final boolean popsUpInNewWindow;

	public FBPage(WebDriver driver, String link, boolean popsUpInNewWindow) {
		this.driver = driver;
		this.popsUpInNewWindow = popsUpInNewWindow;
		baseUrl = "https://www.facebook.com";
		PageFactory.initElements(driver, this);
		String urlEncodedLink = null;
		try {
			urlEncodedLink = URLEncoder.encode(link, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		if (popsUpInNewWindow) {
			encodedLink = urlEncodedLink.replaceAll("%", "%25");
		} else {
			encodedLink = urlEncodedLink;
		}
	}

	@Override
	protected void isLoaded() throws Error {
		String message = "FB share page has not been loaded correctly";
		boolean isValid = false;
		if (popsUpInNewWindow) {
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
				String url = driver.getCurrentUrl();
				if (url.contains(encodedLink)) {
					isValid = true;
				}
			}
			assertThat(message, isValid, is(true));
		} else {
			assertThat(message, driver.getCurrentUrl(), containsString(encodedLink));
		}
	}

	@Override
	protected void load() {
		driver.get(baseUrl);
	}

	/**
	 * Asserts this page is correctly loaded
	 */
	public void shouldBeLoaded() {
		isLoaded();
	}

}
