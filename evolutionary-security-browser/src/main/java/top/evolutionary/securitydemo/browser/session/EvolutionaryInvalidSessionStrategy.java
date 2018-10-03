package top.evolutionary.securitydemo.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;
import top.evolutionary.securitydemo.properties.SecurityProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author richey
 */
public class EvolutionaryInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {


    public EvolutionaryInvalidSessionStrategy(SecurityProperties securityPropertie) {
        super(securityPropertie);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }
}
