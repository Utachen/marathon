package com.fbank.code.entity;

import lombok.Data;

import java.util.List;


@Data
public class BondSearchBody {
//    String dealyield;
//    String CDC;
    List<String> bondrate;
    List<String> issuerrate;
 //   String duration;
    List<String> method;
    List<String> place;
    String guarantee;
    String right;
    //String region;
}
