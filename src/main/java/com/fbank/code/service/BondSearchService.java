package com.fbank.code.service;

import com.alibaba.fastjson.JSONObject;
import com.fbank.code.entity.BondSearchBody;
import com.fbank.code.entity.BondsInfo;
import com.fbank.code.util.ReadJsonFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BondSearchService {
    //private final static String path = "D:\\code\\bonds\\Code\\src\\main\\resources\\static\\bond_info_new.json";
    private static String path = "src/main/resources/static/bond_info_new.json";
    public List<BondsInfo> bondsInfos = new ArrayList<>();

    @PostConstruct
    public void init(){
        String s = ReadJsonFile.getStringByPath(path);
        bondsInfos = JSONObject.parseArray(s, BondsInfo.class);
    }

    public List<BondsInfo> search(BondSearchBody bondSearchBody) {
        if (CollectionUtils.isEmpty(bondsInfos)) {
            return null;
        }


        List<BondsInfo> result =
                bondsInfos.stream()
                        .filter(x -> CollectionUtils.isEmpty(bondSearchBody.getPlace()) || bondSearchBody.getPlace().contains(x.getPlace()))
                        .filter(x -> CollectionUtils.isEmpty(bondSearchBody.getMethod()) || bondSearchBody.getMethod().contains(x.getMethod()))
                        .filter(x -> StringUtils.isBlank(bondSearchBody.getRight()) || bondSearchBody.getRight().equals(x.getRight()))
                        .filter(x -> {
                            if(StringUtils.isBlank(bondSearchBody.getGuarantee())) return true;
                            if( bondSearchBody.getGuarantee().equals("是") && StringUtils.isNotBlank(x.getGuarantee())) return true;
                            if( bondSearchBody.getGuarantee().equals("否") && StringUtils.isBlank(x.getGuarantee())) return true;
                            return false;
                        })
                        .filter(x -> { if (CollectionUtils.isEmpty(bondSearchBody.getBondrate()) || bondSearchBody.getBondrate().contains(x.getBondrate()) ){
                            return true;
                        }
                            if (bondSearchBody.getBondrate().contains("AA-以下") && (x.getBondrate().equals("A-1") || StringUtils.isBlank(x.getBondrate()))) {
                                return true;
                            }
                            return false;
                        })
                        .filter(x->{
                            if (CollectionUtils.isEmpty(bondSearchBody.getIssuerrate()) || bondSearchBody.getIssuerrate().contains(x.getIssuerrate()) ){
                                return true;
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
        Collections.shuffle(result);

        return result;
    }





}
