package top.evolutionary.securitydemo.social.wechat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;
import top.evolutionary.securitydemo.properties.SecurityProperties;
import top.evolutionary.securitydemo.properties.WeChatProperties;
import top.evolutionary.securitydemo.social.EvolutionaryConnectView;
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

    @Bean({"connect/wechatConnect", "connect/wechatConnected"})
    @ConditionalOnMissingBean(name = "wechatConnectedView")
    public View weixinConnectedView() {
        return new EvolutionaryConnectView();
    }
}
