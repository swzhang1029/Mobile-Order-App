package org.orderapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    /**
     * 生成订单编号
     * @return
     */
    public static String genOrderNum() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + System.currentTimeMillis();
    }
}
