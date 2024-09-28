package com.raraujo.phonemanagementapi.phonerecord.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder // To support inheritance if we extend the class
@NoArgsConstructor
// JPA or Hibernate with @Entityrequire the classes to have a no-arg ctor because they need to create instances of entities using reflection during runtime
// @Getter can be removed. It's part of @Data
// @ToString( callSuper = true ) Can be removed. It's part of @Data
@Data // Shortcut to @Getter, @Setter, @RequiredArgsConstructor, @EqualsAndHashCode, @ToString
@Entity
// mark a class as a JPA entity, which means the class is mapped to a database table. This allows the class to be managed by JPA or Hibernate for persistence.
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@AllArgsConstructor
public class PhoneRecord {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Setter( AccessLevel.NONE ) // prevent Lombok from generating a setter for the id field. Note: Added after review
  @EqualsAndHashCode.Include
  // specify the primary key (@Id) as the basis for these methods while ignoring other fields that might cause problems or inefficiencies Note: Added after review
  // For example using fields that are lazy-loaded in a toString can cause early initialization if we where to call the toString method
  private Long id;

  private String name;
  private String phoneNumber;
}
