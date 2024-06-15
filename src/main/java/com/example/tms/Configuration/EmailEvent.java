package com.example.tms.Configuration;

import org.springframework.context.ApplicationEvent;

public class EmailEvent extends ApplicationEvent {
    private String email;
    private String otp;
    private String subject;
    private String username;
    private String password;
    private EmailType emailType;

    public enum EmailType {
        RESET_PASSWORD,
        CREATE_ACCOUNT
    }

    public EmailEvent(Object source, String email, String otp, String subject, String username, String password, EmailType emailType) {
        super(source);
        this.email = email;
        this.otp = otp;
        this.subject = subject;
        this.username = username;
        this.password = password;
        this.emailType = emailType;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getOtp() {
        return otp;
    }

    public String getSubject() {
        return subject;
    }

    public String getPassword() {
        return password;
    }

    public EmailType getEmailType() {
        return emailType;
    }
}
