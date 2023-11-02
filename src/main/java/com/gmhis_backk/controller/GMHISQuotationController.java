package com.gmhis_backk.controller;

import com.gmhis_backk.domain.quotation.GMHISQuotationCreate;
import com.gmhis_backk.domain.quotation.GMHISQuotationPartial;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.GMHISQuotationItemService;
import com.gmhis_backk.service.GMHISQuotationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/quotations")
public class GMHISQuotationController {

    private  final GMHISQuotationService quotationService;

    private  final GMHISQuotationItemService quotationItemService;


    GMHISQuotationController(
            final GMHISQuotationService quotationService,
            final GMHISQuotationItemService quotationItemService
                             ){
        this.quotationItemService = quotationItemService;
        this.quotationService = quotationService;
    }

    @ApiOperation(value = "update existing quotation in the system")
    @PutMapping("/{quotationID}/status")
    public String updateStatus(@PathVariable UUID quotationID, @RequestBody String status) throws ResourceNotFoundByIdException {
        return quotationService.updateQuotationStatus(quotationID, status);
    }

    @ApiOperation(value = "update existing quotation in the system")
    @PutMapping("/{quotationID}")
    public GMHISQuotationPartial update(@PathVariable UUID quotationID, @RequestBody GMHISQuotationCreate quotationCreate) throws ResourceNotFoundByIdException {
        return quotationService.update(quotationID, quotationCreate);
    }

    @ApiOperation(value = "Create a new quotation in the system")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GMHISQuotationPartial create( @RequestBody GMHISQuotationCreate quotationCreate) throws ResourceNotFoundByIdException {

        return quotationService.create(quotationCreate);
    }

    @ApiOperation(value = "Retrieve existing Quotation Request in the system")
    @GetMapping("/{quotationID}")
    public GMHISQuotationPartial retrieve(@PathVariable UUID quotationID) throws ResourceNotFoundByIdException {
        return quotationService.retrieve(quotationID);
    }

    @ApiOperation(value = "find All Quotations in the system")
    @GetMapping()
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(required = false) Long service,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {

        Map<String, Object> searchField = new HashMap<>();

        searchField.put("service", service);
        searchField.put("sort", sort);
        searchField.put("page", page);
        searchField.put("size", size);

        return quotationService.search(searchField);
    }

    @ApiOperation(value = "find All QuotationItems By QuotationID in the system")
    @GetMapping("/find/{quotationID}")
    public List<GMHISQuotationItemPartial> findByCotationID(@PathVariable UUID quotationID) {
        return quotationItemService.findByQuotationID(quotationID);
    }

}
