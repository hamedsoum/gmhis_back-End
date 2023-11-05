package com.gmhis_backk.controller;

import com.gmhis_backk.domain.cautionTransaction.GMHISCautionTransactionCreate;
import com.gmhis_backk.domain.cautionTransaction.GMHISCautionTransactionPartial;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.GMHISCautionTransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/caution-transaction")
public class GMHISCautionTransactionController {

    private final GMHISCautionTransactionService cautionTransactionService;

    public GMHISCautionTransactionController(GMHISCautionTransactionService cautionTransactionService) {
        this.cautionTransactionService = cautionTransactionService;
    }

    @ApiOperation(value = "Create Caution Transaction")
    @PostMapping()
    public GMHISCautionTransactionPartial create(@RequestBody GMHISCautionTransactionCreate cautionTransactionCreate) throws Exception {
        return cautionTransactionService.create(cautionTransactionCreate);
    }

    @ApiOperation(value = "Find All patient existing caution Transaction in the system")
    @GetMapping("/{cautionTransactionID}")
    public List<GMHISCautionTransactionPartial> cautionTransaction(@PathVariable Long cautionTransactionID) {
        return cautionTransactionService.cautionTransactions(cautionTransactionID);
    }


}
