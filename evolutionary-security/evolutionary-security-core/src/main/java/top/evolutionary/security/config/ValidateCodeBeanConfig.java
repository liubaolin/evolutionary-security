package top.evolutionary.security.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.evolutionary.security.properties.SecurityProperties;
import top.evolutionary.security.validate.code.sms.DefaultSmsCodeSender;
import top.evolutionary.security.validate.code.image.ImageCodeGenerator;
import top.evolutionary.security.validate.code.sms.SmsCodeSender;
import top.evolutionary.security.validate.code.ValidateCodeGenerator;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private DefaultKaptcha captchaProducer = null;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 在初始化该bean之前看是否已经有名为imageCodeGenerator的bean了，有就不初始化了
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setCaptchaProducer(captchaProducer);
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }


    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
