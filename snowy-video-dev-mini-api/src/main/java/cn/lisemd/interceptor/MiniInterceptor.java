package cn.lisemd.interceptor;

import cn.lisemd.utils.JsonUtils;
import cn.lisemd.utils.RedisOperator;
import cn.lisemd.utils.SnowyJsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class MiniInterceptor implements HandlerInterceptor {
    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    /**
     * 拦截请求，在controller前进行预处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 返回false 请求被拦截，返回
         * 返回true  请求被放行
         */
        String userId = request.getHeader("userId");
        String userToken = request.getHeader("userToken");

        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String uniqueToken = redis.get(USER_REDIS_SESSION + ":" + userId);
            if (StringUtils.isEmpty(userId) && StringUtils.isBlank(userId)) {
                returnErrorResponse(response,new SnowyJsonResult().errorTokenMsg("登录信息过期，请重新登录"));
                return false;
            } else {
                if (!uniqueToken.equals(userToken)) {
                    returnErrorResponse(response,new SnowyJsonResult().errorTokenMsg("监测到多端登录"));
                    return false;
                }
            }
        } else {
            returnErrorResponse(response,new SnowyJsonResult().errorTokenMsg("登录信息过期，请重新登录"));
            return false;
        }
        return true;
    }

    public void returnErrorResponse(HttpServletResponse response, SnowyJsonResult result) throws IOException, UnsupportedEncodingException {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } finally {
            if(out != null) {
                out.close();
            }
        }
    }

    /**
     * 请求controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求controller之后，渲染视图之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
