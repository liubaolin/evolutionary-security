package top.evolutionary.securitydemo.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author richey
 */

public class WeChatProperties extends SocialProperties {


    /**
     * 第三方id,用来决定第三方发起登录的url,默认是wechat
     */
    private String providerId = "wechat";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
