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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource( properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration" )
@ExtendWith( MockitoExtension.class )
public class PhoneManagementServiceTest {

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
  @DisplayName( "PhoneManagementService should store a valid PhoneRecord" )
  void testPhoneRecordServiceShouldStoreAPhoneRecord() throws Exception {
    Long id = 1L;
    PhoneRecord record = PhoneRecord.builder().id( id ).name( "John Doe" ).phoneNumber( "4155551234" ).build();
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( true );
    when( phoneRecordRepository.save( any() ) ).thenReturn( record );
    when( phoneRecordRepository.existsById( id ) ).thenReturn( true );

    mockMvc.perform( post( "/api/v1/phonerecords" )
             .contentType( MediaType.APPLICATION_JSON )
             .content( "{\"name\":\"John Doe\", \"phoneNumber\":\"4155551234\"}" ) )
           .andExpect( status().isCreated() );
  }

  @Test
  @DisplayName( "PhoneManagementService should not store an invalid PhoneRecord" )
  void testPhoneRecordServiceShouldNotStoreAnInvalidPhoneRecord() throws Exception {
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( false );
    mockMvc.perform( post( "/api/v1/phonerecords" )
             .contentType( MediaType.APPLICATION_JSON )
             .content( "{\"name\":\"Fake User\", \"phoneNumber\":\"123456789\"}" ) )
           .andExpect( status().isBadRequest() )
           .andExpect( jsonPath( "error" ).value( "invalid phone number" ) );
  }

  @Test
  @DisplayName( "PhoneManagementService should return all PhoneRecords" )
  void testPhoneRecordServiceShouldReturnAllPhoneRecords() throws Exception {
    List<PhoneRecord> records = List.of(
      PhoneRecord.builder().name( "John Doe" ).phoneNumber( "4155551234" ).build(),
      PhoneRecord.builder().name( "Jane Doe" ).phoneNumber( "2025550173" ).build(),
      PhoneRecord.builder().name( "Adam Something" ).phoneNumber( "4155550198" ).build()
    );
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( true );
    when( phoneRecordRepository.save( any() ) ).thenReturn( records.get( 0 ) );
    when( phoneRecordRepository.existsById( 1L ) ).thenReturn( true );
    when( phoneRecordRepository.existsById( 2L ) ).thenReturn( true );
    when( phoneRecordRepository.existsById( 3L ) ).thenReturn( true );
    when( phoneRecordRepository.findAll() ).thenReturn( records );
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( true );

    phoneRecordService.addPhoneRecords( records );

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