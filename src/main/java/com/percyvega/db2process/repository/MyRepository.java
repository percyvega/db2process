package com.percyvega.db2process.repository;

import com.percyvega.db2process.domain.MyRecord;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MyRepository extends Repository<MyRecord, Integer> {

    @Query(value = "select * FROM SA.INQ_SMO_RESULT where STATUS = :status and rownum <= :recordCount order by CREATION_DATE desc", nativeQuery = true)
    public Collection<MyRecord> findToInquire(@Param("status") String status, @Param("recordCount") int recordCount) throws DataAccessException;

    public Collection<MyRecord> findAll() throws DataAccessException;

    void save(MyRecord myRecord) throws DataAccessException;

}