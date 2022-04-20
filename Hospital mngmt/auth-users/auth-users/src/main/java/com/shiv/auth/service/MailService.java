package com.shiv.auth.service;

import com.shiv.auth.dto.SimpleEmailDTO;

public interface MailService {
    public void sendMail(SimpleEmailDTO simpleEmailDTO);
}
