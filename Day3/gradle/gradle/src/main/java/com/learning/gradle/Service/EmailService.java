package com.learning.gradle.Service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class EmailService implements IMessageService{
    @Override
    public void sendMessage(String to, String message) {
        System.out.println("Sending email to " + to + " message is : "+message);
    }
}
