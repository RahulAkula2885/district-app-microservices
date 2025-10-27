package com.microservices.user_service;

import com.microservices.user_service.entity.SqlTest;
import com.microservices.user_service.service.ISqltestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    public ISqltestRepository iSqltestRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        List<SqlTest> sqlTestList = createRequest();

        System.out.println(sqlTestList);

        iSqltestRepository.saveAll(sqlTestList);


        List<SqlTest> sqlTestList1 = iSqltestRepository.findAll();
        System.out.println(sqlTestList1);

        List<SqlTest> finalList = iSqltestRepository.findQueryWithOnlyMaleBasedOndepartments();
        System.out.println(finalList);
    }

    private List<SqlTest> createRequest() {

        List<SqlTest> sqlTestList = new ArrayList<>();

        SqlTest sqlTest1 = new SqlTest();
        SqlTest sqlTest2 = new SqlTest();
        SqlTest sqlTest3 = new SqlTest();
        SqlTest sqlTest4 = new SqlTest();
        SqlTest sqlTest5 = new SqlTest();


        sqlTest1.setDepartment("IT");
        sqlTest1.setName("Rahul");
        sqlTest1.setGender("Male");

        sqlTest2.setDepartment("IT");
        sqlTest2.setName("Janu");
        sqlTest2.setGender("Female");

        sqlTest3.setDepartment("HR");
        sqlTest3.setName("Rahul HR");
        sqlTest3.setGender("Male");

        sqlTest4.setDepartment("HR");
        sqlTest4.setName("HR HRaa");
        sqlTest4.setGender("Female");

        sqlTest5.setDepartment("QA");
        sqlTest5.setName("QA Rahul");
        sqlTest5.setGender("Male");

        sqlTestList.add(sqlTest1);
        sqlTestList.add(sqlTest2);
        sqlTestList.add(sqlTest3);
        sqlTestList.add(sqlTest4);
        sqlTestList.add(sqlTest5);

        return sqlTestList;
    }
}
