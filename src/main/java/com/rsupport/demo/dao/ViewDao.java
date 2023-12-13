package com.rsupport.demo.dao;

import com.rsupport.demo.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViewDao extends JpaRepository<View, UUID> {
}
