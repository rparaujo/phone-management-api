package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneRecordServiceImpl implements PhoneRecordService{
    @Override
    public List<PhoneRecord> getPhoneRecords() {
        return List.of(
                PhoneRecord.builder().name("Joe").phoneNumber("123").build(),
                PhoneRecord.builder().name("Matt").phoneNumber("456").build()
        );
    }

    @Override
    public PhoneRecord getPhoneRecord(Long phoneRecordId) {
        return null;
    }

    @Override
    public void addPhoneRecord(PhoneRecord phoneRecord) {

    }

    @Override
    public void deletePhoneRecord(Long phoneRecordId) {

    }

    @Override
    public void updatePhoneRecord(PhoneRecord phoneRecord) {

    }
}
