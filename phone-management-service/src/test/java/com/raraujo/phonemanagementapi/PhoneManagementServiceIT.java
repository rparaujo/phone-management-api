package com.raraujo.phonemanagementapi;


import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordService;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PhoneManagementServiceIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PhoneRecordService phoneRecordService;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void sleep() throws InterruptedException {
    // We need to sleep so that we do not exceed external API requests per second
    Thread.sleep( 1000 );

    jdbcTemplate.execute( "ALTER TABLE phone_record AUTO_INCREMENT = 1" );
  }

  @Test
  @DisplayName( "PhoneManagementService should store a valid PhoneRecord" )
  void testPhoneRecordServiceShouldStoreAPhoneRecord() throws Exception {
    mockMvc.perform( post( "/api/v1/phonerecords" )
             .contentType( MediaType.APPLICATION_JSON )
             .content( "{\"name\":\"John Doe\", \"phoneNumber\":\"4155551234\"}" ) )
           .andExpect( status().isCreated() );
  }

  @Test
  @DisplayName( "PhoneManagementService should not store an invalid PhoneRecord" )
  void testPhoneRecordServiceShouldNotStoreAnInvalidPhoneRecord() throws Exception {
    mockMvc.perform( post( "/api/v1/phonerecords" )
             .contentType( MediaType.APPLICATION_JSON )
             .content( "{\"name\":\"Fake User\", \"phoneNumber\":\"123456789\"}" ) )
           .andExpect( status().isBadRequest() );
  }

  @Test
  @DisplayName( "PhoneManagementService should return all PhoneRecords" )
  void testPhoneRecordServiceShouldReturnAllPhoneRecords() throws Exception {
    phoneRecordService.addPhoneRecord( PhoneRecord.builder().name( "John Doe" ).phoneNumber( "4155551234" ).build() );
    Thread.sleep( 1000 );

    phoneRecordService.addPhoneRecord( PhoneRecord.builder().name( "Jane Doe" ).phoneNumber( "2025550173" ).build() );
    Thread.sleep( 1000 );

    phoneRecordService.addPhoneRecord( PhoneRecord.builder().name( "Adam Something" ).phoneNumber( "4155550198" ).build() );
    Thread.sleep( 1000 );

    mockMvc.perform( get( "/api/v1/phonerecords" )
             .contentType( MediaType.APPLICATION_JSON ) )
           .andExpect( status().isOk() )
           .andExpect( jsonPath( "$", hasSize( 3 ) ) )
           .andExpect( jsonPath( "$[0].name" ).value( "John Doe" ) )
           .andExpect( jsonPath( "$[0].phoneNumber" ).value( "4155551234" ) )
           .andExpect( jsonPath( "$[1].name" ).value( "Jane Doe" ) )
           .andExpect( jsonPath( "$[1].phoneNumber" ).value( "2025550173" ) )
           .andExpect( jsonPath( "$[2].name" ).value( "Adam Something" ) )
           .andExpect( jsonPath( "$[2].phoneNumber" ).value( "4155550198" ) );
  }
}