package com.microservices.user_service.repository;

import com.microservices.user_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {

    @Query("SELECT o from User o where o.email=?1 and o.deleted = false")
    Optional<User> findByEmailAndDeletedFalse(String email);

    @Query("SELECT o from User o where o.countryCode=?1 and o.mobileNumber =?2 and o.deleted = false")
    Optional<User> findByCountryCodeAndMobileNumber(Integer countryCode, String mobileNumber);

    @Query(value = "select * from users order by CREATED_TIME desc", nativeQuery = true)
    Page<User> findAllUsers(Pageable pageable);

    @Query("SELECT o from User o where o.email=?1 and o.deleted = false")
    Optional<User> findByUsername(String username);
}
