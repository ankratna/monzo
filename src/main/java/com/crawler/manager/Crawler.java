package com.crawler.manager;

import com.crawler.model.Page;
import com.crawler.model.Sitemap;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface Crawler {
    public void crawl(String initialUrl, Page page, Sitemap sitemap, List<String> visited, BlockingQueue<Page> queue);
}
