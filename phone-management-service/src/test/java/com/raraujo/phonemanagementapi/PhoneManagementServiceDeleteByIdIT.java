package com.raraujo.phonemanagementapi;


import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordService;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PhoneManagementServiceDeleteByIdIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private PhoneRecordService phoneRecordService;

  @BeforeEach
  void sleep() throws InterruptedException {
    // We need to sleep so that we do not exceed external API requests per second
    Thread.sleep( 1000 );
    jdbcTemplate.execute( "ALTER TABLE phone_record AUTO_INCREMENT = 1" );
  }

  @Test
  @DisplayName( "PhoneManagementService should delete a PhoneRecord by ID" )
  void testPhoneRecordServiceShouldDeleteAPhoneRecordById() throws Exception {
    phoneRecordService.addPhoneRecord( PhoneRecord.builder().name( "John Doe" ).phoneNumber( "4155551234" ).build() );

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