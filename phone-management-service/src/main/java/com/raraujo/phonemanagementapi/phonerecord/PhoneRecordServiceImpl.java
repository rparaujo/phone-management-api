package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneRecordServiceImpl implements PhoneRecordService {

  @Autowired
  private PhoneRecordRepository phoneRecordRepository;

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
  public void addPhoneRecord( PhoneRecord phoneRecord ) {
    // TODO: what if the insert fails? Error validation...
    phoneRecordRepository.save( phoneRecord );
  }

  @Override
  public void deletePhoneRecord( Long phoneRecordId ) {

  }

  @Override
  public void updatePhoneRecord( PhoneRecord phoneRecord ) {

  }
}
