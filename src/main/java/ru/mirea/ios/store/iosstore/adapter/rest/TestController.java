package ru.mirea.ios.store.iosstore.adapter.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("hello-world")
    public String sayHello() {
        return "Hello world";
    }
}
