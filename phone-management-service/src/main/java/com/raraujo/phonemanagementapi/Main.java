package com.raraujo.phonemanagementapi;

import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    @Autowired
    PhoneRecordController phoneRecordController;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}