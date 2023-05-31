package com.fbank.code.entity;


// 从Excel中读取债券详情信息，字段和示例数据如下：
// ID	PUBLISHDATE	SECODE	CBSKCODE	SELFDEFCODE	EXCHANGE	SYMBOL	BONDNAME	BONDSNAME	SPELLSNAME	BONDTYPE1	BONDTYPE2	ISSUECOMPCODE	ISSCOMPTYPE	INITIALCREDITRATE	MATURITYYEAR	MATURITYDAY	BONDTYLE	RAISEMODE	VARIETYTYPE	CUR	PARVALUE	ISSUEPRICE	INITIALISSAMT	COUPONRATE	COMPENSATIONRATE	ISCVT	ISSWAP	ISINTSPLIT	ISREDEMPTION	ISPROC	STARTDATE	ENDDATE	ISSBEGDATE	ISSENDDATE	LISTDATE	MATURITYDATE	PAYMENTDATE	DELISTDATE	PAYMENTNUM	PERPAYDATE	CALCAMODE	PAYMENTMODE	CALCRULES	PROGMODE	PROGRATE	BASERATECODE	FRNSPREAD	ISMINRATE	MINRATE	FRNADJUSTRT	INENHANCEMODE	EXENHANCEMODE	GUARANTOR	GUARTYPE	REGUARANTOR	SPONSOR	LEADUWER	ORIGINATOR	ABSLEVEL	ABSLEVELRATIO	INFOTAXRATE	ISVALID	TMSTAMP	ENTRYDATE	ENTRYTIME	BASICINFOID	TOTALISSUESCALE	BONDYEAR	BONDBATCH	MAINISSUER	REDEEMDATE	REDEEMPRICE	PUTDATE	PUTPRICE	CVTBDEXPIREMEMP	INTERESTRTMEMO	INTPAYMENTMEMO	MEMO	ISSUERNAME	SECURITYID	BASERATESECODE	ESTMATURITYDATE	BCODE	REDEEMPROVISIONS	BASICASSETS	CALWAY	ISFCHOISE	TRADETYPE	SHISSAMT	SZISSAMT	ISCALCA0229	PUBPAYMENTDATE	SHSETTLEWAY	PAYOFFORDER	CLAREMODE
// 776033	20230519	2040873621		N0417833	001006	251143	重庆长寿投资发展集团有限公司2023年面向专业投资者非公开发行短期公司债券(第一期)	23长资D1	CZD	6	621	82668720	99		0		1	2	1	CNY	100	100				0	0	0	0	0	19000101	19000101	19000101	19000101	19000101	19000101	19000101	19000101	0		20	99	2	99		999		0		99	99	0										1	13787451605	2023-05-19 00:00:00	11:34:48	417833		2023	001	99	19000101		19000101						重庆长寿投资发展集团有限公司	1040426785		19000101	23032300001CZD			9	1	0				19000101	1	1	1
// 生成对应的BondInfo对象，将其存入List中

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BondInfo {
    private String id;
    private String publishdate;
    private String secode;
    private String cbskcode;
    private String selfdefcode;
    private String exchange;
    private String symbol;
    private String bondname;
    private String bondsname;
    private String spellsname;
    private String bondtype1;
    private String bondtype2;
    private String issuecompcode;
    private String isscomptype;
    private String initialcreditrate;
    private String maturityyear;
    private String maturityday;
    private String bondtyle;
    private String raisemode;
    private String varietytype;
    private String cur;
    private String parvalue;
    private String issueprice;
    private String initialissamt;
    private String couponrate;
    private String compensationrate;
    private String iscvt;
    private String iswap;
    private String isintsplit;
    private String isredemption;
    private String isproc;
    private String startdate;
    private String enddate;
    private String issbegdate;
    private String issenddate;
    private String listdate;
    private String maturitydate;
    private String paymentdate;
    private String delistdate;
    private String paymentnum;
    private String perpaydate;
    private String calcamode;
    private String paymentmode;
    private String calcrules;
    private String progmode;
    private String prograte;
    private String baseratecode;
    private String frnspread;
    private String isminrate;
    private String minrate;
    private String frnadjustrt;
    private String inenhancemode;
    private String exenhancemode;
    private String guarantor;
    private String guartype;
    private String reguarantor;
    private String sponsor;
    private String leaduwer;
    private String originator;
    private String abslevel;
    private String abslevelratio;
    private String infotaxrate;
    private String isvalid;
    private String tmstamp;
    private String entrydate;
    private String entrytime;
    private String basicinfoid;
    private String totalissuescale;
    private String bondyear;
    private String bondbatch;
    private String mainissuer;
    private String redeemdate;
    private String redeemprice;
    private String putdate;
    private String putprice;
    private String cvtbdexpirememp;
    private String interestrtmemo;
    private String intpaymentmemo;
    private String memo;
    private String issuername;
    private String securityid;
    private String baseratesecode;
    private String estmaturitydate;
    private String bcode;
    private String redeemprovisions;
    private String basicassets;
    private String calway;
    private String isfchoise;
    private String tradetype;
    private String shissamt;
    private String szissamt;
    private String iscalca0229;
    private String pubpaymentdate;
    private String shsettleway;
    private String payofforder;
    private String claremode;


}
