package com.crawler.manager;

import com.crawler.model.Page;
import com.crawler.model.Sitemap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

public class CrawlerManager {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerManager.class);

    private Sitemap sitemap;

    private final ExecutorService executor;

    private final List<String> visited;

    private final BlockingQueue<Page> queue;

    private String url;

    private int numberOfThreads;

    public CrawlerManager(String url, int numberOfThreads, Sitemap sitemap) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.queue = new LinkedBlockingQueue<>();
        this.visited = Collections.synchronizedList(new ArrayList<>());
        this.url = url;
        this.numberOfThreads = numberOfThreads;
        this.sitemap = sitemap;
    }

    public Sitemap startCrawling() {
        LOG.info("Starting to crawl {} with {} threads", url, numberOfThreads);
        Page page = new Page();
        page.setUrl(url);
        visited.add(url);

        /*Step1 : Get All the source link from given url and start Breadth First Search with Threads and Blocking queue*/
        new BasicCrawler().crawl(url, page, sitemap, visited, queue);

        /*Step2: Create runnable tasks , so that links can be parsed con currently*/
        List<Runnable> runnables = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            runnables.add(new CrawlerTask(url, sitemap, visited, queue, new BasicCrawler()));
        }

        /*Step3: Run all the tasks Asynchronous and concurrently  and wait for all the tasks to be finished successfully*/
        CompletableFuture<?>[] futures = runnables
                .stream()
                .map(r -> CompletableFuture.runAsync(r, executor))
                .toArray(CompletableFuture[]::new);

        try {
            CompletableFuture.allOf(futures).join();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        /*Step4: shut down the executor service and return the sitemap*/
        executor.shutdown();
        return sitemap;
    }


}
