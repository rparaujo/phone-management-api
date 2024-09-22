package com.raraujo.phonemanagementapi;

import com.raraujo.numbervalidationservice.PhoneNumberValidator;
import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordRepository;
import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordServiceImpl;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource( properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration" )
@ExtendWith( MockitoExtension.class )
public class PhoneManagementServiceUpdateByIdTest {

  @MockBean
  private PhoneNumberValidator phoneNumberValidator;

  @MockBean
  private PhoneRecordRepository phoneRecordRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  @InjectMocks
  private PhoneRecordServiceImpl phoneRecordService;

  @Test
  @DisplayName( "PhoneManagementService should update a PhoneRecord by ID" )
  void testPhoneRecordServiceShouldUpdatePhoneRecordById() throws Exception {
    Long id = 1L;
    PhoneRecord record = PhoneRecord.builder().id( id ).name( "John Doe" ).phoneNumber( "4155551234" ).build();
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( true );
    when( phoneRecordRepository.findById( id ) ).thenReturn( Optional.of( record ) );
    when( phoneRecordRepository.save( any() ) ).thenReturn( record );

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
    Long id = 9999L;
    when( phoneRecordRepository.findById( id ) ).thenReturn( Optional.empty() );

    mockMvc.perform( put( "/api/v1/phonerecords/{id}", id )
             .contentType( MediaType.APPLICATION_JSON )
             .content( "{\"name\":\"John Doe\", \"phoneNumber\":\"4155551235\"}" ) )
           .andExpect( status().isNotFound() );
  }
}