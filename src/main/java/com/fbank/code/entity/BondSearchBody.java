package com.fbank.code.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class BondSearchBody implements Serializable {
    private static final long serialVersionUID = -2827799211635132486L;
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
