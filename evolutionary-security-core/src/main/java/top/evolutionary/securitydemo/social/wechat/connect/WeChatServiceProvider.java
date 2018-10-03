package top.evolutionary.securitydemo.social.wechat.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import top.evolutionary.securitydemo.social.wechat.api.WeChatApi;
import top.evolutionary.securitydemo.social.wechat.api.WeChatApiImpl;

/**
 * SpringSocial将与服务商的交互封装进了ServiceProvider，并提供了抽象的实现AbstractOAutrh2ServiceProvider；
 * @author richey
 */
public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChatApi> {


    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * @param appId
     * @param appSecret
     */
    public WeChatServiceProvider(String appId, String appSecret) {
        super(new WeChatOAuth2Template(appId, appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }


    /* (non-Javadoc)
     * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
     */
    @Override
    public WeChatApi getApi(String accessToken) {
        return new WeChatApiImpl(accessToken);
    }


}
