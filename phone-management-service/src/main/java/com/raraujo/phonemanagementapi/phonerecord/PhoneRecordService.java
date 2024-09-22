package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PhoneRecordService {
  Optional<List<PhoneRecord>> getPhoneRecords();

  Optional<Page<PhoneRecord>> getPaginatedPhoneRecords( Pageable pageable );

  Optional<PhoneRecord> getPhoneRecord( Long phoneRecordId );

  void addPhoneRecords( List<PhoneRecord> phoneRecords );

  boolean addPhoneRecord( PhoneRecord phoneRecord );

  boolean deletePhoneRecord( Long phoneRecordId );

  OperationStatus updatePhoneRecord( PhoneRecord phoneRecord );

}
