package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneRecordServiceImpl implements PhoneRecordService{

    @Autowired
    private PhoneRecordRepository phoneRecordRepository;

    @Override
    public List<PhoneRecord> getPhoneRecords() {
        return phoneRecordRepository.findAll();
    }

    @Override
    public PhoneRecord getPhoneRecord(Long phoneRecordId) {
        return null;
    }

    @Override
    public void addPhoneRecord(PhoneRecord phoneRecord) {
        // TODO: what if the insert fails? Error validation...
        phoneRecordRepository.save(phoneRecord);
    }

    @Override
    public void deletePhoneRecord(Long phoneRecordId) {

    }

    @Override
    public void updatePhoneRecord(PhoneRecord phoneRecord) {

    }
}
