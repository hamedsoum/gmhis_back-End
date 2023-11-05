package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.*;
import com.gmhis_backk.domain.hospitalization.request.GMHISHospitalizationRequest;
import com.gmhis_backk.domain.hospitalization.request.GMHISHospitalizationRequestCreate;
import com.gmhis_backk.domain.hospitalization.request.GMHISHospitalizationRequestPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.GMHISHospitalizationRequestRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
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
public class GMHISHospitalizationRequestService {

    private final PatientService patientService;
    private final PracticianService practicianService;

    private final ExaminationService examinationService;

    private final GMHISHospitalizationRequestRepository hospitalizationRequestRepository;

    private final InsuredService insuredService;

    private final UserService userService;
    public GMHISHospitalizationRequestService(
            final PatientService patientService,
            final PracticianService practicianService,
            final UserService userService,
            final GMHISHospitalizationRequestRepository hospitalizationRequestRepository,
            final ExaminationService examinationService,
            final InsuredService insuredService) {
        this.patientService = patientService;
        this.practicianService = practicianService;
        this.userService = userService;
        this.hospitalizationRequestRepository = hospitalizationRequestRepository;
        this.examinationService = examinationService;
        this.insuredService = insuredService;
    }

    protected String generateHospitalizationRequestNumber() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        //Facture Hospi
        return "GMHIS-HOR-" + n;
    }

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }


    public GMHISHospitalizationRequestPartial toPartial(GMHISHospitalizationRequest hospitalizationRequest) {
        GMHISHospitalizationRequestPartial hospitalizationRequestPartial = new GMHISHospitalizationRequestPartial();
        hospitalizationRequestPartial.setId( hospitalizationRequest.getId());
        hospitalizationRequestPartial.setCode(hospitalizationRequest.getCode());
        hospitalizationRequestPartial.setPatientID(hospitalizationRequest.getPatient().getId());
        hospitalizationRequestPartial.setPatientName(new GMHISName(hospitalizationRequest.getPatient().getFirstName(), hospitalizationRequest.getPatient().getLastName()));
        hospitalizationRequestPartial.setPraticianName(new GMHISName(hospitalizationRequest.getPractician().getPrenoms(), hospitalizationRequest.getPractician().getNom()));
        hospitalizationRequestPartial.setPraticianID(hospitalizationRequest.getPractician().getId());
        hospitalizationRequestPartial.setReason(hospitalizationRequest.getReason());
        hospitalizationRequestPartial.setProtocole(hospitalizationRequest.getProtocole());
        hospitalizationRequestPartial.setDayNumber(hospitalizationRequest.getDayNumber());
        hospitalizationRequestPartial.setDate(hospitalizationRequest.getCreatedAt());
        hospitalizationRequestPartial.setExamination(hospitalizationRequest.getExamination());
        hospitalizationRequestPartial.setAdmissionID(hospitalizationRequest.getAdmission_id());
        hospitalizationRequestPartial.setStartDate(hospitalizationRequest.getStartDate());
        hospitalizationRequestPartial.setInsuranceName(hospitalizationRequest.getInsured().getInsurance().getName());
        hospitalizationRequestPartial.setInsuranceID(hospitalizationRequest.getInsured().getInsurance().getId());
        hospitalizationRequestPartial.setInsuranceMatricule(hospitalizationRequest.getInsured().getCardNumber());
        return hospitalizationRequestPartial;
    }

    protected List<GMHISHospitalizationRequestPartial> map(List<GMHISHospitalizationRequest> hospitalizationRequests) {
        List<GMHISHospitalizationRequestPartial> hospitalizationRequestPartialList = new ArrayList<>();

        hospitalizationRequests.forEach(hospitalizationRequest -> {
            hospitalizationRequestPartialList.add(toPartial(hospitalizationRequest));
        });

        return hospitalizationRequestPartialList;
    }

    public GMHISHospitalizationRequestPartial create(GMHISHospitalizationRequestCreate hospitalizationRequestCreate) throws ResourceNotFoundByIdException {
        Patient patient = patientService.findById(hospitalizationRequestCreate.getPatientID());
        if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");

        Pratician practician = practicianService.findPracticianById(6L)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" Practien inexistant"));

        GMHISHospitalizationRequest hospitalizationRequest = new GMHISHospitalizationRequest();
        hospitalizationRequest.setPatient(patient);
        hospitalizationRequest.setAdmission_id(hospitalizationRequestCreate.getAdmissionID());
        hospitalizationRequest.setPractician(practician);
        examinationService.findExaminationById(hospitalizationRequestCreate.getExaminationID()).ifPresent(hospitalizationRequest::setExamination);
        insuredService.findInsuredById(hospitalizationRequestCreate.getInsuredID()).ifPresent(hospitalizationRequest::setInsured);
        hospitalizationRequest.setCode(generateHospitalizationRequestNumber());
        hospitalizationRequest.setStartDate(hospitalizationRequestCreate.getStartDate());
        BeanUtils.copyProperties(hospitalizationRequestCreate,hospitalizationRequest,"id");
        hospitalizationRequest.setCreatedBy(getCurrentUser().getId());
        hospitalizationRequest.setCreatedAt(new Date());
        return toPartial(hospitalizationRequestRepository.save(hospitalizationRequest));
    }

    public GMHISHospitalizationRequestPartial retrieve(UUID hospitalizationRequestID) throws ResourceNotFoundByIdException{
        GMHISHospitalizationRequest hospitalizationRequest = hospitalizationRequestRepository.findById(hospitalizationRequestID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" la demande d'hospitalisation est  inexistante"));
        return toPartial(hospitalizationRequest);
    }

    public GMHISHospitalizationRequestPartial update(UUID hospitalizationRequestID, GMHISHospitalizationRequestCreate hospitalizationRequestCreate) throws ResourceNotFoundByIdException{
        GMHISHospitalizationRequest hospitalizationRequest = hospitalizationRequestRepository.findById(hospitalizationRequestID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" la demande d'hospitalisation est  inexistante"));

        Patient patient = patientService.findById(hospitalizationRequestCreate.getPatientID());
        if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");

        Pratician practician = practicianService.findPracticianById(1L)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" Practien inexistant"));

        hospitalizationRequest.setPatient(patient);
        hospitalizationRequest.setPractician(practician);
        BeanUtils.copyProperties(hospitalizationRequestCreate,hospitalizationRequest,"id");
        hospitalizationRequest.setUpdatedBy(getCurrentUser().getId());
        hospitalizationRequest.setUpdatededAt(new Date());

        return toPartial(hospitalizationRequestRepository.save(hospitalizationRequest));
    }

    public ResponseEntity<Map<String, Object>> search(Map<String, ?> hospitalizationSearch) {

        Map<String, Object> searchResult = new HashMap<>();

        int page = (int) hospitalizationSearch.get("page");
        String[] sort = (String[]) hospitalizationSearch.get("sort");
        int size = (int) hospitalizationSearch.get("size");

        Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        Page<GMHISHospitalizationRequest> hospitalizationPage = null;

        hospitalizationPage = hospitalizationRequestRepository.findAll(pageable);

        List<GMHISHospitalizationRequest> hospitalizationList = hospitalizationPage.getContent();

        List<GMHISHospitalizationRequestPartial> hospitalizations = this.map(hospitalizationList);

        searchResult.put("items", hospitalizations);
        searchResult.put("totalElements", hospitalizationPage.getTotalElements());
        searchResult.put("totalPages", hospitalizationPage.getTotalPages());
        searchResult.put("size", hospitalizationPage.getSize());
        searchResult.put("pageNumber", hospitalizationPage.getNumber());
        searchResult.put("numberOfElements", hospitalizationPage.getNumberOfElements());
        searchResult.put("first", hospitalizationPage.isFirst());
        searchResult.put("last", hospitalizationPage.isLast());
        searchResult.put("empty", hospitalizationPage.isEmpty());

        return new ResponseEntity<>(searchResult, HttpStatus.OK);

    }

    }
