package com.example.service.batch;

import org.springframework.stereotype.Service;

import com.example.model.Batch;
import com.example.repository.batch.batchRepository;

@Service
public class batchService {
    private final batchRepository batchRepository;

    public batchService(com.example.repository.batch.batchRepository batchRepository) {
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
