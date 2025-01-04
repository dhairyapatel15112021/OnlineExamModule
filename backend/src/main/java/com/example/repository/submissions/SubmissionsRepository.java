package com.example.repository.submissions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dto.Response;
import com.example.model.Submissions;

public interface SubmissionsRepository extends JpaRepository<Submissions, Integer> {

    @Query("SELECT new com.example.dto.Response(s.token) FROM Submissions s JOIN s.programmeResponse p WHERE p.programme.id = :programId")
    List<Response> findAllTokens(@Param("programId") int programId);

}


