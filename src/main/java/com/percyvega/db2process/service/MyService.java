package com.percyvega.db2process.service;

import com.percyvega.db2process.domain.MyRecord;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface MyService {

    Collection<MyRecord> findToInquire(int recordCount) throws DataAccessException;

    Collection<MyRecord> findAll() throws DataAccessException;

    void save(MyRecord myRecord) throws DataAccessException;

}
