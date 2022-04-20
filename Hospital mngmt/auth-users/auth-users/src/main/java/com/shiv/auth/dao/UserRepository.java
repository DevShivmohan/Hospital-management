package com.shiv.auth.dao;

import com.shiv.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByEmail(String email);

    @Query(value = "select * from user;",nativeQuery = true)
    public Optional<List<User>> getUsers();
}
