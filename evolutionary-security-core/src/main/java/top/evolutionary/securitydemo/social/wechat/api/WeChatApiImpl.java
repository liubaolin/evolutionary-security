package top.evolutionary.securitydemo.social.wechat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author richey
 */
public class WeChatApiImpl extends AbstractOAuth2ApiBinding implements WeChatApi {


    /**
     *
     */
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    /**
     * @param accessToken
     */
    public WeChatApiImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        //去掉默认的StringHttpMessageConverter 因为其编码用的是ISO-8859-1
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    /**
     * 获取微信用户信息。
     */
    @Override
    public WeChatUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String response = getRestTemplate().getForObject(url, String.class);
        if(StringUtils.contains(response, "errcode")) {
            return null;
        }
        WeChatUserInfo profile = null;
        try {
            profile = objectMapper.readValue(response, WeChatUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

}
