package com.crawler.exception;

public class CrawlerCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CrawlerCustomException(String message) {
		super(message);
	}

}
