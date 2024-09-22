package com.raraujo.phonemanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.raraujo.phonemanagementapi", "com.raraujo.numbervalidationservice" } )
public class Main {
  public static void main( String[] args ) {
    SpringApplication.run( Main.class, args );
  }
}