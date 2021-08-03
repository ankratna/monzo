package com.crawler.manager;


import com.crawler.model.Page;
import com.crawler.model.Sitemap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CrawlerTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(CrawlerTask.class);

	private final List<String> visited;

	private final BlockingQueue<Page> queue;

	private final String firstURL;

	private final Sitemap sitemap;

	private final Crawler crawler;

	public CrawlerTask(String url, Sitemap s, List<String> visited, BlockingQueue<Page> queue, Crawler crawler) {
		this.firstURL = url;
		this.sitemap = s;
		this.queue = queue;
		this.visited = visited;
		this.crawler = crawler;
	}

	public void run()  {
		/*
		 * While the queue is not empty, poll the element from the head of the queue,
		 * add it to the visited list, and crawl it to get its links
		 */
		while (!queue.isEmpty()) {
			try {
				Page page = queue.poll();
				if (!visited.contains(page.getUrl())
						&& queue.stream().map(Page::getUrl).noneMatch(page.getUrl()::equals)
						) {
					visited.add(page.getUrl());
					crawler.crawl(firstURL,page,sitemap,visited,queue);
				}
			}
			catch (Exception e){
				// LOG.error(e.getMessage());
			}
		}
	}

}
