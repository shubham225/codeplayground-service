package com.shubham.onlinetest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shubham.onlinetest.controller.RestApi.VERSION;

@RestController
@RequestMapping(value = VERSION + "actions")
public class ActionController {
}
