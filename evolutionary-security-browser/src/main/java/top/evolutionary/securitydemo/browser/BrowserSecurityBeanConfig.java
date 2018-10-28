/**
 * 
 */
package top.evolutionary.securitydemo.browser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import top.evolutionary.securitydemo.browser.logout.EvolutionaryLogoutSuccessHandler;
import top.evolutionary.securitydemo.browser.session.EvolutionaryExpiredSessionStrategy;
import top.evolutionary.securitydemo.browser.session.EvolutionaryInvalidSessionStrategy;
import top.evolutionary.securitydemo.properties.SecurityConfigProperties;

/**
 * 浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 * 
 * @author richey
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {

	@Autowired
	private SecurityConfigProperties securityConfigProperties;
	
	/**
	 * session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new EvolutionaryInvalidSessionStrategy(securityConfigProperties);
	}
	
	/**
	 * 并发登录导致前一个session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new EvolutionaryExpiredSessionStrategy(securityConfigProperties);
	}
	
	/**
	 * 退出时的处理策略配置
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new EvolutionaryLogoutSuccessHandler(securityConfigProperties.getBrower().getSignOutUrl());
	}
	
}
