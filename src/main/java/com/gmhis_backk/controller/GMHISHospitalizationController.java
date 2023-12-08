package com.gmhis_backk.controller;

import com.gmhis_backk.domain.hospitalization.GMHISHospitalizationCreate;
import com.gmhis_backk.domain.hospitalization.GMHISHospitalizationPartial;
import com.gmhis_backk.domain.hospitalization.protocole.GMHISProtocolePartial;
import com.gmhis_backk.domain.hospitalization.protocole.service.GMHISProtocoleServiceCreate;
import com.gmhis_backk.domain.hospitalization.protocole.service.GMHISProtocoleServicePartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.GMHISHospitalizationService;
import com.gmhis_backk.service.GMHISProtocoleService;
import com.gmhis_backk.service.GMHISProtocoleserviceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/hospitalizations")
public class GMHISHospitalizationController {

    private final GMHISHospitalizationService hospitalizationService;

    private  final GMHISProtocoleService protocoleService;

    private final GMHISProtocoleserviceService protocoleserviceService;


    GMHISHospitalizationController(final GMHISHospitalizationService hospitalizationService, GMHISProtocoleService protocoleService, GMHISProtocoleserviceService protocoleserviceService){
        this.hospitalizationService = hospitalizationService;
        this.protocoleService = protocoleService;

        this.protocoleserviceService = protocoleserviceService;
    }

    //---------------------------------- Protocole Service --------------------------------------------
    @ApiOperation(value = "find a existing Protocole Service in the system")
    @GetMapping("/{protocoleID}/service")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<List<GMHISProtocoleServicePartial>>  findServices(@PathVariable String protocoleID) {
        List<GMHISProtocoleServicePartial> pServices = protocoleserviceService.findProtocoleServices(UUID.fromString(protocoleID));
        return new ResponseEntity<>(pServices, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new  Protocole service in the system")
    @PostMapping("/{protocoleID}/service")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable UUID protocoleID, @RequestBody GMHISProtocoleServiceCreate protocoleServiceCreate) throws ResourceNotFoundByIdException {
        protocoleserviceService.create(protocoleID, protocoleServiceCreate);
    }


    //---------------------------------- Protocole --------------------------------------------
    @ApiOperation(value = "find a existing Hospitalization Protocoles in the system")
    @GetMapping("/{hospitalizationID}/protocole")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<List<GMHISProtocolePartial>> findProtocoles(@PathVariable String hospitalizationID) {
        List<GMHISProtocolePartial> protocoles = protocoleService.findProtocoles(UUID.fromString(hospitalizationID));
        return new ResponseEntity<>(protocoles, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new Hospitalization Protocole in the system")
    @PostMapping("/{hospitalizationID}/protocole")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProtocole(@PathVariable UUID hospitalizationID, @RequestBody String protocoleDescription) throws ResourceNotFoundByIdException {
        protocoleService.create(hospitalizationID, protocoleDescription);
    }


    //---------------------------------- Hospitalization --------------------------------------------
    @ApiOperation(value = "close existing Hospitalization in the system")
    @PutMapping("/{hospitalizationID}/close")
    public GMHISHospitalizationPartial close(@PathVariable UUID hospitalizationID, @RequestBody GMHISHospitalizationCreate hospitalizationCreate) throws ResourceNotFoundByIdException {
        return hospitalizationService.closeHospitalization(hospitalizationID, hospitalizationCreate);
    }

    @ApiOperation(value = "update existing Hospitalization in the system")
    @PutMapping("/{hospitalizationID}")
    public GMHISHospitalizationPartial update(@PathVariable UUID hospitalizationID, @RequestBody GMHISHospitalizationCreate hospitalizationCreate) throws ResourceNotFoundByIdException {
        return hospitalizationService.update(hospitalizationID, hospitalizationCreate);
    }

    @ApiOperation(value = "allocation of Hospitalization to nurse")
    @PutMapping("/{hospitalizationID}/nurse")
    public ResponseEntity<GMHISHospitalizationPartial>  addNurse(@PathVariable UUID hospitalizationID, @RequestBody Long nurseID) throws ResourceNotFoundByIdException {
        GMHISHospitalizationPartial hospitalizationPartial =  hospitalizationService.addNurse(hospitalizationID, nurseID);
        return new ResponseEntity<>(hospitalizationPartial, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new Hospitalization in the system")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GMHISHospitalizationPartial create( @RequestBody GMHISHospitalizationCreate hospitalizationCreate) throws ResourceNotFoundByIdException {

        return hospitalizationService.create(hospitalizationCreate);
    }

    @ApiOperation(value = "Retrieve existing Hospitalization  in the system")
    @GetMapping("/{hospitalizationID}")
    public GMHISHospitalizationPartial retrieve(@PathVariable UUID hospitalizationID) throws ResourceNotFoundByIdException {
        return hospitalizationService.retrieve(hospitalizationID);
    }

    @ApiOperation(value = "find All Hospitalization in the system")
    @GetMapping()
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(required = false) Long patientID,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {

        Map<String, Object> hospitalizationSearchField = new HashMap<>();

        hospitalizationSearchField.put("patientID", patientID);
        hospitalizationSearchField.put("sort", sort);
        hospitalizationSearchField.put("page", page);
        hospitalizationSearchField.put("size", size);

        return hospitalizationService.search(hospitalizationSearchField);
    }
}
