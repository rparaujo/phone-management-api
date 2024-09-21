package com.raraujo.phonemanagementapi;


import com.raraujo.phonemanagementapi.phonerecord.PhoneRecordService;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class) // Extending to use JUnit5
public class PhoneManagementServiceIT {

    private final PhoneRecordService phoneRecordService;

    @Autowired
    public PhoneManagementServiceIT( PhoneRecordService phoneRecordService ) {
        this.phoneRecordService = phoneRecordService;
    }

    @Test
    @DisplayName("PhoneRecord Service should return PhoneRecords")
    void testPhoneRecordServiceShouldReturnSomePhoneRecords() {
        PhoneRecord phoneRecord = PhoneRecord
                .builder()
                .name("Joe")
                .phoneNumber("123")
                .build();
        phoneRecordService.addPhoneRecord(phoneRecord);
        final List<PhoneRecord> phoneRecords = phoneRecordService.getPhoneRecords();
        assertEquals(1, phoneRecords.size());
    }
}