package top.evolutionary.securitydemo.social.wechat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import top.evolutionary.securitydemo.properties.SecurityProperties;
import top.evolutionary.securitydemo.properties.WeChatProperties;
import top.evolutionary.securitydemo.social.wechat.connect.WeChatConnectionFactory;

/**
 * @author richey
 */
@Configuration
@ConditionalOnProperty(prefix = "evolutionary.security.social.wechat", name = "app-id")
public class WeChatAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;


    /**
     *
     * @see
     * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
     * #createConnectionFactory()
     */
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeChatProperties weixinConfig = securityProperties.getSocial().getWechat();
        return new WeChatConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }

    /*@Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new ImoocConnectView();
    }*/
}
