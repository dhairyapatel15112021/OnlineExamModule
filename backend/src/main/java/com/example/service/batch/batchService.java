package com.example.service.batch;

import org.springframework.stereotype.Service;

import com.example.model.Batch;
import com.example.repository.batch.BatchRepository;

@Service
public class BatchService {
    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public boolean createBatch(Batch batch){
        try{
            batchRepository.save(batch);
            return true;
        }   
        catch(Exception e){
            return false;
        }
    }
}
