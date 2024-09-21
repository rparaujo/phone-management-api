package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/phonerecords")
public class PhoneRecordController {
    private final PhoneRecordService phoneRecordService;

    @Autowired
    public PhoneRecordController( PhoneRecordService phoneRecordService ) {
        this.phoneRecordService = phoneRecordService;
    }

    @GetMapping
    public List<PhoneRecord> getPhoneRecords() {
        return phoneRecordService.getPhoneRecords();
    }
}
