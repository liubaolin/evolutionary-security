package top.evolutionary.securitydemo.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import top.evolutionary.securitydemo.properties.SecurityConfigProperties;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author richey
 */
public class EvolutionaryExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy{


    public EvolutionaryExpiredSessionStrategy(SecurityConfigProperties securityPropertie) {
        super(securityPropertie);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }
}
