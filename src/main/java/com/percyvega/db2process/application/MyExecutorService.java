package com.percyvega.db2process.application;

import com.percyvega.db2process.domain.MyRecord;
import com.percyvega.db2process.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class MyExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(MyExecutorService.class);

    @Autowired
    private MyService myService;

    public void run(String... args) throws Exception {
        logger.debug("Starting main(" + Arrays.toString(args) + ")");

        Collection<MyRecord> myRecords;
        ExecutorService executorService;
        while (true) {

            // lock, pick up and update up to x records at a time
            myRecords = myService.findToInquire(10);

            if (myRecords.isEmpty()) {

                logger.debug("isEmpty(): " + true);
                Thread.sleep(15000);

            } else {

                // number is the max number of threads to work on the records
                executorService = Executors.newFixedThreadPool(5);

                ProcessRecord processRecord;
                for (MyRecord myRecord : myRecords) {
                    processRecord = new ProcessRecord(myRecord);
                    executorService.execute(processRecord);
                }

                logger.debug("ExecutorService completed.");
                executorService.shutdown();

                logger.debug("Starting wait.");
                executorService.awaitTermination(15000, TimeUnit.MILLISECONDS);
                logger.debug("Finishing wait.");

            }
        }
    }

    private class ProcessRecord implements Runnable {

        MyRecord myRecord;

        public ProcessRecord(MyRecord myRecord) {
            this.myRecord = myRecord;
        }

        @Override
        public void run() {
            logger.debug("Processing record with objid: " + myRecord.getObjid());

            try {

                myRecord.setTryCount(myRecord.getTryCount() + 1);
                myRecord.setStatus(MyRecord.Status.PROCESSING);
                myService.save(myRecord);
                myRecord.setResponse("This is a response!");
                myRecord.setStatus(MyRecord.Status.PROCESSED);
                myService.save(myRecord);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
