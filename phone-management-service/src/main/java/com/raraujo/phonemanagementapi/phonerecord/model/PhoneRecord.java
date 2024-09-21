package com.raraujo.phonemanagementapi.phonerecord.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@ToString(callSuper = true)
public class PhoneRecord {
    private String name;
    private String phoneNumber;
}
