package top.evolutionary.securitydemo.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import top.evolutionary.securitydemo.social.wechat.api.WeChatApi;
import top.evolutionary.securitydemo.social.wechat.api.WeChatUserInfo;

/**
 * 微信Api适配器,将微信api的数据模型转为Spring Social的标准模型
 * @author richey
 */
public class WeChatAdapter implements ApiAdapter<WeChatApi> {
    private String openId;

    public WeChatAdapter() {
    }

    public WeChatAdapter(String openId) {
        this.openId = openId;
    }

    /**
     * @param api
     * @return
     */
    @Override
    public boolean test(WeChatApi api) {
        return true;
    }

    /**
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(WeChatApi api, ConnectionValues values) {
        WeChatUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(WeChatApi api) {
        return null;
    }

    /**
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(WeChatApi api, String message) {
        //do nothing
    }
}
