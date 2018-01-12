package com.gradle.Servlet;


import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParse {

    public Map parseRequst(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();

        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            map.put(paramName, request.getParameter(paramName));
        }

        return map;
    }
}
