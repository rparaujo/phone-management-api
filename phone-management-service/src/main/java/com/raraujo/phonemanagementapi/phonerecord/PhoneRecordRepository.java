package com.raraujo.phonemanagementapi.phonerecord;

import com.raraujo.phonemanagementapi.phonerecord.model.PhoneRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Encapsulates the logic required to access data sources like databases
 * <p>
 * The PhoneRecordRepository interface extends JpaRepository, providing a set of standard methods for
 * CRUD operations without needing to implement them.
 * <p>
 * It's a repository of PhoneRecords and the ID is of type Long
 */
@Repository
public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, Long> {
}
