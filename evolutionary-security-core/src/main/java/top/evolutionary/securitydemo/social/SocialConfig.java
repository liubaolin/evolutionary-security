package top.evolutionary.securitydemo.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import top.evolutionary.securitydemo.properties.SecurityConfigProperties;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        repository.setTablePrefix("evolutionary_");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }

        return repository;
    }

    @Bean
    public SpringSocialConfigurer evolutionarySocialSecurityConfig() {
        String filterProcessorUrl = securityConfigProperties.getSocial().getFilterProcessorUrl();
        EvolutionarySpringSocialConfigurer evolutionarySpringSocialConfigurer = new EvolutionarySpringSocialConfigurer(filterProcessorUrl);
        evolutionarySpringSocialConfigurer.signupUrl(securityConfigProperties.getBrower().getSignUpUrl());
        return evolutionarySpringSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }


}
