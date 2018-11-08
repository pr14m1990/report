package com.project.daily.report.utils;

import org.cryptacular.bean.EncodingHashBean;
import org.cryptacular.spec.CodecSpec;
import org.cryptacular.spec.DigestSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomPasswordEncoder implements PasswordEncoder {

    @Autowired
    ExpressiveConfig expressiveConfig;
    EncodingHashBean hasher = new EncodingHashBean(
            new CodecSpec("Base64"), new DigestSpec("SHA256"), 1, true);

    public String encode(CharSequence rawPassword) {
        AnnotationConfigApplicationContext acc = new AnnotationConfigApplicationContext(ExpressiveConfig.class);
        ExpressiveConfig ec = acc.getBean(ExpressiveConfig.class);
        return hasher.hash(rawPassword, ec.getProperty("system.enc.hash").getBytes());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return hasher.compare(encodedPassword, rawPassword);
    }

}