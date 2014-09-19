package com.compare.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuquan
 * Date: 13-11-1
 * Time: 上午11:32
 * To change this template use File | Settings | File Templates.
 */
public class ValueHelper {

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static BigDecimal defaultValue = new BigDecimal(decimalFormat.format(0));
    static {
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    public static String[] str2StrArray(String str){
        String[] rs = null;
        if(StringUtils.isNotBlank(str)){
            if(str.indexOf(",") != -1){
                rs = str.split(",");
            }else{
                String[] tmp = {str};
                rs = tmp;
            }
        }
        return rs;
    }

    public static List<Integer> str2IntList(String str){
        List<Integer> list = null;
        if(StringUtils.isNotBlank(str)){
            list = new ArrayList<Integer>();
            if(str.indexOf(",") != -1){
                String[] tmp = str.split(",");
                for(int i=0; i< tmp.length;i++){
                    list.add(Integer.valueOf(tmp[i]));
                }
            }else{
                list.add(Integer.valueOf(str));
            }
        }
        return list;
    }

    public static List<String> str2StrList(String str){
        List<String> list = null;
        if(StringUtils.isNotBlank(str)){
            list = new ArrayList<String>();
            if(str.indexOf(",") != -1){
                String[] tmp = str.split(",");
                for(int i=0; i< tmp.length;i++){
                    list.add(tmp[i]);
                }
            }else{
                list.add(str);
            }
        }
        return list;
    }

    public static DateTime getDate(String dateTime) {
        if (StringUtils.isNotBlank(dateTime)) {
            return DateUtil.getDateTime(dateTime, "MM/dd/yyyy");
        }
        return null;
    }
    public static DateTime getDateByFormat(String dateTime, String format) {
        if (StringUtils.isNotBlank(dateTime)) {
            return DateUtil.getDateTime(dateTime,format);
        }
        return null;
    }

    public static BigDecimal toBigDecimal(String value, DecimalFormat df){
        if(df == null){
            df = decimalFormat;
        }
         if(StringUtils.isNotBlank(value)){
             return new BigDecimal(df.format(Double.valueOf(value)));
         }
        return defaultValue;
    }

    public static BigDecimal toBigDecimal(String value){
        if(StringUtils.isNotBlank(value)){
            return new BigDecimal(decimalFormat.format(Double.valueOf(value)));
        }
        return defaultValue;
    }

    public static BigDecimal toBigDecimal(Double value){
        if(value != null){
            return new BigDecimal(decimalFormat.format(value));
        }
        return defaultValue;
    }

    public static Integer getMonthInterval(DateTime start, DateTime end){
        Integer rs = 0;
        if(start !=null && end != null){
            int y = end.getYear()-start.getYear();
            int m = end.getMonthOfYear()-start.getMonthOfYear();
            rs = y*12+m;
        }
        return rs;
    }

    public static String[] getArrayByIndexs(String[] values, List<Integer> indexs){
        String[] rs = null;
        if(values !=null && values.length>0 && indexs !=null && !indexs.isEmpty()){
            rs = new String[indexs.size()];
            for(int i=0; i<indexs.size();i++){
                rs[i] = values[indexs.get(i).intValue()];
            }
        }
        return rs;
    }

    public static List<String> getListByIndexs(List<String> values, List<Integer> indexs){
        List<String> rs = null;
        if(values !=null && !values.isEmpty() && indexs !=null && !indexs.isEmpty()){
           rs = new ArrayList<String>();
           for(int i=0; i<indexs.size();i++){
              rs.add(values.get(indexs.get(i).intValue()));
           }
        }
        return rs;
    }

    public static int getYearInterval(DateTime start, DateTime end){
        Interval interval = new Interval(start, end);
        Period p = interval.toPeriod();
        return p.getYears();
    }
}
