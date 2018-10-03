package top.evolutionary.securitydemo.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author richey
 * SpringSocial提供了/connect接口获取绑定第三方用户的信息,默认返回该视图
 * @see org.springframework.social.connect.web.ConnectController#connectionStatus(NativeWebRequest, Model)
 */
@Component("connect/status")
public class EvolutionaryConnectionStatusView extends AbstractView {


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 渲染视图,这里我们只返回第三方应用及其是否绑定的信息就可以了
     *
     * @param map
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        Map<String, List<Connection<?>>> connectionsMap = (Map<String, List<Connection<?>>>) map.get("connectionMap");

        Map<String, Boolean> result = Maps.newHashMap();
        connectionsMap.forEach((key, connections) ->
                result.put(key, CollectionUtils.isNotEmpty(connections))
        );
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }

}
