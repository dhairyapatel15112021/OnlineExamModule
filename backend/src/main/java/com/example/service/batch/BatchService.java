package com.example.service.batch;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Batch;
import com.example.repository.batch.BatchRepository;

@Service
public class BatchService {
    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public Batch createBatch(Batch batch){
        try{
            return batchRepository.save(batch);
        }   
        catch(Exception e){
            return null;
        }
    }

    public boolean isBatchExist(int year){
        return batchRepository.findByYear(year) != null;
    }

    public List<Batch> getBatches(){
        return batchRepository.findAll();
    }
}
