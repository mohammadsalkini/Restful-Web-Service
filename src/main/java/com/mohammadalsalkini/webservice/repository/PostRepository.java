package com.mohammadalsalkini.webservice.repository;

import com.mohammadalsalkini.webservice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mohammad Alsalkini
 * @project webservice
 * @created 19.04.2020 - 19:15
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
