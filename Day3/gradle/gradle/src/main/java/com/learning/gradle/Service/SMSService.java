package com.learning.gradle.Service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class SMSService implements IMessageService{
    @Override
    public void sendMessage(String to, String message) {
        System.out.println("Sending SMS to : "+ to +"  With message : "+ message);
    }
}
