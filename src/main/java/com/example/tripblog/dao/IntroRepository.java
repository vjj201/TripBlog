package com.example.tripblog.dao;

import com.example.tripblog.entity.Intro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sandy
 * @date
 */

@Repository
public interface IntroRepository extends JpaRepository<Intro, Long> {

}
