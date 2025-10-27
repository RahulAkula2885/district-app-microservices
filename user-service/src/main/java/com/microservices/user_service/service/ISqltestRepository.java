package com.microservices.user_service.service;

import com.microservices.user_service.entity.SqlTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISqltestRepository extends JpaRepository<SqlTest,Long> {


    @Query(value = "Select * from sql_testing s where s.gender='Male' And s.department NOT IN" +
            " (Select department from sql_testing where gender='Female')",nativeQuery = true)
    List<SqlTest> findQueryWithOnlyMaleBasedOndepartments();
}
