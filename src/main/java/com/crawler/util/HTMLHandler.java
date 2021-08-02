package com.crawler.util;


import com.crawler.WebCrawlerApp;
import com.crawler.exception.CrawlerCustomException;
import com.crawler.model.Sitemap;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class HTMLHandler {

	private static final Logger LOG = LoggerFactory.getLogger(HTMLHandler.class);

	public static void resultToHTML(Sitemap sitemap, String url) {

		InputStream inputStream = null;

		String htmlString = null;

		try {
			System.out.println("ip1");
			inputStream = WebCrawlerApp.class.getResourceAsStream("/template.html");
			System.out.println("ip2");
			System.out.println(Objects.nonNull(inputStream));
			htmlString = IOUtils.toString(inputStream, Charset.defaultCharset());
			System.out.println("ip3");
		} catch (IOException e) {
			LOG.error("Could not find HTML template!");
		}

		if (htmlString == null) {
			LOG.error("Could not find HTML template!");
			throw new CrawlerCustomException("Could not create HTML with the result");
		}
		/*
		 * Create HTML table
		 */
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<table class=\"table\"><thead><tr>");
		stringBuilder.append("<th scope=\"col\">URL</th>\r\n" + "<th scope=\"col\">Links</th>");
		stringBuilder.append("</tr></thead>");
		stringBuilder.append("<tbody>");
		sitemap.getPages().stream().forEach(page -> {
			stringBuilder.append("<tr>");
			stringBuilder.append("<th scope=\"row\">" + page.getUrl() + "</th>");
			if (page.getLinks() != null) {
				stringBuilder.append("<td><ul>");
				page.getLinks().stream().forEach(link -> {
					stringBuilder.append("<li>");
					stringBuilder.append(link);
					stringBuilder.append("</li>");
				});
				stringBuilder.append("</td>");
				stringBuilder.append("</tr>");
			}
		});
		stringBuilder.append("</tbody></table>");
		htmlString = htmlString.replace("$url", url);
		htmlString = htmlString.replace("$body", stringBuilder.toString());
		try {
			Files.write(Paths.get("result.html"), htmlString.getBytes());
		} catch (IOException e) {
			LOG.error("Could not crete HTML result page!");
		}

	}

}