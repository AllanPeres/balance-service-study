package com.mentorshipwise.balanceservicestudy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    //TODO Create endpoints to create a balance, top-up a balance and spend from the balance
    @GetMapping("/foo/{bla}")
    public String getBalance(@PathVariable String bla) {
        return bla;
    }

}
