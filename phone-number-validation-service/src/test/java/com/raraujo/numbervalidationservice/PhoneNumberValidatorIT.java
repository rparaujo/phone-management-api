package com.raraujo.numbervalidationservice;

import com.raraujo.numbervalidationservice.apnv.APNValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneNumberValidatorIT {
  @BeforeEach
  void sleep() throws InterruptedException {
    // We need to sleep so that we do not exceed external API requests per second
    Thread.sleep( 1000 );
  }

  @Test
  void isPhoneNumberValid() {
    PhoneNumberValidator phoneNumberValidator = new APNValidator();
    assertTrue( phoneNumberValidator.isPhoneNumberValid( "14152007986" ) );
  }

  @Test
  void isPhoneNumberNotValid() {
    PhoneNumberValidator phoneNumberValidator = new APNValidator();
    assertFalse( phoneNumberValidator.isPhoneNumberValid( "1234" ) );
  }
}
