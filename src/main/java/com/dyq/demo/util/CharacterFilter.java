package com.dyq.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dengyouquan
 * @createTime 2019-04-24
 **/
public class CharacterFilter {
    public static String keywordFilter(String keyword) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？『』]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keyword);
        return m.replaceAll("").trim();
    }
}
