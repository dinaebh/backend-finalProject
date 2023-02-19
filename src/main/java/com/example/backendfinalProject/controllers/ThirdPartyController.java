package com.example.backendfinalProject.controllers;

import com.example.backendfinalProject.services.impl.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class ThirdPartyController {

    @Autowired
    ThirdPartyService thirdPartyService;

    @PostMapping("/transfer/{hashedkey}")
    @ResponseStatus(HttpStatus.OK)
    public void sendMoney(@PathVariable String hashedkey, @RequestParam Long accountId, @RequestParam Long secretKey, @RequestParam BigDecimal money) {
        thirdPartyService.sendMoney(hashedkey, accountId, secretKey, money);
    }

    @PostMapping("/transfer-receive/{hashedkey}")
    public void receiveMoney(@PathVariable String hashedkey, @RequestParam Long accountId, @RequestParam Long secretKey, @RequestParam BigDecimal money) {
        thirdPartyService.receiveMoney(hashedkey, accountId, secretKey, money);
    }
}
