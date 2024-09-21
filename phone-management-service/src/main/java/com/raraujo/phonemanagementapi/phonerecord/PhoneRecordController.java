package com.raraujo.phonemanagementapi.phonerecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @PostMapping
    public void addPhoneRecord(@RequestBody Map<String, String> phoneRecordMap) {
        ObjectMapper mapper = new ObjectMapper();
        PhoneRecord phoneRecord = mapper.convertValue(phoneRecordMap, PhoneRecord.class);
        phoneRecordService.addPhoneRecord(phoneRecord);
    }
}
