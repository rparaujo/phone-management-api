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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource( properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration" )
@ExtendWith( MockitoExtension.class )
public class PhoneManagementServiceDeleteByIdTest {

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
  @DisplayName( "PhoneManagementService should delete a PhoneRecord by ID" )
  void testPhoneRecordServiceShouldDeleteAPhoneRecordById() throws Exception {
    Long id = 1L;
    phoneRecordService.addPhoneRecord( PhoneRecord.builder().name( "John Doe" ).phoneNumber( "4155551234" ).build() );
    when( phoneNumberValidator.isPhoneNumberValid( anyString() ) ).thenReturn( true );
    when( phoneRecordRepository.existsById( id ) ).thenReturn( true ).thenReturn( false );
    doNothing().when( phoneRecordRepository ).deleteById( id );

    mockMvc.perform( delete( "/api/v1/phonerecords/{id}", id ) )
           .andExpect( status().isNoContent() );
  }

  @Test
  @DisplayName( "PhoneManagementService should return 404 a when trying to delete a non existing PhoneRecord by ID" )
  void testPhoneRecordServiceShouldReturn404WhenDeletingANonExistingPhoneRecord() throws Exception {
    Long id = 9999L;
    when( phoneRecordRepository.existsById( id ) ).thenReturn( false );

    mockMvc.perform( delete( "/api/v1/phonerecords/{id}", id ) )
           .andExpect( status().isNotFound() );
  }
}