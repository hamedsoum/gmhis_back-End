package com.gmhis_backk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Image;


@Repository 
@Transactional
public interface ImageDbRepository extends JpaRepository<Image, Long> {
	
}
