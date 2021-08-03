package com.crawler;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebCrawlerAppTest {

    @Test
    public void main() {
            String url = "https://ankratna.github.io";
            String[] args = { url, "2", "true" };
            WebCrawlerApp.main(args);

    }
}