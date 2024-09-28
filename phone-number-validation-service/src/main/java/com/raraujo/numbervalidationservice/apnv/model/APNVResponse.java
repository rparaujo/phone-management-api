package com.raraujo.numbervalidationservice.apnv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor // required by jackson.databind
@Getter
@ToString( callSuper = true )
public class APNVResponse {

  private boolean valid;
  private String location;
  private String carrier;
  private String type;
  private String phone;
  private Format format;
  private Country country;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Format {
    private String international;
    private String local;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Country {
    private String code;
    private String name;
    private String prefix;
  }
}
