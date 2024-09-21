package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;

import java.util.List;

public interface PhoneRecordService {
    List<PhoneRecord> getPhoneRecords();
    PhoneRecord getPhoneRecord(Long phoneRecordId);
    void addPhoneRecord(PhoneRecord phoneRecord);
    void deletePhoneRecord(Long phoneRecordId);
    void updatePhoneRecord(PhoneRecord phoneRecord);

}
