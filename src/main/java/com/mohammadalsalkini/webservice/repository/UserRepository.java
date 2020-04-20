package com.mohammadalsalkini.webservice.repository;

import com.mohammadalsalkini.webservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammad Alsalkini
 * @project webservice
 * @created 19.04.2020 - 13:13
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
