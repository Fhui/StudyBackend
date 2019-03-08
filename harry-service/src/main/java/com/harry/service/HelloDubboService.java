package com.harry.service;


import org.springframework.stereotype.Component;

/**
 * @author harry
 * @create 2019-03-06 11:50
 **/
@Component
public class HelloDubboService {

    public String sayWhat(String name) {
        return "dubbo is connection ---> " + name;
    }

}
