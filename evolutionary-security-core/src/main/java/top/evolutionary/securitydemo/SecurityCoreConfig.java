package top.evolutionary.securitydemo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.evolutionary.securitydemo.properties.GoogleKapchaConfigProperties;
import top.evolutionary.securitydemo.properties.SecurityConfigProperties;

/**
 * @author richey
 */
@Configuration
@EnableConfigurationProperties(value = {SecurityConfigProperties.class, GoogleKapchaConfigProperties.class})
public class SecurityCoreConfig {


}
