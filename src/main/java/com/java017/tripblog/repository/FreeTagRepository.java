package com.java017.tripblog.repository;

import com.java017.tripblog.entity.FreeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTagRepository extends JpaRepository<FreeTag, Long>  {
}