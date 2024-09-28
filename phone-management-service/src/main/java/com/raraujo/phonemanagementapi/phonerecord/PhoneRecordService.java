package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Not actually needed by Spring and can be removed.
 * In fact, it is not being used at the moment.
 * <p>
 * In a real application, using interfaces can help with decoupling the implementation and testability for mocks
 */
public interface PhoneRecordService {
  Optional<List<PhoneRecord>> getPhoneRecords();

  Optional<Page<PhoneRecord>> getPaginatedPhoneRecords( Pageable pageable );

  Optional<PhoneRecord> getPhoneRecord( Long phoneRecordId );

  void addPhoneRecords( List<PhoneRecord> phoneRecords );

  Optional<PhoneRecord> addPhoneRecord( PhoneRecord phoneRecord );

  boolean deletePhoneRecord( Long phoneRecordId );

  OperationStatus updatePhoneRecord( PhoneRecord phoneRecord );

}
