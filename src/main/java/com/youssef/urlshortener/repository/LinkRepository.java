package com.youssef.urlshortener.repository;

import com.youssef.urlshortener.entity.Link;
import com.youssef.urlshortener.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByCode(String code);
    List<Link> findByUser(User user);
    long countByUser(User user);
}