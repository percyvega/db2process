package com.percyvega.db2process.service;

import com.percyvega.db2process.domain.MyRecord;
import com.percyvega.db2process.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class MyServiceImpl implements MyService {

    private MyRepository myRepository;

    @Autowired
    public MyServiceImpl(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    @Override
    @Transactional
    public Collection<MyRecord> findToInquire(int recordCount) throws DataAccessException {
        Collection<MyRecord> myRecords = myRepository.findToInquire(MyRecord.Status.QUEUED.toString(), recordCount);
        for(MyRecord myRecord : myRecords) {
            myRecord.setStatus(MyRecord.Status.PICKED_UP);
            save(myRecord);
        }
        return myRecords;
    }

    @Override
    public Collection<MyRecord> findAll() throws DataAccessException {
        return myRepository.findAll();
    }

    @Override
    public void save(MyRecord myRecord) throws DataAccessException {
        myRepository.save(myRecord);
    }

}
