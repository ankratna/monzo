package com.crawler.manager;

import com.crawler.model.Page;
import com.crawler.model.Sitemap;
import com.crawler.util.CrawlerUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class BfsCrawler implements Crawler {
    private static final Logger LOG = LoggerFactory.getLogger(BfsCrawler.class);

    public void crawl(String initialUrl, Page page, Sitemap sitemap, List<String> visited, BlockingQueue<Page> queue) {

        try {
            Document document = Jsoup.connect(page.getUrl()).get();
            Elements linksOnPage = document.select("a[href]");
            Set<String> links = new HashSet<>();

            for (Element link : linksOnPage) {
                String linkURL = link.attr("abs:href");

                if (StringUtils.isEmpty(linkURL))
                    continue;

                linkURL = CrawlerUtils.sanatizeUrl(linkURL);

                if (!visited.contains(linkURL)
                        && CrawlerUtils.isSameDomain(linkURL, initialUrl)
                        && queue.stream().map(Page::getUrl).noneMatch(linkURL::equals)) {
                    Page linkedPage = new Page();
                    linkedPage.setUrl(linkURL);
                    queue.add(linkedPage);
                }

                links.add(linkURL);
            }
            page.setLinks(links);
            LOG.info("Crawled {}. Found {} links", page.getUrl(), page.getLinks().size());
            sitemap.addPage(page);
        } catch (IOException e) {
            LOG.error("Error reading {} Message: {}", page.getUrl(), e.getMessage());
        }

    }
}
