package com.wzz.apidemo.utils;

import com.wzz.apidemo.anotation.NotRepeatSubmit;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ApiUtil
 * 参考支付宝加密的算法
 * @author wzz
 * @date 2020/6/21
 **/
public class ApiUtil {

    /**
     * 按参数名升续拼接参数
     * @param request
     * @return
     */
    public static String concatSignString(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<>(16);
        request.getParameterMap().forEach((key, value) -> parameterMap.put(key, value[0]));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = parameterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            // 或略掉的字段
            if ("sign".equals(k)) {
                continue;
            }
            if (parameterMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(parameterMap.get(k).trim()).append("&");
            }
        }

        return sb.toString();
    }

    public static String concatSignString(Map<String, String> map) {
        Map<String, String> parameterMap = new HashMap<>(16);
        map.forEach((key, value) -> parameterMap.put(key, value));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = parameterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (parameterMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(parameterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();
    }

    /**
     * 获取方法上的@NotRepeatSubmit注解
     * @param handler
     * @return
     */
    public static NotRepeatSubmit getNotRepeatSubmit(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            return method.getAnnotation(NotRepeatSubmit.class);
        }
        return null;
    }
}
