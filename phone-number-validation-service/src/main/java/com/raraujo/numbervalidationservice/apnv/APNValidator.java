package com.raraujo.numbervalidationservice.apnv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raraujo.numbervalidationservice.PhoneNumberValidator;
import com.raraujo.numbervalidationservice.apnv.model.APNVResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APNValidator implements PhoneNumberValidator {

  private final HttpClient httpClient = HttpClient.newBuilder()
                                                  .version( HttpClient.Version.HTTP_2 )
                                                  .build();

  @Override
  public boolean isPhoneNumberValid( String phoneNumber ) {
    String apikey = System.getenv( "PHONE_VALIDATION_SERVICE_API_KEY" );
    String url = "https://phonevalidation.abstractapi.com/v1/?api_key=" + apikey + "&phone=" + phoneNumber;
    ObjectMapper mapper = new ObjectMapper();

    String response = this.getSync( url );

    APNVResponse validationResponse = null;
    try {
      if ( response.contains( "\"error\"" ) ) {
      } else {
        validationResponse = mapper.readValue( response, APNVResponse.class );
      }
    } catch ( JsonProcessingException e ) {
      throw new RuntimeException( e );
    }
    return validationResponse.isValid();
  }

  private String getSync( String url ) {
    HttpRequest request = HttpRequest.newBuilder()
                                     .uri( URI.create( url ) )
                                     .GET()
                                     .build();

    try {
      HttpResponse<String> response = httpClient.send( request, HttpResponse.BodyHandlers.ofString() );
      return response.body();
    } catch ( IOException | InterruptedException e ) {
      e.printStackTrace();
      return null; // Handle exceptions as appropriate
    }
  }
}
