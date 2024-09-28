package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.numbervalidationservice.PhoneNumberValidator;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * is a specialization of the @Component annotation that indicates that a class is a service layer component.
 * is typically used to annotate classes that hold business logic. These classes often interact with repositories to perform operations on the data model.
 */
@Service
//public class PhoneRecordServiceImpl implements PhoneRecordService {
public class PhoneRecordServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger( PhoneRecordServiceImpl.class );

  private final PhoneRecordRepository phoneRecordRepository;


  private final PhoneNumberValidator phoneNumberValidator;

  @Autowired
  public PhoneRecordServiceImpl( PhoneNumberValidator phoneNumberValidator, PhoneRecordRepository phoneRecordRepository ) {
    this.phoneNumberValidator = phoneNumberValidator;
    this.phoneRecordRepository = phoneRecordRepository;
  }

  //  @Override
  public Optional<List<PhoneRecord>> getPhoneRecords() {
    try {
      return Optional.of( phoneRecordRepository.findAll() );
    } catch ( Exception ex ) {
      logger.error( "Failed to get all records: " + ex.getMessage() );
      return Optional.empty();
    }
  }

  //  @Override
  public Optional<Page<PhoneRecord>> getPaginatedPhoneRecords( Pageable pageable ) {
    try {
      return Optional.of( phoneRecordRepository.findAll( pageable ) );
    } catch ( Exception ex ) {
      logger.error( "Failed to get pageable records: " + ex.getMessage() );
      return Optional.empty();
    }
  }

  //  @Override
  public Optional<PhoneRecord> getPhoneRecord( Long phoneRecordId ) {
    try {
      return phoneRecordRepository.findById( phoneRecordId );
    } catch ( Exception ex ) {
      logger.error( "Failed to get record with id : " + phoneRecordId + " Message: " + ex.getMessage() );
      return Optional.empty();
    }
  }

  //  @Override
  public void addPhoneRecords( List<PhoneRecord> phoneRecords ) {
    phoneRecords.forEach( this::addPhoneRecord );
  }

  //  @Override
  public Optional<PhoneRecord> addPhoneRecord( PhoneRecord phoneRecord ) {
    try {
      if ( !phoneNumberValidator.isPhoneNumberValid( phoneRecord.getPhoneNumber() ) ) {
        return Optional.empty();
      }
      return Optional.of( phoneRecordRepository.save( phoneRecord ) );
    } catch ( Exception ex ) {
      logger.error( "Failed to add phone record: " + ex.getMessage() );
      return Optional.empty();
    }
  }

  //  @Override
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

  //  @Override

  /**
   * The whole method behaves like a transaction.
   * If the update fails for some reason the update is reverted and the database is kept in a
   * consistent state
   */
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
