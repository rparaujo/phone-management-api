package com.raraujo.phonemanagementapi;


import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordService;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PhoneManagementServiceDeleteByIdIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PhoneRecordService phoneRecordService;

  @BeforeEach
  void setup() {
    phoneRecordService.addPhoneRecords(
      List.of(
        PhoneRecord.builder().name( "John Doe" ).phoneNumber( "123456789" ).build(),
        PhoneRecord.builder().name( "Jane Doe" ).phoneNumber( "987654321" ).build(),
        PhoneRecord.builder().name( "Adam Something" ).phoneNumber( "543216789" ).build()
      )
    );
  }

  @Test
  @DisplayName( "PhoneManagementService should delete a PhoneRecord by ID" )
  void testPhoneRecordServiceShouldDeleteAPhoneRecordById() throws Exception {
    int id = 1;
    mockMvc.perform( delete( "/api/v1/phonerecords/{id}", id ) )
           .andExpect( status().isNoContent() );
  }

  @Test
  @DisplayName( "PhoneManagementService should return 404 a when trying to delete a non existing PhoneRecord by ID" )
  void testPhoneRecordServiceShouldReturn404WhenDeletingANonExistingPhoneRecord() throws Exception {
    int id = 99999;
    mockMvc.perform( delete( "/api/v1/phonerecords/{id}", id ) )
           .andExpect( status().isNotFound() );
  }
}