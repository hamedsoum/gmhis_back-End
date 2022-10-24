package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.WaitingRoom;
import com.gmhis_backk.dto.WaitingRoomDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface WaitingRoomService {
	public WaitingRoom saveWaitingRoom(WaitingRoomDTO wrDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public WaitingRoom updateWaitingRoom(Long id,WaitingRoomDTO wrDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;

	public WaitingRoom findWaitingRoomByName(String waitingRoom);
	
	public Optional<WaitingRoom> findWaitingRoomById(Long id);

	public List<WaitingRoom> findWaitingRooms();
	
	public Page<WaitingRoom> findWaitingRooms(Pageable pageable);
	
	public Page<WaitingRoom> findWaitingRoomsContaining(String name,Pageable pageable);
	
	public List<WaitingRoom> findActiveWaitingRooms();
	
	public Page<WaitingRoom> findByActive(String namme, Boolean active, Pageable pageable);
	
	public Long findWaitingRoomByPractician(Long practician_id);

}
