package com.bitc.schedule.controller;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableScheduling
public class ScheduleController {

    @RequestMapping
    public String index() throws Exception {
        return "index";
    }
}
