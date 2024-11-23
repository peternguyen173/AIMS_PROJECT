package com.example.demo.repository;

import com.example.demo.entity.OrderMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMediaRepository extends JpaRepository<OrderMedia, Long> {
}
