package com.gmhis_backk.controller;

import com.gmhis_backk.domain.GMHISHospitalizationRequestCreate;
import com.gmhis_backk.domain.GMHISHospitalizationRequestPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.GMHISHospitalizationRequestService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/hospitalization-request")
public class GMHISHospitalizationRequestController {

    private final GMHISHospitalizationRequestService hospitalizationRequestService;

    GMHISHospitalizationRequestController(final GMHISHospitalizationRequestService hospitalizationRequestService){
        this.hospitalizationRequestService = hospitalizationRequestService;
    }

    @ApiOperation(value = "update existing Hospitalization request in the system")
    @PutMapping("/{hospitalizationRequestID}")
    public GMHISHospitalizationRequestPartial update(@PathVariable UUID hospitalizationRequestID, @RequestBody GMHISHospitalizationRequestCreate hospitalizationRequestCreate) throws ResourceNotFoundByIdException {
        return hospitalizationRequestService.update(hospitalizationRequestID, hospitalizationRequestCreate);
    }

    @ApiOperation(value = "Create a new Hospitalization Request in the system")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GMHISHospitalizationRequestPartial create( @RequestBody GMHISHospitalizationRequestCreate hospitalizationRequestCreate) throws ResourceNotFoundByIdException {

        return hospitalizationRequestService.create(hospitalizationRequestCreate);
    }

    @ApiOperation(value = "Retrieve existing Hospitalization Request in the system")
    @GetMapping("/{hospitalizationRequestID}")
    public GMHISHospitalizationRequestPartial retrieve(@PathVariable UUID hospitalizationRequestID) throws ResourceNotFoundByIdException {
        return hospitalizationRequestService.retrieve(hospitalizationRequestID);
    }

    @ApiOperation(value = "find All Hospitalization Request in the system")
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

        return hospitalizationRequestService.search(hospitalizationSearchField);
    }
}
