package top.evolutionary.securitydemo.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import top.evolutionary.securitydemo.social.wechat.api.WeChatApi;

/**
 * @author richey
 */
public class WeChatConnectionFactory extends OAuth2ConnectionFactory<WeChatApi> {


    /**
     * @param appId
     * @param appSecret
     */
    public WeChatConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeChatServiceProvider(appId, appSecret), new WeChatAdapter());

    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WeChatAccessGrant) {
            return ((WeChatAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }

    /**
     * 最后一个参数,默认实现全局用一个ApiAdapter,但是微信不同,微信ApiAdapter有一个openId,决定了它不可以是单实例的
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.oauth2.AccessGrant)
     */
    @Override
    public Connection<WeChatApi> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WeChatApi>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /* (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
     */
    @Override
    public Connection<WeChatApi> createConnection(ConnectionData data) {
        return new OAuth2Connection<WeChatApi>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WeChatApi> getApiAdapter(String providerUserId) {
        return new WeChatAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WeChatApi> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeChatApi>) getServiceProvider();
    }

}
