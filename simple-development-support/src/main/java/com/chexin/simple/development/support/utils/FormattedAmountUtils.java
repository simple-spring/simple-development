package com.chexin.simple.development.support.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * desc:    金额相关处理工具类
 */
public class FormattedAmountUtils {

    /**
     * 不考虑分隔符的正确性
     */
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
    private static final char[] RMB_NUMS = "零壹贰叁肆伍陆柒捌玖".toCharArray();
    private static final String[] UNITS = {"元", "角", "分", "整"};
    private static final String[] U1 = {"", "拾", "佰", "仟"};
    private static final String[] U2 = {"", "万", "亿"};

    /**
     * 将金额（整数部分等于或少于12位，小数部分2位）转换为中文大写形式.
     * @param amount 金额数字
     * @return       中文大写
     * @throws IllegalArgumentException
     */
    public static String convert(String amount) throws IllegalArgumentException {
        // 去掉分隔符
        amount = amount.replace(",", "");

        // 验证金额正确性
        if (amount.equals("0.00")) {
            throw new IllegalArgumentException("金额不能为零.");
        }
        Matcher matcher = AMOUNT_PATTERN.matcher(amount);
        if (! matcher.find()) {
            throw new IllegalArgumentException("输入金额有误.");
        }

        // 整数部分
        String integer  = matcher.group(1);
        // 小数部分
        String fraction = matcher.group(2);

        String result = "";
        if (! integer.equals("0")) {
            // 整数部分
            result += integer2rmb(integer) + UNITS[0];
        }
        if (fraction.equals("00")) {
            // 添加[整]
            result += UNITS[3];
        } else if (fraction.startsWith("0") && integer.equals("0")) {
            // 去掉分前面的[零]
            result += fraction2rmb(fraction).substring(1);
        } else {
            // 小数部分
            result += fraction2rmb(fraction);
        }

        return result;
    }

    /**
     * 将金额小数部分转换为中文大写
     * @param fraction
     * @return
     */
    private static String fraction2rmb(String fraction) {
        // 角
        char jiao = fraction.charAt(0);
        // 分
        char fen  = fraction.charAt(1);
        return (RMB_NUMS[jiao - '0'] + (jiao > '0' ? UNITS[1] : ""))
                + (fen > '0' ? RMB_NUMS[fen - '0'] + UNITS[2] : "");
    }

    /**
     * 将金额整数部分转换为中文大写
     * @param integer
     * @return
     */
    private static String integer2rmb(String integer) {
        StringBuilder buffer = new StringBuilder();
        // 从个位数开始转换
        int i, j;
        for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
            char n = integer.charAt(i);
            if (n == '0') {
                // 当n是0且n的右边一位不是0时，插入[零]
                if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
                    buffer.append(RMB_NUMS[0]);
                }
                // 插入[万]或者[亿]
                if (j % 4 == 0) {
                    if (i > 0 && integer.charAt(i - 1) != '0'
                            || i > 1 && integer.charAt(i - 2) != '0'
                            || i > 2 && integer.charAt(i - 3) != '0') {
                        buffer.append(U2[j / 4]);
                    }
                }
            } else {
                if (j % 4 == 0) {
                    // 插入[万]或者[亿]
                    buffer.append(U2[j / 4]);
                }
                // 插入[拾]、[佰]或[仟]
                buffer.append(U1[j % 4]);
                // 插入数字
                buffer.append(RMB_NUMS[n - '0']);
            }
        }
        return buffer.reverse().toString();
    }
    /**
     * 对金额的格式调整到分  //23->23.00
     * @param money
     * @return
     */
    public static String moneyFormat(String money){
        StringBuffer sb=new StringBuffer();
        if(money==null){
            return "0.00";
        }
        int index=money.indexOf(".");
        if(index==-1){
            return money+".00";
        }else{
            //整数部分
            String s0=money.substring(0,index);
            //小数部分
            String s1=money.substring(index+1);
            //小数点后一位
            if(s1.length()==1){
                s1=s1+"0";
                //如果超过3位小数，截取2位就可以了
            }else if(s1.length()>2){
                s1=s1.substring(0,2);
            }
            sb.append(s0);
            sb.append(".");
            sb.append(s1);
        }
        return sb.toString();
    }

    /**
     * 格式化金额,保留两位小数(四舍五入)
     * @param db
     * @return
     */
    public static String formattedAmount(double db) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(db);
    }

    /**
     * 四舍五入并保留两位小数
     * @param db
     * @return
     */
    public static double RoundHalfUp(double db){
        BigDecimal b = new BigDecimal(db);
        BigDecimal bigDecimal = b.setScale(2,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * 查看精度
     * @param db
     * @return
     */
    public static String precision(double db){
        BigDecimal b = new BigDecimal(db);
        return b.toString();
    }

    /** fmtMicrometer
     * @Description: 格式化数字为千分位
     * @param text
     * @return    设定文件
     * @return String    返回类型
     */
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.00");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.00");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0.00");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

    /**@Title: delFmtMicrometer
     * @Description: 去除千分位格式化
     * @param text
     * @return    设定文件
     * @return String    返回类型
     */
    public static String delFmtMicrometer(String text) {
        double db = 0;
        try {
            db = new DecimalFormat().parse(text).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(db);
    }

}
