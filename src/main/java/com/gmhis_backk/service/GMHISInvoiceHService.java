package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.invoiceH.GMHISInvoiceH;
import com.gmhis_backk.domain.invoiceH.GMHISInvoiceHCreate;
import com.gmhis_backk.domain.invoiceH.GMHISInvoiceHPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.GMHISInvoiceHRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
@Log4j2
public class GMHISInvoiceHService {

    private final PatientService patientService;

    private final GMHISInvoiceHRepository invoiceHRepository;

    private  final GMHISInvoiceHItemService invoiceHItemService;
    private  final InsuranceService insuranceService;

    private final UserService userService;

    public GMHISInvoiceHService(
            final PatientService patientService,
            final UserService userService,
            final GMHISInvoiceHRepository invoiceHRepository,
            final GMHISInvoiceHItemService invoiceHItemService,
            final InsuranceService insuranceService
    ) {
        this.patientService = patientService;
        this.userService = userService;
        this.invoiceHRepository = invoiceHRepository;
        this.invoiceHItemService = invoiceHItemService;
        this.insuranceService = insuranceService;
    }

    protected String generateinvoiceHNumber() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        //Facture Hospi
        return "GMHIS-IVH-" + n;
    }
    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }


    public GMHISInvoiceHPartial toPartial(GMHISInvoiceH invoiceH) {
        GMHISInvoiceHPartial invoiceHPartial = new GMHISInvoiceHPartial();
        invoiceHPartial.setId(invoiceH.getId());
        invoiceHPartial.setCode(invoiceH.getCode());
        invoiceHPartial.setInvoiceNumber(invoiceH.getInvoiceNumber());
        invoiceHPartial.setPatientName(new GMHISName(invoiceH.getPatient().getFirstName(), invoiceH.getPatient().getLastName()));
        invoiceHPartial.setPatientID(invoiceH.getPatient().getId());
        invoiceHPartial.setStatus(invoiceH.getStatus());
        invoiceHPartial.setTotalAmount(invoiceH.getTotalAmount());
        invoiceHPartial.setInsuranceID(invoiceH.getInsuranceId());
        invoiceHPartial.setInsuranceName(invoiceH.getInsuranceName());
        invoiceHPartial.setModeratorTicket(invoiceH.getModeratorTicket());
        invoiceHPartial.setDateOp(invoiceH.getCreatedAt());
        invoiceHPartial.setAffection(invoiceH.getAffection());
        invoiceHPartial.setIndication(invoiceH.getIndication());
        invoiceHPartial.setCmuPart(invoiceH.getCmuPart());
        invoiceHPartial.setInsurancePart(invoiceH.getInsurancePart());
        return   invoiceHPartial;
    }


    protected List<GMHISInvoiceHPartial> map(List<GMHISInvoiceH> invoiceHList) {
        List<GMHISInvoiceHPartial> invoiceHPartialList = new ArrayList<>();

        invoiceHList.forEach(invoiceH -> {
            invoiceHPartialList.add(toPartial(invoiceH));
        });

        return invoiceHPartialList;
    }

    public GMHISInvoiceHPartial create(GMHISInvoiceHCreate invoiceCreate) throws ResourceNotFoundByIdException {
        GMHISInvoiceH invoiceH = new GMHISInvoiceH();

        log.info("totalAmount {}", invoiceCreate.getTotalAmount());
        Patient patient = patientService.findById(invoiceCreate.getPatientID());
        if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");
        invoiceH.setPatient(patient);

        if (invoiceCreate.getInsuranceID() != null){
            Insurance insurance = insuranceService.findInsuranceById(invoiceCreate.getInsuranceID()).orElse(null);
            if (insurance != null) {
                log.info("insuranceName {}", insurance.getName());

                invoiceH.setInsuranceId(insurance.getId());
                invoiceH.setInsuranceName(insurance.getName());
            } ;
        }

        invoiceH.setStatus("Impayé");
        invoiceH.setCode(generateinvoiceHNumber());
        invoiceH.setInvoiceNumber(generateinvoiceHNumber());
        invoiceH.setModeratorTicket(invoiceCreate.getModeratorTicket());
        invoiceH.setTotalAmount(invoiceCreate.getTotalAmount());
        invoiceH.setAffection(invoiceCreate.getAffection());
        invoiceH.setIndication(invoiceCreate.getIndication());
        invoiceH.setCmuPart(invoiceCreate.getCmuPart());
        invoiceH.setInsurancePart(invoiceCreate.getInsurancePart());
        invoiceH.setCreatedAt(new Date());
        invoiceH.setCreatedBy(getCurrentUser().getId());
        GMHISInvoiceH invoiceHSaved = invoiceHRepository.save(invoiceH);

  invoiceCreate.getInvoiceHItems().forEach(item -> {
      log.info("quantity {}", item.getQuantity());

      invoiceHItemService.create(item, invoiceHSaved.getId());
  });

        return  toPartial(invoiceHSaved);
    }

    public GMHISInvoiceHPartial retrieve(UUID invoiceID) throws ResourceNotFoundByIdException{
        GMHISInvoiceH invoiceH = invoiceHRepository.findById(invoiceID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" la facture est inexistante"));
        return toPartial(invoiceH);
    }

    public GMHISInvoiceHPartial update (UUID invoiceID, GMHISInvoiceHCreate invoiceCreate) throws ResourceNotFoundByIdException {
        GMHISInvoiceH invoiceUpdate = invoiceHRepository.findById(invoiceID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" la devis est inexistante"));

        return toPartial(invoiceUpdate);
    }

    public ResponseEntity<Map<String, Object>> search(Map<String, ?> devisSearch) {

        Map<String, Object> searchResult = new HashMap<>();

        int page = (int) devisSearch.get("page");
        String[] sort = (String[]) devisSearch.get("sort");
        int size = (int) devisSearch.get("size");

        Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        Page<GMHISInvoiceH> invoiceHPage = null;

        invoiceHPage = invoiceHRepository.findAll(pageable);

        List<GMHISInvoiceH> invoiceList = invoiceHPage.getContent();

        List<GMHISInvoiceHPartial> invoice = this.map(invoiceList);

        searchResult.put("items", invoice);
        searchResult.put("totalElements", invoiceHPage.getTotalElements());
        searchResult.put("totalPages", invoiceHPage.getTotalPages());
        searchResult.put("size", invoiceHPage.getSize());
        searchResult.put("pageNumber", invoiceHPage.getNumber());
        searchResult.put("numberOfElements", invoiceHPage.getNumberOfElements());
        searchResult.put("first", invoiceHPage.isFirst());
        searchResult.put("last", invoiceHPage.isLast());
        searchResult.put("empty", invoiceHPage.isEmpty());

        return new ResponseEntity<>(searchResult, HttpStatus.OK);

    }

    }
