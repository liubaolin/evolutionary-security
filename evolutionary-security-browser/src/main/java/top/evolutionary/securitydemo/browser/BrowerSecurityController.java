package top.evolutionary.securitydemo.browser;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import top.evolutionary.securitydemo.browser.support.SimpleResult;
import top.evolutionary.securitydemo.browser.support.SocialUserInfo;
import top.evolutionary.securitydemo.common.SecurityConstants;
import top.evolutionary.securitydemo.properties.SecurityConfigProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author richey
 */
@RestController
public class BrowerSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResult requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是：" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityConfigProperties.getBrower().getLoginPage());
            }
        }
        return new SimpleResult("访问的服务需要登录页面，请引导用户到登录页");
    }


    /**
     * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
     *
     * @param request
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        Connection connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        socialUserInfo.setHeadimg(connection.getImageUrl());
        socialUserInfo.setNickname(connection.getDisplayName());
        socialUserInfo.setProviderId(connection.getKey().getProviderId());
        socialUserInfo.setProviderUserId(connection.getKey().getProviderUserId());
        return socialUserInfo;
    }

    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResult sessionInvalid() {
        String message = "session失效";
        return new SimpleResult(message);
    }
}
