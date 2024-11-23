package com.example.demo.repository;

import com.example.demo.entity.CartMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMediaRepository extends JpaRepository<CartMedia, Long> {

}
