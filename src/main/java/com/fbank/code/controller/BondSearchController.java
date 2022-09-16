package com.fbank.code.controller;

import com.fbank.code.entity.BondSearchBody;
import com.fbank.code.service.BondSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/BondsSearch/")
public class BondSearchController {

    @Autowired
    private BondSearchService bondSearchService;

    @PostMapping("search")
    public Object search(@RequestBody BondSearchBody bondSearchBody) {

        return bondSearchService.search(bondSearchBody);
    }
}
