package com.dylan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class TestController {
    @RequestMapping(value="/test", method= RequestMethod.GET)
    public String showTestForm() {
        return "testForm";
    }
}
