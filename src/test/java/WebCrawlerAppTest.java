/*

import com.crawler.WebCrawlerApp;
import com.crawler.exception.CrawlerCustomException;
import org.junit.Test;
import com.crawler.util.CrawlerUtils;

public class WebCrawlerAppTest {

	@Test(expected = CrawlerCustomException.class)
	public void notEnoughArguments() throws CrawlerCustomException {
		String[] args = { "argument" };
		com.crawler.WebCrawlerApp.main(args);
	}

	@Test(expected = CrawlerCustomException.class)
	public void wrongUrl() throws CrawlerCustomException {
		String[] args = { "wrongurl", "5", "true" };
		com.crawler.WebCrawlerApp.main(args);
	}

	@Test(expected = CrawlerCustomException.class)
	public void wrongNumberOfThreads() {
		String[] args = { "https://monzo.com", "-5", "true" };
		com.crawler.WebCrawlerApp.main(args);
	}

	@Test
	public void testUrlTest() {
		String url = "https://adaral.github.io";
		String[] args = { url, "2", "true" };
		com.crawler.WebCrawlerApp.main(args);
	}
	
	@Test
	public void isSameDomainTest() {
		*/
/*String url = "https://monzo.com";
		String linkUrl = "https://monzo.com/blog/";
		CrawlerUtils.isSameDomain(linkUrl, url);

		url = "https://www.monzo.com";
		linkUrl = "https://www.monzo.com/blog/";
		CrawlerUtils.isSameDomain(linkUrl, url);
*//*

		String url = "https://www.monzo.com";
		String linkUrl = "https://en.wikipedia.org/wiki/Robots_exclusion_standard";
		CrawlerUtils.isSameDomain(linkUrl, url);
	}

	@Test
	public void isSameDomainExceptionTest() {
		String url = "http://monzo.com/h?s=^IXIC";
		CrawlerUtils.isSameDomain(url, url);
	}

	@Test
	public void isUrlValid() {
		CrawlerUtils.isURLValid("https://monzo.com");
	}

}
*/
