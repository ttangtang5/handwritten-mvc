package com.tang.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @Description
 * @Author RLY
 * @Date 2018/12/4 11:35
 * @Version 1.0
 **/
public class CodeTest {

    private static StringBuffer bufs = new StringBuffer();

    private static final Long COUPON_BASE_NUM = 11000000L;

    private static final Long STUDYCARD_BASE_NUM = 1100000L;

    private static final Long RECHARGECARD_BASE_NUM = 1100000L;

    private static Long temp_num = 0L;

    public static void main(String[] args) {
        List<String> cardsList = getCodes("CODETYPE_STUDYCARD", 1000);
        List<String> cardsList2 = getCodes("CODETYPE_STUDYCARD", 1000);
        cardsList.addAll(cardsList2);
        String pefix = "XM";
        pefix = pefix + "0000";
        Iterator i$ = cardsList.iterator();

        while(i$.hasNext()) {
            String code = (String)i$.next();
            System.out.println(pefix + code);
        }
    }

    public static List<String> getCodes(String codeType, Integer totalNum) {
        List<String> codeslist = new ArrayList();
        Random rd = new Random();
        byte var5 = -1;
        switch(codeType.hashCode()) {
            case -923109169:
                if (codeType.equals("CODETYPE_RECHARGECARD")) {
                    var5 = 2;
                }
                break;
            case -612769602:
                if (codeType.equals("CODETYPE_COUPON")) {
                    var5 = 0;
                }
                break;
            case -473165663:
                if (codeType.equals("CODETYPE_STUDYCARD")) {
                    var5 = 1;
                }
        }

        switch(var5) {
            case 0:
                Long baseNum = COUPON_BASE_NUM.longValue() + 100000 - COUPON_BASE_NUM.longValue();

                for(int i = 0; i < totalNum.intValue(); ++i) {
                    Integer a = rd.nextInt(1000) + 1;
                    codeslist.add(productCode(baseNum.longValue() + (long)a.intValue()));
                    baseNum = baseNum.longValue() + (long)a.intValue();
                }

                return codeslist;
            case 1:
                if(temp_num == 0){
                    temp_num = 3100000L;
                }

                temp_num = temp_num + 100000;

                Long studyCardBaseNum = temp_num - STUDYCARD_BASE_NUM.longValue();

                for(int i = 0; i < totalNum.intValue(); ++i) {
                    Integer a = rd.nextInt(100) + 1;
                    codeslist.add(productCode(studyCardBaseNum.longValue() + (long)a.intValue()));
                    studyCardBaseNum = studyCardBaseNum.longValue() + (long)a.intValue();
                }

                return codeslist;
            case 2:
                Long rechargeCardBaseNum = RECHARGECARD_BASE_NUM.longValue() + 100000 - RECHARGECARD_BASE_NUM.longValue();

                for(int i = 0; i < totalNum.intValue(); ++i) {
                    Integer a = rd.nextInt(100) + 1;
                    codeslist.add(productCode(rechargeCardBaseNum.longValue() + (long)a.intValue()));
                    rechargeCardBaseNum = rechargeCardBaseNum.longValue() + (long)a.intValue();
                }
        }

        return codeslist;
    }

    private static String productCode(Long baseNum) {
        StringBuffer buf = new StringBuffer();
        getYu(baseNum);
        char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String[] str = reverseSort(bufs.toString()).split(",");

        for(int i = 0; i < str.length; ++i) {
            if (!StringUtils.isEmpty(str[i])) {
                buf.append(digits[Integer.parseInt(str[i])]);
            }
        }

        bufs.setLength(0);
        return buf.toString();
    }

    private static Long getYu(Long i) {
        if (i.longValue() > 36L) {
            if (i.longValue() % 36L < 36L) {
                bufs.append(i.longValue() % 36L + ",");
            }

            return getYu(i.longValue() / 36L);
        } else {
            if (i.longValue() < 36L) {
                bufs.append(i + ",");
            }

            return i;
        }
    }

    private static String reverseSort(String str) {
        StringBuffer b = new StringBuffer();
        String[] s = str.split(",");

        for(int i = s.length - 1; i >= 0; --i) {
            if (!StringUtils.isEmpty(s[i])) {
                b.append(s[i] + ",");
            }
        }

        return b.toString();
    }
}
