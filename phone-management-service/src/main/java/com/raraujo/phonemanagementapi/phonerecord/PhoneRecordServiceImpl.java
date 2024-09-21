package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneRecordServiceImpl implements PhoneRecordService{

    private final List<PhoneRecord> phoneRecords = new ArrayList<>();

    @Override
    public List<PhoneRecord> getPhoneRecords() {
        return phoneRecords;
    }

    @Override
    public PhoneRecord getPhoneRecord(Long phoneRecordId) {
        return null;
    }

    @Override
    public void addPhoneRecord(PhoneRecord phoneRecord) {
        phoneRecords.add(phoneRecord);
    }

    @Override
    public void deletePhoneRecord(Long phoneRecordId) {

    }

    @Override
    public void updatePhoneRecord(PhoneRecord phoneRecord) {

    }
}
