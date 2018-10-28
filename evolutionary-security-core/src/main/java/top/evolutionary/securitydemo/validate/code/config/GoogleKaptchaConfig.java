package top.evolutionary.securitydemo.validate.code.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.evolutionary.securitydemo.properties.GoogleKapchaConfigProperties;

import java.util.Properties;

@Configuration
public class GoogleKaptchaConfig {

    @Autowired
    private GoogleKapchaConfigProperties googleKapchaConfigProperties;

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", googleKapchaConfigProperties.getBorder());
        properties.setProperty("kaptcha.border.color", googleKapchaConfigProperties.getBorderColor());
        properties.setProperty("kaptcha.textproducer.font.color", googleKapchaConfigProperties.getTextproducerFontColor());
        properties.setProperty("kaptcha.image.width", googleKapchaConfigProperties.getImageWidth());
        properties.setProperty("kaptcha.image.height", googleKapchaConfigProperties.getImageHeight());
        properties.setProperty("kaptcha.textproducer.font.size", googleKapchaConfigProperties.getTextproducerFontSize());
        properties.setProperty("kaptcha.session.key", googleKapchaConfigProperties.getSessionKey());
        properties.setProperty("kaptcha.textproducer.char.length", googleKapchaConfigProperties.getTextproducerCharLength());
        properties.setProperty("kaptcha.textproducer.font.names", googleKapchaConfigProperties.getTextproducerFontName());
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
