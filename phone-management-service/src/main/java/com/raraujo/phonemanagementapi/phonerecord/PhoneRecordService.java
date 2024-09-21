package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;

import java.util.List;
import java.util.Optional;

public interface PhoneRecordService {
  List<PhoneRecord> getPhoneRecords();

  Optional<PhoneRecord> getPhoneRecord( Long phoneRecordId );

  void addPhoneRecords( List<PhoneRecord> phoneRecords );

  void addPhoneRecord( PhoneRecord phoneRecord );

  void deletePhoneRecord( Long phoneRecordId );

  void updatePhoneRecord( PhoneRecord phoneRecord );

}
