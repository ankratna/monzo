package com.crawler.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Page {

	private String url;

	private Set<String> links;

}
