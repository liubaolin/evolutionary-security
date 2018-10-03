package top.evolutionary.securitydemo.social.wechat.api;

/**
 * 微信API调用接口
 * @author richey
 */
public interface WeChatApi {


    WeChatUserInfo getUserInfo(String openId);


}
