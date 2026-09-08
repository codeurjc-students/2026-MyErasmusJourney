package com.myerasmusjourney.backend.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tests")
@Profile("test")
public class TestErrorController {

    @GetMapping("/500")
    public void internalError() {
        throw new RuntimeException("Test internal server error");
    }

}
