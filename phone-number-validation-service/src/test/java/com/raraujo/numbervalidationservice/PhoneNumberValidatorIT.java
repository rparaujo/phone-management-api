package com.raraujo.numbervalidationservice;

import com.raraujo.numbervalidationservice.apnv.APNValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest( classes = { APNValidator.class } )
@AutoConfigureMockMvc
@TestPropertySource( properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration" )
@ExtendWith( MockitoExtension.class )
public class PhoneNumberValidatorIT {

  @MockBean
  private HttpClient httpClient;

  @Autowired
  @InjectMocks
  private APNValidator phoneNumberValidator;

  @Test
  void isPhoneNumberValid() throws IOException, InterruptedException {
    HttpResponse<String> mockResponse = mock( HttpResponse.class );
    when( mockResponse.body() ).thenReturn(
      "{\n" +
        "  \"phone\": \"14152007986\",\n" +
        "  \"valid\": true,\n" +
        "  \"format\": {\n" +
        "    \"international\": \"+14152007986\",\n" +
        "    \"local\": \"(415) 200-7986\"\n" +
        "  },\n" +
        "  \"country\": {\n" +
        "    \"code\": \"US\",\n" +
        "    \"name\": \"United States\",\n" +
        "    \"prefix\": \"+1\"\n" +
        "  },\n" +
        "  \"location\": \"California\",\n" +
        "  \"type\": \"mobile\",\n" +
        "  \"carrier\": \"T-Mobile USA, Inc.\"\n" +
        "}"
    );
    when( httpClient.send( any( HttpRequest.class ), any( BodyHandler.class ) ) )
      .thenReturn( mockResponse );
    assertTrue( phoneNumberValidator.isPhoneNumberValid( "14152007986" ) );
  }

  @Test
  void isPhoneNumberNotValid() throws IOException, InterruptedException {
    HttpResponse<String> mockResponse = mock( HttpResponse.class );
    when( mockResponse.body() ).thenReturn(
      "{\n" +
        "  \"phone\": \"1234\",\n" +
        "  \"valid\": false\n" +
        "}"
    );
    when( httpClient.send( any( HttpRequest.class ), any( BodyHandler.class ) ) )
      .thenReturn( mockResponse );
    assertFalse( phoneNumberValidator.isPhoneNumberValid( "1234" ) );
  }
}
