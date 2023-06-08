package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.WaitingRoom;

public interface WaitingRoomRepository extends JpaRepository<WaitingRoom, Long> {
	public WaitingRoom findByName(String name);

	@Query(value = "select w from WaitingRoom w where active = 1")
	public List<WaitingRoom> findActiveWaitingRooms();

	public Page<WaitingRoom> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select w from WaitingRoom w where w.name like %:name% and w.active = :active")
	public Page<WaitingRoom> findByActive(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);

	@Query(value="select r.id from pratician p, service s, waiting_room r where p.speciality_id = s.id and s.waiting_room_id = r.id and p.user_id = :practician_id", nativeQuery = true)
	public Long findWaitingRoomByPractician(@Param("practician_id") Long practician_id);
}
