package com.gmhis_backk.controller;

import com.gmhis_backk.domain.invoiceH.GMHISInvoiceHCreate;
import com.gmhis_backk.domain.invoiceH.GMHISInvoiceHPartial;
import com.gmhis_backk.domain.invoiceH.item.GMHISInvoiceHItemPartial;
import com.gmhis_backk.domain.quotation.GMHISQuotationCreate;
import com.gmhis_backk.domain.quotation.GMHISQuotationPartial;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.GMHISInvoiceHItemService;
import com.gmhis_backk.service.GMHISInvoiceHService;
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
@RequestMapping("/invoice-h")
public class GMHISInvoiceHController {

    private  final GMHISInvoiceHService invoiceService;

    private  final GMHISInvoiceHItemService invoiceHItemService;


    GMHISInvoiceHController(
            final GMHISInvoiceHService invoiceService,
            final GMHISInvoiceHItemService invoiceHItemService
                             ){
        this.invoiceHItemService = invoiceHItemService;
        this.invoiceService = invoiceService;
    }


    @ApiOperation(value = "update existing Hospitalization Invoice in the system")
    @PutMapping("/{invoiceHID}")
    public GMHISInvoiceHPartial update(@PathVariable UUID invoiceHID, @RequestBody GMHISInvoiceHCreate invoiceCreate) throws ResourceNotFoundByIdException {
        return invoiceService.update(invoiceHID, invoiceCreate);
    }

    @ApiOperation(value = "Create a new Hospitalization Invoice in the system")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GMHISInvoiceHPartial create( @RequestBody GMHISInvoiceHCreate invoiceCreate) throws ResourceNotFoundByIdException {

        return invoiceService.create(invoiceCreate);
    }

    @ApiOperation(value = "Retrieve existing Hospitalization Invoice Request in the system")
    @GetMapping("/{invoiceHID}")
    public GMHISInvoiceHPartial retrieve(@PathVariable UUID invoiceHID) throws ResourceNotFoundByIdException {
        return invoiceService.retrieve(invoiceHID);
    }

    @ApiOperation(value = "find All Hospitalization Invoice in the system")
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

        return invoiceService.search(searchField);
    }

    @ApiOperation(value = "find All Hospitalization InvoiceHItems By invoiceHID in the system")
    @GetMapping("/{invoiceHID}/find")
    public List<GMHISInvoiceHItemPartial> findByInvoiceHID(@PathVariable UUID invoiceHID) {
        return invoiceHItemService.findByQuotationID(invoiceHID);
    }

}
