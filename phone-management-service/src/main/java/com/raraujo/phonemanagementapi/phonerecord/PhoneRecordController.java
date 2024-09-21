package com.raraujo.phonemanagementapi.phonerecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping( "api/v1/phonerecords" )
public class PhoneRecordController {
  @Autowired
  private PhoneRecordService phoneRecordService;

  @GetMapping
  public List<PhoneRecord> getPhoneRecords() {
    return phoneRecordService.getPhoneRecords();
  }

  @GetMapping( "/{id}" )
  public ResponseEntity<Object> getPhoneRecordById( @PathVariable( "id" ) Long phoneRecordId ) {
    Optional<PhoneRecord> optionalPhoneRecord = phoneRecordService.getPhoneRecord( phoneRecordId );
    return optionalPhoneRecord.<ResponseEntity<Object>>map( ResponseEntity::ok )
                              .orElseGet( () -> ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Phone record not found: " + phoneRecordId ) );
  }

  @PostMapping
  public ResponseEntity<Void> addPhoneRecord( @RequestBody Map<String, String> phoneRecordMap ) {
    ObjectMapper mapper = new ObjectMapper();
    PhoneRecord phoneRecord = mapper.convertValue( phoneRecordMap, PhoneRecord.class );
    phoneRecordService.addPhoneRecord( phoneRecord );

    return ResponseEntity.status( HttpStatus.CREATED ).build();
  }

  @DeleteMapping( "/{id}" )
  public ResponseEntity<Void> deletePhoneRecord( @PathVariable Long id ) {
    boolean deleted = phoneRecordService.deletePhoneRecord( id );
    if ( deleted ) {
      return ResponseEntity.noContent().build(); // HTTP 204 No Content
    } else {
      return ResponseEntity.notFound().build(); // HTTP 404 Not Found
    }
  }
}
