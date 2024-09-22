package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.numbervalidationservice.PhoneNumberValidator;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneRecordServiceImpl implements PhoneRecordService {

  @Autowired
  private PhoneRecordRepository phoneRecordRepository;


  private final PhoneNumberValidator phoneNumberValidator;

  @Autowired
  public PhoneRecordServiceImpl( PhoneNumberValidator phoneNumberValidator ) {
    this.phoneNumberValidator = phoneNumberValidator;
  }


  @Override
  public List<PhoneRecord> getPhoneRecords() {
    return phoneRecordRepository.findAll();
  }

  @Override
  public Optional<PhoneRecord> getPhoneRecord( Long phoneRecordId ) {
    return phoneRecordRepository.findById( phoneRecordId );
  }

  @Override
  public void addPhoneRecords( List<PhoneRecord> phoneRecords ) {
    phoneRecords.forEach( this::addPhoneRecord );
  }

  @Override
  public boolean addPhoneRecord( PhoneRecord phoneRecord ) {
    if ( !phoneNumberValidator.isPhoneNumberValid( phoneRecord.getPhoneNumber() ) ) {
      return false;
    }
    PhoneRecord savedRecord = phoneRecordRepository.save( phoneRecord );
    return phoneRecordRepository.existsById( savedRecord.getId() );
  }


  @Override
  public boolean deletePhoneRecord( Long phoneRecordId ) {
    if ( !phoneRecordRepository.existsById( phoneRecordId ) ) {
      return false;
    }
    phoneRecordRepository.deleteById( phoneRecordId );
    return !phoneRecordRepository.existsById( phoneRecordId );
  }

  @Override
  @Transactional
  public void updatePhoneRecord( PhoneRecord phoneRecord ) {

  }
}
