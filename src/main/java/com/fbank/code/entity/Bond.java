package com.fbank.code.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;
import java.text.*;

@Data
@AllArgsConstructor
public class Bond {

    private String code;
    private String name;
    private String issuer;
    private String variety;
    private double couponRate;
    private double ytm;
    private double terms;
    private LocalDate maturityDate;
    private String bondRating;
    private String issuerRating;
    private String guarantorRating;
    private String issuerRegion;

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double MIN_COUPON_RATE = 0.01;
    private static final double MAX_COUPON_RATE = 0.1;
    private static final double MIN_TERMS = 0.1;
    private static final double MAX_TERMS = 10.0;
    private static final double MIN_YTM = 0.01;
    private static final double MAX_YTM = 0.1;
    private static final String[] RATINGS = {"AAA", "AA+", "AA", "AA-", "A+", "A", "A-", "BBB+", "BBB", "BBB-",
            "BB+", "BB", "B+", "B", "B-", "CCC+", "CCC", "CCC-", "CC", "C", "D"};
    private static final String[] ISSUERS = {"中国银行", "中国建设银行", "中国农业银行", "中国工商银行", "交通银行", "中信银行", "招商银行", "民生银行", "兴业银行"};
    private static final String[] VARIETIES = {"MTN", "企业债", "可转债"};

    // 生成随机债券数据
    public static Bond generateRandomBond() {
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("#.##");
        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
        String code = String.join("", Collections.nCopies(6, "" + LETTERS.charAt(random.nextInt(LETTERS.length()))));
        String name = year + String.join("", Collections.nCopies(4, "" + LETTERS.charAt(random.nextInt(LETTERS.length())))) + VARIETIES[random.nextInt(VARIETIES.length)] + String.format("%03d", random.nextInt(1000));
        String issuer = ISSUERS[random.nextInt(ISSUERS.length)];
        String variety = VARIETIES[random.nextInt(VARIETIES.length)];
        double couponRate = Double.parseDouble(df.format(random.nextDouble() * (MAX_COUPON_RATE - MIN_COUPON_RATE) + MIN_COUPON_RATE));
        double ytm = Double.parseDouble(df.format(random.nextDouble() * (MAX_YTM - MIN_YTM) + MIN_YTM));
        double terms = Double.parseDouble(df.format(random.nextDouble() * (MAX_TERMS - MIN_TERMS) + MIN_TERMS));
        LocalDate maturityDate = LocalDate.now().plusYears((int) terms);
        String bondRating = RATINGS[random.nextInt(RATINGS.length)];
        String issuerRating = RATINGS[random.nextInt(RATINGS.length)];
        String guarantorRating = RATINGS[random.nextInt(RATINGS.length)];
        String issuerRegion = "中国大陆";
        return new Bond(code, name, issuer, variety, couponRate, ytm, terms, maturityDate, bondRating, issuerRating, guarantorRating, issuerRegion);
    }

    public static List<Bond> generateRandomBonds(int num) {
        List<Bond> bonds = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            bonds.add(generateRandomBond());
        }
        return bonds;
    }

    public static void main(String[] args) {
        List<Bond> bonds = generateRandomBonds(10);
        System.out.println(JSONObject.toJSONString(bonds));
    }
}