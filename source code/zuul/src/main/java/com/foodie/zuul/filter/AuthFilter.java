package com.foodie.zuul.filter;
import com.foodie.zuul.feignapi.AuthService;
import com.foodie.zuul.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限验证 Filter
 * 注册和登录接口不过滤
 * <p>
 * 验证权限需要前端在 Cookie 或 Header 中（二选一即可）设置用户的 userId 和 token
 * 因为 token 是存在 Redis 中的，Redis 的键由 userType+userId 构成，值是 token
 * 在两个地方都没有找打 userId 或 token其中之一，就会返回首页
 */
@Slf4j
@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    private AuthService authenservice;
    private RequestContext requestContext = null;
    private HttpServletRequest request = null;
    private String requestURI;
    private String headerToken;
    private String key;
    private CookieUtils cookieUtils = new CookieUtils();
    private Cookie tokenCookie = null;

    //无权限时的提示语
    private static final String INVALID_TOKEN = "invalid token";


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        requestContext = RequestContext.getCurrentContext();
        request = requestContext.getRequest();
        headerToken = request.getHeader("token");
        requestURI = request.getRequestURI();
        if (requestURI.contains("userclient")) {
            return false;
        }else if (requestURI.contains("authenservice")){
            return false;
        }
        return true;
    }


    @Override
    public Object run() throws ZuulException {
        //check token, key, 有一个为空就403无权限，拦截
        try {
            checkTokenAndKey();
        } catch (Exception e) {
            return null;
        }
        //校验token的有效性
        try {
            verifyToken(requestContext, request);
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private void checkTokenAndKey() {
        Cookie keycookie = cookieUtils.getCookieByName(request, "key");
        if (null==keycookie){
            setUnauthorizedResponse(requestContext);
            return;
        }

        key=keycookie.getValue();
        if (StringUtils.isEmpty(this.key)) {
            this.key = request.getHeader("key");
            if (StringUtils.isEmpty(this.key)) {
                setUnauthorizedResponse(requestContext);
                return;
            }
        }else if (requestURI.contains("student")&&key.charAt(0)!='1') {
            setUnauthorizedResponse(requestContext);
            return;
        }
        else if (requestURI.contains("teacher")&&key.charAt(0)!='2') {
            setUnauthorizedResponse(requestContext);
            return;
        }
        tokenCookie = cookieUtils.getCookieByName(request, "token");
        if (tokenCookie == null || StringUtils.isEmpty(tokenCookie.getValue())) {
            headerToken = request.getHeader("token");
            if (headerToken == null) {
                setUnauthorizedResponse(requestContext);
                return;
            }
        }else {
            headerToken=tokenCookie.getValue();
        }
    }


    /**
     * 调用authenservice检查，key，headertoken 一定是有的
     */
    private void verifyToken(RequestContext requestContext, HttpServletRequest request) {
        Boolean checktokenbykey = authenservice.checktokenbykey(key, headerToken);
        if (checktokenbykey == false)
            setUnauthorizedResponse(requestContext);
        else
            authenservice.refreshtoken(key);
    }

    /**
     * 验证token不成功，或者返回403无权限
     * 跳转至首页
     *
     * @param requestContext
     */
    private void setUnauthorizedResponse(RequestContext requestContext) {
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        try {
            requestContext.getResponse().sendRedirect("http://localhost/userclient/unauthorized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
