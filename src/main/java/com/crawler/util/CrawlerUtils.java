package com.crawler.util;

import com.crawler.exception.CrawlerCustomException;
import com.crawler.model.InputArguments;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.net.URISyntaxException;

public class CrawlerUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CrawlerUtils.class);

public static InputArguments parseInput(String[] args){
	/*String args[] = new String[4];
	args[0] = "https://monzo.com";
	args[1] = "20";*/

	if (args.length < 2) {
		LOG.error("Need to pass 2 arguments! {url} {number of threads}}");
		throw new CrawlerCustomException("Need to pass 2 arguments! {url} {number of threads} }");
	}

	String url = null;
	int numberOfThreads = 0;

	try {
		url = args[0];
		if (!CrawlerUtils.isURLValid(url)) {
			LOG.error("URL is not valid!");
			throw new CrawlerCustomException("URL is not valid!");
		}
		numberOfThreads = Integer.parseInt(args[1]);
		if (numberOfThreads < 1) {
			LOG.error("The number of threads must be bigger than 0!");
			throw new CrawlerCustomException("The number of threads must be bigger than 0!");
		}
	} catch (Exception e) {
		LOG.error("At least one of the arguments is wrong!");
		throw new CrawlerCustomException("At least one of the arguments is wrong!");
	}

	return new InputArguments(url,numberOfThreads);

}

	public static boolean isSameDomain(String linkUrl, String url) {
		try {
			URI linkURI = new URI(linkUrl);
			URI uri = new URI(url);

			String linkUriString = linkURI.getHost().startsWith("www.") ? linkURI.getHost().substring(4)
					: linkURI.getHost();
			String uriString = uri.getHost().startsWith("www.") ? uri.getHost().substring(4) : uri.getHost();

			return linkUriString.equals(uriString);
		} catch (URISyntaxException e) {
			LOG.error("Error parsing URL. Message: {}", e.getMessage());
		}
		return false;
	}

	public static boolean isURLValid(String url) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(url);
	}

	public static String sanatizeUrl(String linkURL)
	{
		while (!Character.isLetter(linkURL.charAt(linkURL.length() - 1))) {
			linkURL = linkURL.substring(0, linkURL.length() - 1);
		}
		return linkURL;
	}
}
