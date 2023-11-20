package com.gmhis_backk.controller;

import com.gmhis_backk.domain.hospitalization.GMHISHospitalizationCreate;
import com.gmhis_backk.domain.hospitalization.GMHISHospitalizationPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.GMHISHospitalizationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/hospitalizations")
public class GMHISHospitalizationController {

    private final GMHISHospitalizationService hospitalizationService;

    GMHISHospitalizationController(final GMHISHospitalizationService hospitalizationService){
        this.hospitalizationService = hospitalizationService;
    }

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
            @RequestParam(required = false) Long service,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {

        Map<String, Object> hospitalizationSearchField = new HashMap<>();

        hospitalizationSearchField.put("service", service);
        hospitalizationSearchField.put("sort", sort);
        hospitalizationSearchField.put("page", page);
        hospitalizationSearchField.put("size", size);

        return hospitalizationService.search(hospitalizationSearchField);
    }
}
