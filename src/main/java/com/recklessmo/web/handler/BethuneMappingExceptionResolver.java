package com.recklessmo.web.handler;

import com.alibaba.fastjson.JSON;
import com.recklessmo.response.JsonResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class BethuneMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Log logger = LogFactory.getLog(BethuneMappingExceptionResolver.class);


    /**
     * 覆盖doResolveException方法，记录错误日志
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler,
                                              Exception ex) {
        try {
            //json请求就返回异常
            String url = request.getRequestURI();
            if(url.startsWith("/v1") || url.startsWith("/common") || url.startsWith("/public")) {
                logger.error("用户访问" + request.getRequestURI() + "失败," + ex.getMessage(), ex);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain");
                JsonResponse jsonResponse = new JsonResponse();
                jsonResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.setData("服务异常，我们正在抓紧修改，请耐心等候");
                response.getWriter().write(JSON.toJSONString(jsonResponse));
            }else{
                //页面请求不返回内容
                return null;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return new ModelAndView();
    }

}
