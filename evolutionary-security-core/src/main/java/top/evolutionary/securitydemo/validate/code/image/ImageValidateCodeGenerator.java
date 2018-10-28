package top.evolutionary.securitydemo.validate.code.image;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.web.context.request.ServletWebRequest;
import top.evolutionary.securitydemo.properties.SecurityConfigProperties;
import top.evolutionary.securitydemo.validate.code.ValidateCodeGenerator;

/**
 * @author richey
 */
public class ImageValidateCodeGenerator implements ValidateCodeGenerator {

    private DefaultKaptcha captchaProducer = null;

    private SecurityConfigProperties securityConfigProperties;

    @Override
    public ImageCode generate(ServletWebRequest request) {
        String code = captchaProducer.createText();
        return new ImageCode(captchaProducer.createImage(code), code, securityConfigProperties.getCode().getImage().getExpireIn());
    }

    public DefaultKaptcha getCaptchaProducer() {
        return captchaProducer;
    }

    public void setCaptchaProducer(DefaultKaptcha captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    public SecurityConfigProperties getSecurityConfigProperties() {
        return securityConfigProperties;
    }

    public void setSecurityConfigProperties(SecurityConfigProperties securityConfigProperties) {
        this.securityConfigProperties = securityConfigProperties;
    }
}
