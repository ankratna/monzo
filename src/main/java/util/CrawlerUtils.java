package util;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/*
 * Class containing util methods used along the application
 */
public class CrawlerUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CrawlerUtils.class);

	/*
	 * This method compares the hostname of two URLs. Ignores wether they start with
	 * www or not
	 */
	public static boolean isSameDomain(String linkUrl, String url) {

		try {
			URI linkURI = new URI(linkUrl);
			URI uri = new URI(url);


			 // Delete the trailing www. if exists

		//	System.out.println("linkurl :"+linkUrl);
	//		System.out.println("url: "+url);
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
