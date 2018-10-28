package top.evolutionary.securitydemo.properties;

import top.evolutionary.securitydemo.common.LoginInType;
import top.evolutionary.securitydemo.common.SecurityConstants;

/**
 * @author richey
 */
public class BrowerProperties {

    /**
     * session管理配置项
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
     */
    private String loginPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;

    /**
     * 社交登录，如果需要用户注册，跳转的页面
     */
    private String signUpUrl = SecurityConstants.DEFAULT_SIGN_UP_URL;

    /**
     * 退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。
     */
    private String signOutUrl = SecurityConstants.DEFAULT_SIGN_OUT_URL;

    private LoginInType loginInType = LoginInType.JSON;

    private int rememberSeconds = 3600;



    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginInType getLoginInType() {
        return loginInType;
    }

    public void setLoginInType(LoginInType loginInType) {
        this.loginInType = loginInType;
    }

    public int getRememberSeconds() {
        return rememberSeconds;
    }

    public void setRememberSeconds(int rememberSeconds) {
        this.rememberSeconds = rememberSeconds;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }
}
