package top.evolutionary.securitydemo.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;
import top.evolutionary.securitydemo.properties.QQProperties;
import top.evolutionary.securitydemo.properties.SecurityProperties;
import top.evolutionary.securitydemo.social.EvolutionaryConnectView;
import top.evolutionary.securitydemo.social.qq.connect.QQConnectionFactory;

/**
 * @author richey
 */
@Configuration
@ConditionalOnProperty(prefix = "evolutionary.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }


    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View weixinConnectedView() {
        return new EvolutionaryConnectView();
    }
}
