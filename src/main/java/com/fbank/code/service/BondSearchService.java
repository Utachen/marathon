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
    //private static String path = "src/main/resources/static/bond_info_new.json";
//    private static String path = "src/main/resources/static/bond_info_newnewnew.json";
    private static String path = "C:\\code\\bonds\\Code\\src\\main\\resources\\static\\bond_info_newnewnew.json";

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
                        .filter(x -> StringUtils.isBlank(bondSearchBody.getRegion()) || bondSearchBody.getRegion().equals("重庆") || bondSearchBody.getRegion().equals(x.getRegions()))
                        .filter(x -> bondSearchBody.getFmpriceevaluate() == 0.0d || bondSearchBody.getFmpriceevaluate() <= x.getFmpriceevaluate())
                        .filter(x -> bondSearchBody.getFmpricedeal() == 0.0d || bondSearchBody.getFmpricedeal() <= x.getFmpricedeal())
                        .filter(x -> {
                            if (StringUtils.isBlank(bondSearchBody.getGuarantee())) return true;
                            if (bondSearchBody.getGuarantee().equals("是") && StringUtils.isNotBlank(x.getGuarantee()))
                                return true;
                            if (bondSearchBody.getGuarantee().equals("否") && StringUtils.isBlank(x.getGuarantee()))
                                return true;
                            return false;
                        })
                        .filter(x -> {
                            if (CollectionUtils.isEmpty(bondSearchBody.getBondrate()) || bondSearchBody.getBondrate().contains(x.getBondrate())) {
                                return true;
                            }
                            if (bondSearchBody.getBondrate().contains("AA-以下") && (x.getBondrate().equals("A-1") || StringUtils.isBlank(x.getBondrate()))) {
                                return true;
                            }
                            return false;
                        })
                        .filter(x -> {
                            if (CollectionUtils.isEmpty(bondSearchBody.getIssuerrate()) || bondSearchBody.getIssuerrate().contains(x.getIssuerrate())) {
                                return true;
                            }
                            return false;
                        })
                        .filter(x -> {
                            if (StringUtils.isBlank(bondSearchBody.getDuartion())) return true;
                            switch (bondSearchBody.getDuartion()) {
                                case "3":
                                    if ((x.getDurationend() < 0.25 || x.getDurationyes() < 0.25))
                                        return true;
                                    break;
                                case "6":
                                    if ((0.25 <= x.getDurationend() && x.getDurationend() < 0.5) || (0.25 <= x.getDurationyes() && x.getDurationyes() < 0.5)) {
                                        return true;
                                    }
                                    break;
                                case "9":
                                    if ((0.5 <= x.getDurationyes() && x.getDurationyes() < 0.75) || (0.5 <= x.getDurationend() && x.getDurationend() < 0.75)) {
                                        return true;
                                    }
                                    break;
                                case "12":
                                    if ((0.75 <= x.getDurationyes() && x.getDurationyes() < 1) || (0.75 <= x.getDurationend() && x.getDurationend() < 1)) {
                                        return true;
                                    }
                                case "36":
                                    if ((1 <= x.getDurationyes() && x.getDurationyes() < 3) || (1 <= x.getDurationend() && x.getDurationend() < 3)) {
                                        return true;
                                    }
                                    break;
                                case "60":
                                    if ((3 <= x.getDurationyes() && x.getDurationyes() < 5) || (3 <= x.getDurationend() && x.getDurationend() < 5)) {
                                        return true;
                                    }
                                    break;
                                case "84":
                                    if ((5 <= x.getDurationyes() && x.getDurationyes() < 7) || (5 <= x.getDurationend() && x.getDurationend() < 7)) {
                                        return true;
                                    }
                                    break;
                                case "120":
                                    if ((7 <= x.getDurationyes() && x.getDurationyes() < 10) || (7 <= x.getDurationend() && x.getDurationend() < 10)) {
                                        return true;
                                    }
                                    break;
                                case "120+":
                                    if ((10 <= x.getDurationyes()) || (10 <= x.getDurationend())) {
                                        return true;
                                    }
                                    break;
                                default:
                                    return true;
                            }
                            return false;
                        })
                        .collect(Collectors.toList());

        Collections.shuffle(result);

        return result;
    }





}
