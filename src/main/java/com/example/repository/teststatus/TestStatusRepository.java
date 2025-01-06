package com.example.repository.teststatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Teststatus;

public interface TestStatusRepository extends JpaRepository<Teststatus,Integer> {
    
    @Query("SELECT s FROM Teststatus s WHERE s.student.id = :studentId AND s.test.id = :testId")
    Teststatus findByTest_idAndStudent_id(@Param("studentId") int studentId , @Param("testId") int testId);

    @Query("SELECT r FROM Teststatus r WHERE r.student.id = :studentId")
    List<Teststatus> findByStudent_id(@Param("studentId") int studentId);
}
