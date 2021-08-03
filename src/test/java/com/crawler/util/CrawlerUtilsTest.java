package com.crawler.util;

import com.crawler.exception.CrawlerCustomException;
import com.crawler.model.InputArguments;
import org.junit.Assert;
import org.junit.Test;

public class CrawlerUtilsTest {
    @Test
    public void sameDomain() {
        boolean expected = CrawlerUtils.isSameDomain("https://monzo.com","https://monzo.com/abc/def");
        Assert.assertEquals(expected,true);
    }

    @Test
    public void notSameDomain() {
        boolean expected = CrawlerUtils.isSameDomain("https://monzo.com","https://abc.com/abc/def");
        Assert.assertEquals(expected,false);
    }

    @Test
    public void valid() {
        boolean expected = CrawlerUtils.isURLValid("https://monzo.com");
        Assert.assertEquals(expected,true);
    }

    @Test
    public void inValid() {
        boolean expected = CrawlerUtils.isURLValid("abc.def.ghi");
        Assert.assertEquals(expected,false);
    }

    @Test
    public void sanatizeUrl() {
        String expected = CrawlerUtils.sanatizeUrl("https://abc.com/abc/def%20#%20");
        Assert.assertEquals(expected,"https://abc.com/abc/def");
    }

    @Test(expected = CrawlerCustomException.class)
    public void notEnoughArguments() throws CrawlerCustomException {
        String[] args = { "argument" };
        CrawlerUtils.parseInput(args);
    }

    @Test(expected = CrawlerCustomException.class)
    public void wrongUrl() throws CrawlerCustomException {
        String[] args = { "wrongurl", "5"};
        CrawlerUtils.parseInput(args);
    }

    @Test(expected = CrawlerCustomException.class)
    public void wrongNumberOfThreads() {
        String[] args = { "https://monzo.com", "-5"};
        CrawlerUtils.parseInput(args);
    }

    @Test
    public void parseInput() {
        String[] args = { "https://monzo.com", "5"};
        InputArguments inputArguments = CrawlerUtils.parseInput(args);
        Assert.assertEquals(inputArguments.getUrl(),args[0]);
        Assert.assertEquals(inputArguments.getNumberOfThreads(),5);
    }

}