package com.bizwink.util;

import java.util.List;

/**
 * Created by petersong on 17-3-12.
 */
public class DeReplication {
    private List messages;
    private List errors;

    public List getMessages() {
        return messages;
    }

    public void setMessages(List messages) {
        this.messages = messages;
    }

    public List getErrors() {
        return errors;
    }

    public void setErrors(List errors) {
        this.errors = errors;
    }
}
