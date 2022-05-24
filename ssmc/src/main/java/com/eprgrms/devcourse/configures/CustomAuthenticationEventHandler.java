package com.eprgrms.devcourse.configures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEventHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        log.info("Successful authenticaion result: {}", authentication.getPrincipal());
    }

    @EventListener
    public void handleAuthenticaionFailureEvent(AbstractAuthenticationFailureEvent event) {
        Exception e = event.getException();
        Authentication authentication = event.getAuthentication();
        log.warn("Unsuccessful authentication result: {}", authentication, e);
    }

}
