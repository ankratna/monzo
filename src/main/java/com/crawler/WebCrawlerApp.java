package com.crawler;

import com.crawler.manager.CrawlerManager;
import com.crawler.model.InputArguments;
import com.crawler.model.Sitemap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.crawler.util.CrawlerUtils;
import com.crawler.util.HTMLHandler;

public class WebCrawlerApp {

    private static final Logger LOG = LoggerFactory.getLogger(WebCrawlerApp.class);

    private static final Sitemap sitemap = new Sitemap();

    public static void main(String[] args) {

        InputArguments inputArguments = CrawlerUtils.parseInput(args);
        CrawlerManager crawlerManager = new CrawlerManager(inputArguments.getUrl(), inputArguments.getNumberOfThreads(), sitemap);
        Sitemap sitemap = crawlerManager.startCrawling();

        LOG.info("Generating HTML file with the results");

        HTMLHandler.resultToHTML(sitemap, inputArguments.getUrl());

        LOG.info("Generated crawl-results.html with the result!");

    }

}
