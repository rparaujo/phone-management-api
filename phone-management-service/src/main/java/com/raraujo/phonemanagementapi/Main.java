package com.raraujo.phonemanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @SpringBootApplication is a convenience annotation that is equivalent to declaring @SpringBootConfiguration,
 * ,@EnableAutoConfiguration and @ComponentScan
 * @SpringBootConfiguration: Indicates that a class provides Spring Boot application @Configuration. Can be used as an alternative to the Spring's
 * standard @Configuration annotation so that configuration can be found automatically (for example in tests).
 * Application should only ever include one @SpringBootConfiguration and most idiomatic Spring Boot applications will
 * inherit it from @SpringBootApplication.
 * @EnableAutoConfiguration Enable auto-configuration of the Spring Application Context, attempting to guess and configure beans that you are
 * likely to need. Auto-configuration classes are usually applied based on your classpath and what beans you have defined.
 */
@SpringBootApplication

/**
 * @ComponentScan Configures component scanning directives for use with @Configuration classes
 * for example processing @Autowired annotations.
 * In this case we are extending component scanning to "com.raraujo.phonemanagementapi" and
 * "com.raraujo.numbervalidationservice" packages.
 */
@ComponentScan( basePackages = { "com.raraujo.phonemanagementapi", "com.raraujo.numbervalidationservice" } )
public class Main {
  public static void main( String[] args ) {
    /**
     * Class that can be used to bootstrap and launch a Spring application from a Java main method.
     * By default class will Create an appropriate ApplicationContext instance (depending on your classpath)
     */
    SpringApplication.run( Main.class, args );
  }
}