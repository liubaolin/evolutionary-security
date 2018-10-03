package top.evolutionary.securitydemo.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;
import top.evolutionary.securitydemo.authentication.AbstractChannelSecurityConfig;
import top.evolutionary.securitydemo.authentication.mobile.SmsCodeAuthenticationSecurityconfig;
import top.evolutionary.securitydemo.browser.session.EvolutionaryExpiredSessionStrategy;
import top.evolutionary.securitydemo.common.SecurityConstants;
import top.evolutionary.securitydemo.properties.SecurityProperties;
import top.evolutionary.securitydemo.validate.code.config.ValidateCodeSecurityConfig;

import javax.sql.DataSource;

/**
 * @author richey
 */

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SmsCodeAuthenticationSecurityconfig smsCodeAuthenticationSecurityconfig;


    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer evolutionarySocialConfigurer;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //第一次启动的时候自动创建表
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                        .and()
                    .apply(smsCodeAuthenticationSecurityconfig)
                        .and()
                    .apply(evolutionarySocialConfigurer)
                        .and()
                    .rememberMe()
                        .tokenRepository(persistentTokenRepository())
                        .tokenValiditySeconds(securityProperties.getBrower().getRememberSeconds())
                        .userDetailsService(userDetailsService)
                        .and()
                    .sessionManagement()
                        .invalidSessionStrategy(invalidSessionStrategy)
//                        .invalidSessionUrl("/session/invalid")
                        .maximumSessions(securityProperties.getBrower().getSession().getMaximumSessions())//最大登录数
                        .maxSessionsPreventsLogin(securityProperties.getBrower().getSession().isMaxSessionsPreventsLogin())//当Session的数量达到最大数量后,阻止后续的登录行为
                        .expiredSessionStrategy(sessionInformationExpiredStrategy)
                        .and()
                        .and()
                    .authorizeRequests()
                    .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                            SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                            SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                            securityProperties.getBrower().getLoginPage(),
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                            securityProperties.getBrower().getSignUpUrl(),
                            securityProperties.getBrower().getSession().getSessionInvalidUrl()+".json",
                            securityProperties.getBrower().getSession().getSessionInvalidUrl()+".html",
                            securityProperties.getBrower().getSession().getSessionInvalidUrl(),
                            "/user/regist")
                        .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .csrf().disable();
    }
}
