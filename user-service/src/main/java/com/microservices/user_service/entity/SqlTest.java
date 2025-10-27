package com.microservices.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "sqlTesting")
@Entity
public class SqlTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String gender;
    public String department;

}
