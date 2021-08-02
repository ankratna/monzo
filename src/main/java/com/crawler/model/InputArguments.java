package com.crawler.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InputArguments {

    String url;
    int numberOfThreads;

}
