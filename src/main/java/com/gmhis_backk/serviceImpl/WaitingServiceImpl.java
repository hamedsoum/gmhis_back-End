package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.WaitingRoom;
import com.gmhis_backk.dto.WaitingRoomDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.repository.WaitingRoomRepository;
import com.gmhis_backk.service.WaitingRoomService;

@Service
public class WaitingServiceImpl implements WaitingRoomService {
	
	@Autowired
	private WaitingRoomRepository waitingRoomRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public WaitingRoom findWaitingRoomByName(String waitingRoom) {
		return waitingRoomRepository.findByName(waitingRoom);
	}

	@Override
	public Optional<WaitingRoom> findWaitingRoomById(Long id) {
		return waitingRoomRepository.findById(id);
	}

	@Override
	public List<WaitingRoom> findWaitingRooms() {
		return waitingRoomRepository.findAll();
	}

	@Override
	public Page<WaitingRoom> findWaitingRooms(Pageable pageable) {
		return waitingRoomRepository.findAll(pageable);
	}

	@Override
	public Page<WaitingRoom> findWaitingRoomsContaining(String name, Pageable pageable) {
		return waitingRoomRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<WaitingRoom> findActiveWaitingRooms() {
		return waitingRoomRepository.findActiveWaitingRooms();
	}

	@Override
	public Page<WaitingRoom> findByActive(String namme, Boolean active, Pageable pageable) {
		return waitingRoomRepository.findByActive(namme, active, pageable);
	}

	@Override
	public Long findWaitingRoomByPractician(Long practician_id) {
		return waitingRoomRepository.findWaitingRoomByPractician(practician_id);
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional
	public WaitingRoom saveWaitingRoom(WaitingRoomDTO wrDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		WaitingRoom wrByName = waitingRoomRepository.findByName(wrDto.getName());
		if(wrByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de la salle existe déjà ");  
		} 
		WaitingRoom wr = new WaitingRoom();		
		BeanUtils.copyProperties(wrDto,wr,"id");
		wr.setCreatedAt(new Date());
		wr.setCreatedBy(getCurrentUserId().getId());
		return waitingRoomRepository.save(wr);	
	}

	@Override @Transactional
	public WaitingRoom updateWaitingRoom(Long id,WaitingRoomDTO wrDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		WaitingRoom updateWr = waitingRoomRepository.findById(id).orElse(null);
		
		if (updateWr == null) {
			 throw new ResourceNotFoundByIdException("Aucune salle trouvée pour l'identifiant");
		} else {
			WaitingRoom wrByName = waitingRoomRepository.findByName(wrDto.getName());
			if(wrByName != null) {
				if(wrByName.getId() != updateWr.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la salle existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(wrDto, updateWr,"id");
		updateWr.setUpdatedAt(new Date());
		updateWr.setUpdatedBy(getCurrentUserId().getId());
		return waitingRoomRepository.save(updateWr);
	}
	


}
