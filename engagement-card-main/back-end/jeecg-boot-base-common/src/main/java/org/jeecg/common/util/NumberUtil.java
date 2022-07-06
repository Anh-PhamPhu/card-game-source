package org.jeecg.common.util;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author zlx
 */
public class NumberUtil {

    public static List<Integer> iteratorDelete(List<Integer> list, Integer obj) {
        Iterator<Integer> it = list.iterator();

        while (it.hasNext()) {
            Integer item = it.next();

            if (item.equals(obj)) {
                it.remove();//删除当前项
            }
        }
        return list;
    }

}
