package com.application.learnenglish.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageManager {
    private final MessageSource messageSource;

    public String getMessage(String messageKey, Object[] messageArgs) {
        return this.messageSource.getMessage(messageKey, messageArgs, Locale.getDefault());
    }

    public String buildErrorMessage(String messageKey, Object... messageArgs) {
        return getMessage(messageKey, messageArgs);
    }

    public String buildErrorMessage(String messageKey) {
        return getMessage(messageKey, null);
    }

    public MessageManager(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
