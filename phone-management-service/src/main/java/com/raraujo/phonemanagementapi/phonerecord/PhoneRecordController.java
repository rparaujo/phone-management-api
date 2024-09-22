package com.raraujo.phonemanagementapi.phonerecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping( "api/v1/phonerecords" )
@Validated
public class PhoneRecordController {

  private final ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private PhoneRecordService phoneRecordService;

  @GetMapping( "/all" )
  public ResponseEntity<Map<String, Object>> getPhoneRecords() {
    List<PhoneRecord> records = phoneRecordService.getPhoneRecords().orElse( Collections.emptyList() );
    if ( records.isEmpty() ) {
      return ResponseEntity.status( HttpStatus.BAD_REQUEST ).build();
    }

    // Create the custom response
    Map<String, Object> response = new HashMap<>();
    response.put( "total", records.size() );
    response.put( "records", records );

    return ResponseEntity.ok( response );
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> getPageablePhoneRecords(
    @RequestParam( defaultValue = "0" ) int page,
    @RequestParam( defaultValue = "10" ) int size ) {

    Pageable pageable = PageRequest.of( page, size );
    Page<PhoneRecord> records = phoneRecordService.getPaginatedPhoneRecords( pageable ).orElse( Page.empty() );

    if ( records.isEmpty() ) {
      return ResponseEntity.status( HttpStatus.BAD_REQUEST ).build();
    }

    // Create the custom response
    Map<String, Object> response = new HashMap<>();
    response.put( "total", records.getTotalElements() );
    response.put( "page", records.getNumber() );
    response.put( "limit", records.getSize() );
    response.put( "records", records.getContent() );

    return ResponseEntity.ok( response );
  }


  @GetMapping( "/{id}" )
  public ResponseEntity<Object> getPhoneRecordById( @PathVariable( "id" ) @Positive( message = "Id must be a positive number" ) Long phoneRecordId ) {
    Optional<PhoneRecord> optionalPhoneRecord = phoneRecordService.getPhoneRecord( phoneRecordId );
    return optionalPhoneRecord.<ResponseEntity<Object>>map( ResponseEntity::ok )
                              .orElseGet( () -> ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Phone record not found: " + phoneRecordId ) );
  }

  @PostMapping
  public ResponseEntity<Object> addPhoneRecord( @RequestBody Map<String, String> phoneRecordMap ) {
    PhoneRecord phoneRecord = mapper.convertValue( phoneRecordMap, PhoneRecord.class );
    Optional<PhoneRecord> record = phoneRecordService.addPhoneRecord( phoneRecord );

    return record.<ResponseEntity<Object>>map( value -> ResponseEntity.status( HttpStatus.CREATED ).body( value ) )
                 .orElseGet( () -> ResponseEntity.status( HttpStatus.BAD_REQUEST )
                                                 .contentType( MediaType.APPLICATION_JSON )
                                                 .body( "{\"error\":\"invalid phone number\"}" ) );
  }

  @DeleteMapping( "/{id}" )
  public ResponseEntity<Void> deletePhoneRecordById( @PathVariable Long id ) {
    boolean deleted = phoneRecordService.deletePhoneRecord( id );
    if ( deleted ) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping( "/{id}" )
  public ResponseEntity<Void> updatePhoneRecordById( @PathVariable Long id, @RequestBody Map<String, String> phoneRecordMap ) {
    PhoneRecord phoneRecord = mapper.convertValue( phoneRecordMap, PhoneRecord.class );
    phoneRecord.setId( id );

    OperationStatus status = phoneRecordService.updatePhoneRecord( phoneRecord );

    switch ( status ) {
      case NOT_FOUND:
        return ResponseEntity.notFound().build();
      case OK:
        return ResponseEntity.ok().build();
    }
    return ResponseEntity.internalServerError().build();
  }
}
