package com.example.bytecookie.domain.user.repository;

import com.example.bytecookie.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
