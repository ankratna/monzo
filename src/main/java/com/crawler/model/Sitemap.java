package com.crawler.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Sitemap {

	private List<Page> pages = new ArrayList();

	public void addPage(Page page) {
		this.pages.add(page);
	}

}
