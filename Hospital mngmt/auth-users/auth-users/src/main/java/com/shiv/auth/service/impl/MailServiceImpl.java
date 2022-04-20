package com.shiv.auth.service.impl;

import com.shiv.auth.dto.SimpleEmailDTO;
import com.shiv.auth.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(SimpleEmailDTO simpleEmailDTO) {
        try{
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setTo(simpleEmailDTO.getEmail());
            simpleMailMessage.setSubject(simpleEmailDTO.getSubject());
            simpleMailMessage.setText(simpleEmailDTO.getMessage());
            javaMailSender.send(simpleMailMessage);
            log.info("Mail sent to "+simpleEmailDTO.getEmail());
        }catch (Exception e){
            log.error(e.toString());
        }
    }
}
