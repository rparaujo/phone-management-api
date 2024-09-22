package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.numbervalidationservice.PhoneNumberValidator;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneRecordServiceImpl implements PhoneRecordService {

  private static final Logger logger = LoggerFactory.getLogger( PhoneRecordServiceImpl.class );

  private final PhoneRecordRepository phoneRecordRepository;


  private final PhoneNumberValidator phoneNumberValidator;

  @Autowired
  public PhoneRecordServiceImpl( PhoneNumberValidator phoneNumberValidator, PhoneRecordRepository phoneRecordRepository ) {
    this.phoneNumberValidator = phoneNumberValidator;
    this.phoneRecordRepository = phoneRecordRepository;
  }


  @Override
  public Optional<List<PhoneRecord>> getPhoneRecords() {
    try {
      return Optional.of( phoneRecordRepository.findAll() );
    } catch ( Exception ex ) {
      logger.error( "Failed to get all records: " + ex.getMessage() );
      return Optional.empty();
    }
  }

  @Override
  public Optional<PhoneRecord> getPhoneRecord( Long phoneRecordId ) {
    try {
      return phoneRecordRepository.findById( phoneRecordId );
    } catch ( Exception ex ) {
      logger.error( "Failed to get record with id : " + phoneRecordId + " Message: " + ex.getMessage() );
      return Optional.empty();
    }
  }

  @Override
  public void addPhoneRecords( List<PhoneRecord> phoneRecords ) {
    phoneRecords.forEach( this::addPhoneRecord );
  }

  @Override
  public boolean addPhoneRecord( PhoneRecord phoneRecord ) {
    try {
      if ( !phoneNumberValidator.isPhoneNumberValid( phoneRecord.getPhoneNumber() ) ) {
        return false;
      }
      PhoneRecord savedRecord = phoneRecordRepository.save( phoneRecord );
      return phoneRecordRepository.existsById( savedRecord.getId() );
    } catch ( Exception ex ) {
      logger.error( "Failed to add phone record: " + ex.getMessage() );
      return false;
    }
  }

  @Override
  public boolean deletePhoneRecord( Long phoneRecordId ) {
    try {
      if ( !phoneRecordRepository.existsById( phoneRecordId ) ) {
        return false;
      }
      phoneRecordRepository.deleteById( phoneRecordId );
      return !phoneRecordRepository.existsById( phoneRecordId );
    } catch ( Exception ex ) {
      logger.error( "Failed to delete phone record: " + ex.getMessage() );
      return false;
    }
  }

  @Override
  @Transactional
  public OperationStatus updatePhoneRecord( PhoneRecord phoneRecord ) {
    try {
      PhoneRecord record = phoneRecordRepository.findById( phoneRecord.getId() ).orElseThrow(
        () -> new IllegalStateException( "Phone record with id: " + phoneRecord.getId() + " was not found" ) );
      BeanUtils.copyProperties( phoneRecord, record, "id" ); // Exclude 'id' to avoid overwriting
      phoneRecordRepository.save( record ); // Ensure the record is saved
    } catch ( IllegalStateException ex ) {
      return OperationStatus.NOT_FOUND;
    } catch ( Exception ex ) {
      logger.error( "Failed to update phone record: " + ex.getMessage() );
      return OperationStatus.UNKNOW_ERROR;
    }
    return OperationStatus.OK;
  }
}
