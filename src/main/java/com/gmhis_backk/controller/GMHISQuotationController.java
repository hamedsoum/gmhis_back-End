package com.gmhis_backk.controller;

import com.gmhis_backk.domain.GMHISHospitalizationRequestCreate;
import com.gmhis_backk.domain.GMHISHospitalizationRequestPartial;
import com.gmhis_backk.domain.quotation.GMHISQuotationCreate;
import com.gmhis_backk.domain.quotation.GMHISQuotationPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.GMHISQuotationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/quotation")
public class GMHISQuotationController {

    private  final GMHISQuotationService quotationService;

    GMHISQuotationController(final GMHISQuotationService quotationService){
        this.quotationService = quotationService;
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

}
