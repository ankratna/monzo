package crawler;


import model.Page;
import model.Sitemap;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CrawlerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class Crawler implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);

	private final List<String> visited;

	private final BlockingQueue<Page> queue;

	private final String firstURL;

	private final Sitemap sitemap;

	private final List<String> disallowedURLs;

	private final Boolean showLog;

	/*
	 * Constructor
	 */
	public Crawler(String url, Sitemap s, List<String> visited, BlockingQueue<Page> queue, List<String> disallowedURLs,
			Boolean showLog) {
		this.firstURL = url;
		this.sitemap = s;
		this.queue = queue;
		this.visited = visited;
		this.disallowedURLs = disallowedURLs;
		this.showLog = showLog;
	}

	public void run()  {
		/*
		 * While the queue is not empty, poll the element from the head of the queue,
		 * add it to the visited list, and crawl it to get its links
		 */
		while (!queue.isEmpty()) {
			// System.out.println("Size before take : "+queue.size());
		//	System.out.println("before qs: "+queue.size());
			try {
				Page page = queue.take();
			//	System.out.println("after qs: " + queue.size());
				/*
				 * Check if it's been visited already. If it hasn't, crawl it
				 */
				if (!visited.contains(page.getUrl())
						&& queue.stream().map(Page::getUrl).noneMatch(page.getUrl()::equals)
						) {
					visited.add(page.getUrl());
					crawl(page);
				}
			}
			catch (Exception e){
				System.out.println("Size before exception take : "+queue.size());
				System.out.println("inside exception run : "+e.getMessage());
			}
		//	System.out.println("in progress");
		//	System.out.println("queue size: "+queue.size());
		//	System.out.println("visited size: "+visited.size());
		}
		System.out.println("done");
	}

	private void crawl(Page page) {

		try {
			/*
			 * Get the document and its a href elements with jsoup
			 */
			Document document = Jsoup.connect(page.getUrl()).get();
			Elements linksOnPage = document.select("a[href]");
			Set<String> links = new HashSet<>();
			/*
			 * Iterate over all elements
			 */
			for (Element link : linksOnPage) {
				String linkURL = link.attr("abs:href");
				/*
				 * If the link is empty, ignore it
				 */
				if (StringUtils.isEmpty(linkURL))
					continue;
				/*
				 * Delete all trailing characters that are not letters, such as / and #. This is
				 * done to avoid duplicates, as www.monzo.com and www.monzo.com/ would be trated
				 * as different, but will have the same links
				 */
				while (!Character.isLetter(linkURL.charAt(linkURL.length() - 1))) {
					linkURL = linkURL.substring(0, linkURL.length() - 1);
				}
				/*
				 * Check if the current URL is in the disallowed list, if they belong to the
				 * same domain, if it hasn't been visited and if it's not already in the queue
				 */
				if (disallowedURLs.stream().noneMatch(linkURL::startsWith)) {
					/*
					 * If it's not in the queue, create a new page and add it
					 */
					if (!visited.contains(linkURL)
							&& CrawlerUtils.isSameDomain(linkURL, firstURL)
					&& queue.stream().map(Page::getUrl).noneMatch(linkURL::equals)) {
					//	System.out.println("adding in queue : "+linkURL);
						Page linkedPage = new Page();
						linkedPage.setUrl(linkURL);
						queue.add(linkedPage);
					}

					/*
					 * Add the list of links to the page
					 */
					links.add(linkURL);

			//	System.out.println("linkurl: "+linkURL);

				}
			}
			/*
			 * Add page to sitemap
			 */
			page.setLinks(links);
			if (showLog)
				LOG.info("Crawled {}. Found {} links", page.getUrl(), page.getLinks().size());
			sitemap.addPage(page);

		} catch (IOException e) {
			LOG.error("Error reading {} Message: {}", page.getUrl(), e.getMessage());
		}

	}

}
