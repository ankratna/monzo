package com.crawler.util;

import com.crawler.exception.CrawlerCustomException;
import com.crawler.model.InputArguments;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/*
 * Class containing com.crawler.util methods used along the application
 */
public class CrawlerUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CrawlerUtils.class);

	/*parse the given input and the com.crawler.manager object*/

public static InputArguments parseInput(String[] args1){
	String args[] = new String[4];
	args[0] = "https://monzo.com";
	args[1] = "20";

	if (args.length < 2) {
		LOG.error("Need to pass 2 arguments! {url} {number of threads}}");
		throw new CrawlerCustomException("Need to pass 2 arguments! {url} {number of threads} }");
	}

	String url = null;
	int n = 0;

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
	} catch (Exception e) {
		LOG.error("At least one of the arguments is wrong!");
		throw new CrawlerCustomException("At least one of the arguments is wrong!");
	}

	return new InputArguments(url,n);

}


	/*
	 * This method compares the hostname of two URLs. Ignores wether they start with
	 * www or not
	 */
	public static boolean isSameDomain(String linkUrl, String url) {

		try {
			URI linkURI = new URI(linkUrl);
			URI uri = new URI(url);


			 // Delete the trailing www. if exists

			System.out.println("linkurl :"+linkUrl);
			System.out.println("url: "+url);
			String linkUriString = linkURI.getHost().startsWith("www.") ? linkURI.getHost().substring(4)
					: linkURI.getHost();
			String uriString = uri.getHost().startsWith("www.") ? uri.getHost().substring(4) : uri.getHost();


			 // If both hostnames are the same, return true

			boolean result =  linkUriString.equals(uriString);
	//		System.out.println("result: "+result);
			return result;

		} catch (URISyntaxException e) {
			LOG.error("Error parsing URL. Message: {}", e.getMessage());
		}

		return false;

	}

	/*
	 * Validates if the URL is valid with Apache Commons URL Validator
	 */
	public static boolean isURLValid(String url) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(url);
	}



}
