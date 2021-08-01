package crawler;

import exception.CrawlerCustomException;
import model.Page;
import model.Sitemap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CrawlerUtils;

import java.util.ArrayList;
import java.util.List;

public class WebCrawlerApp {

	private static final Logger LOG = LoggerFactory.getLogger(WebCrawlerApp.class);

	private static final Sitemap sitemap = new Sitemap();

	public static void main(String[] args1) {

		/*
		 * If all parameters are not passed, return an error
		 */
//https://monzo.com 20 true false
		String args[] = new String[4];
		args[0] = "https://monzo.com";
		args[1] = "20";
		args[2] = "true";
		args[3] = "true";
		if (args.length < 3) {
			LOG.error(
					"Need to pass 4 arguments! {url} {number of threads} {read robots.txt (boolean)} {show info log (boolean)}");
			throw new CrawlerCustomException(
					"Need to pass 4 arguments! {url} {number of threads} {read robots.txt (boolean)} {show info log (boolean)}");
		}

		String url = null;
		int n = 0;
		boolean useRobots = false;
		boolean showLog = false;

		try {
			url = args[0];
			/*
			 * If URL is not valid, show error and stop the program
			 */
			if (!CrawlerUtils.isURLValid(url)) {
				LOG.error("URL is not valid!");
				throw new CrawlerCustomException("URL is not valid!");
			}
			n = Integer.parseInt(args[1]);
			/*
			 * If the number of threads is less than one, show error and stop the program
			 */
			if (n < 1) {
				LOG.error("The number of threads must be bigger than 0!");
				throw new CrawlerCustomException("The number of threads must be bigger than 0!");
			}
			useRobots = Boolean.parseBoolean(args[2]);
			showLog = Boolean.parseBoolean(args[3]);
		} catch (Exception e) {
			LOG.error("At least one of the arguments is wrong!");
			throw new CrawlerCustomException("At least one of the arguments is wrong!");
		}

		/*
		 * Create the list of disallowed URLs if the argument is true
		 */
		List<String> disallowedURLs = new ArrayList<>();
		if (useRobots)
			disallowedURLs = RobotsParser.checkRobotsTxt(url);
		System.out.println("Robots List : "+disallowedURLs);

		/*
		 * Create a new Crawler Manager and call the startCrawling method
		 */
		CrawlerManager crawlerManager = new CrawlerManager(url, n, disallowedURLs, sitemap, showLog);
		Sitemap sitemap = crawlerManager.startCrawling();

		LOG.info("Generating HTML file with the results");


	/*	for(Page page : sitemap.getPages())
		{
			System.out.println("key: "+page.getUrl());
			System.out.println("values: "+page.getLinks().toString());
			System.out.println("-----");
		}*/
	//	HTMLHandler.resultToHTML(sitemap, url);

		LOG.info("Generated results.html with the result!");

	}

}
