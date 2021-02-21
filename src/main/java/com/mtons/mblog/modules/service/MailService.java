package com.mtons.mblog.modules.service;

import java.util.Map;

/**
 * @author : yangquan
 */
public interface MailService {
    void config();
    void sendTemplateEmail(String to, String title, String template, Map<String, Object> content);
}
