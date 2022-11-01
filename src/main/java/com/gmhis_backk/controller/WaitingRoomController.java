package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.WaitingRoom;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.WaitingRoomDTO;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.WaitingRoomService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/waiting_room")
public class WaitingRoomController {

	@Autowired
	WaitingRoomService waitingRoomService;
	
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de tous les codes d'acte dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllActCategory(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<WaitingRoom> pageWaitingRoom;
		
		pageWaitingRoom = waitingRoomService.findWaitingRooms(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageWaitingRoom = waitingRoomService.findByActive(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageWaitingRoom = waitingRoomService.findWaitingRoomsContaining(name.trim(), paging);
		}else {
			pageWaitingRoom = waitingRoomService.findWaitingRooms(paging);
		}
		
		List<WaitingRoom> waitingRoomList = pageWaitingRoom.getContent();

		
		List<Map<String, Object>> actCodes = this.getMapFromWaitingRoomList(waitingRoomList);

		response.put("items", actCodes);
		response.put("currentPage", pageWaitingRoom.getNumber());
		response.put("totalItems", pageWaitingRoom.getTotalElements());
		response.put("totalPages", pageWaitingRoom.getTotalPages());
		response.put("size", pageWaitingRoom.getSize());
		response.put("first", pageWaitingRoom.isFirst());
		response.put("last", pageWaitingRoom.isLast());
		response.put("empty", pageWaitingRoom.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	
	protected List<Map<String, Object>> getMapFromWaitingRoomList(List<WaitingRoom> waitingRooms) {
		List<Map<String, Object>> waitingRoomList = new ArrayList<>();
		waitingRooms.stream().forEach(waitingRoomDto -> {

			Map<String, Object> actGroupsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(waitingRoomDto.getCreatedBy()) ? new User()
					: userRepository.findById(waitingRoomDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(waitingRoomDto.getUpdatedBy()) ? new User()
					: userRepository.findById(waitingRoomDto.getUpdatedBy()).orElse(null);
			actGroupsMap.put("id", waitingRoomDto.getId());
			actGroupsMap.put("name", waitingRoomDto.getName());
			actGroupsMap.put("active", waitingRoomDto.getActive());
			actGroupsMap.put("capacity", waitingRoomDto.getCapacity());
			actGroupsMap.put("createdAt", waitingRoomDto.getCreatedAt());
			actGroupsMap.put("updatedAt", waitingRoomDto.getUpdatedAt());
			actGroupsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			actGroupsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			actGroupsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			actGroupsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			waitingRoomList.add(actGroupsMap);
		});
		return waitingRoomList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une salle")
	public ResponseEntity<WaitingRoom> addWaitingRoom(@RequestBody WaitingRoomDTO actCodeDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		WaitingRoom waitingRoom = waitingRoomService.saveWaitingRoom(actCodeDto);
		return new ResponseEntity<WaitingRoom>(waitingRoom,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une salle dans le systeme")
	public ResponseEntity<WaitingRoom>updateWaiting(@PathVariable("id") Long id,@RequestBody WaitingRoomDTO actCodeDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		WaitingRoom updateWaitingRoom = waitingRoomService.updateWaitingRoom(id, actCodeDto);
		return new ResponseEntity<>(updateWaitingRoom,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un code d'acte ")
	public  ResponseEntity<Optional<WaitingRoom>> getDetail(@PathVariable Long id){
		Optional<WaitingRoom> waitingRoom = waitingRoomService.findWaitingRoomById(id);
		return new ResponseEntity<>(waitingRoom,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des salles d'attentes actives dans le système")
	@GetMapping("/active_waiting_room_name")
	public ResponseEntity<List<Map<String, Object>>>  activeActCategoryName() {
		List<Map<String, Object>>  waitingRoomList = new ArrayList<>();

		waitingRoomService.findActiveWaitingRooms().forEach(actCodeDto -> {
			Map<String, Object> waitingRoomMap = new HashMap<>();
			waitingRoomMap.put("id", actCodeDto.getId());
			waitingRoomMap.put("name", actCodeDto.getName());
			waitingRoomList.add(waitingRoomMap);
		});
		
		return new ResponseEntity<>(waitingRoomList, HttpStatus.OK);
	}
	
}
