package com.raraujo.phonemanagementapi;


import com.raraujo.numbervalidationservice.PhoneNumberValidator;
import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordServiceImpl;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith( MockitoExtension.class )
public class PhoneManagementServiceUpdateByIdIT {

  @MockBean
  private PhoneNumberValidator phoneNumberValidator;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  @InjectMocks
  private PhoneRecordServiceImpl phoneRecordService;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void sleep() throws InterruptedException {
    jdbcTemplate.execute( "ALTER TABLE phone_record AUTO_INCREMENT = 1" ); // Reset DB auto increment
  }

  @Test
  @DisplayName( "PhoneManagementService should update a PhoneRecord by ID" )
  void testPhoneRecordServiceShouldUpdatePhoneRecordById() throws Exception {
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( true );
    phoneRecordService.addPhoneRecord( PhoneRecord.builder().name( "John Doe" ).phoneNumber( "4155551234" ).build() );

    int id = 1;
    mockMvc.perform( put( "/api/v1/phonerecords/{id}", id )
             .contentType( MediaType.APPLICATION_JSON )
             .content( "{\"name\":\"John Doe\", \"phoneNumber\":\"4155551235\"}" ) )
           .andExpect( status().isOk() );

    mockMvc.perform( get( "/api/v1/phonerecords/{id}", id )
             .contentType( MediaType.APPLICATION_JSON ) )
           .andExpect( status().isOk() )
           .andExpect( jsonPath( "$.name" ).value( "John Doe" ) )
           .andExpect( jsonPath( "$.phoneNumber" ).value( "4155551235" ) );
  }

  @Test
  @DisplayName( "PhoneManagementService should return 404 when updating a PhoneRecord that does not exist" )
  void testPhoneRecordServiceShouldReturn404WhenUpdatePhoneRecordByIdDoesNotExist() throws Exception {
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( true );
    phoneRecordService.addPhoneRecord( PhoneRecord.builder().name( "John Doe" ).phoneNumber( "4155551234" ).build() );

    int id = 99999;
    mockMvc.perform( put( "/api/v1/phonerecords/{id}", id )
             .contentType( MediaType.APPLICATION_JSON )
             .content( "{\"name\":\"John Doe\", \"phoneNumber\":\"4155551235\"}" ) )
           .andExpect( status().isNotFound() );
  }
}