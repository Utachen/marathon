package com.fbank.code;

import com.alibaba.fastjson.JSONObject;
import com.fbank.code.entity.BondSearchBody;
import com.fbank.code.entity.BondsInfo;
import com.fbank.code.service.BondSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class bondsTest {
    @Autowired
    BondSearchService service;
    @Test
    public void bonds() {
        for (BondsInfo bondsInfo : service.bondsInfos) {
            System.out.println(JSONObject.toJSON(bondsInfo));
        }

    }

    @Test
    public void bondsTest(){
        BondSearchBody bondSearchBody = new BondSearchBody();
        List<String> place = new ArrayList<>();
        place.add("银行间");
        List<String> rate = new ArrayList<>();
        rate.add("AAA");
        rate.add("AA");
        List<String> bondrate = new ArrayList<>();
        bondrate.add("AA-以下");
        bondSearchBody.setBondrate(bondrate);

        bondSearchBody.setIssuerrate(rate);
        bondSearchBody.setPlace(place);

        List<BondsInfo> res = service.search(bondSearchBody);
        System.out.println(res);
        System.out.println(res.size());
    }
}
