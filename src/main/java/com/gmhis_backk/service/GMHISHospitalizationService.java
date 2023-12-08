package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.constant.GMHISHospitalizationStatus;
import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.hospitalization.GMHISHospitalization;
import com.gmhis_backk.domain.hospitalization.GMHISHospitalizationCreate;
import com.gmhis_backk.domain.hospitalization.GMHISHospitalizationPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.GMHISHospitalizationRepository;
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
public class GMHISHospitalizationService {

    private final PatientService patientService;
    private final PracticianService practicianService;

    private final UserService userService;

    private  final GMHISHospitalizationRepository hospitalizationRepository;

    private final GMHISProtocoleService protocoleService;
    GMHISHospitalizationService(
            final PatientService patientService,
            final PracticianService practicianService,
            final UserService userService,
            final GMHISHospitalizationRepository hospitalizationRepository,
            final GMHISProtocoleService protocoleService){
        this.patientService = patientService;
        this.practicianService = practicianService;
        this.userService = userService;
        this.hospitalizationRepository = hospitalizationRepository;
        this.protocoleService = protocoleService;
    }

    protected String generateHospitalizationNumber() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        //Facture Hospi
        return "GMHIS-HOS-" + n;
    }

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }

    public GMHISHospitalizationPartial toPartial(GMHISHospitalization hospitalization) {

        GMHISHospitalizationPartial hospitalizationPartial = new GMHISHospitalizationPartial();
        hospitalizationPartial.setId(hospitalization.getId());
        hospitalizationPartial.setCode(hospitalization.getCode());
        hospitalizationPartial.setPatientName(new GMHISName(hospitalization.getPatient().getFirstName(), hospitalization.getPatient().getLastName()));
        hospitalizationPartial.setPracticianName(new GMHISName(hospitalization.getPractician().getPrenoms(), hospitalization.getPractician().getNom()));
       if(hospitalization.getNurse() != null) hospitalizationPartial.setNurseName(new GMHISName(hospitalization.getNurse().getFirstName(), hospitalization.getNurse().getLastName()));
        hospitalizationPartial.setBedroom(hospitalization.getBedroom());
        hospitalizationPartial.setReason(hospitalization.getReason());
        hospitalizationPartial.setProtocole(hospitalization.getProtocole());
        hospitalizationPartial.setStart(hospitalization.getStart());
        hospitalizationPartial.setEnd(hospitalization.getEnd());
        hospitalizationPartial.setConclusion(hospitalization.getConclusion());
        hospitalizationPartial.setStatus(hospitalization.getStatus());

        return hospitalizationPartial;
    }

    protected List<GMHISHospitalizationPartial> map(List<GMHISHospitalization> hospitalizations) {
        List<GMHISHospitalizationPartial> hospitalizationPartialList = new ArrayList<>();

        hospitalizations.forEach(hospitalization -> {
            hospitalizationPartialList.add(toPartial(hospitalization));
        });

        return hospitalizationPartialList;
    }

    public GMHISHospitalizationPartial create(GMHISHospitalizationCreate hospitalizationCreate) throws ResourceNotFoundByIdException {
        Patient patient = patientService.findById(hospitalizationCreate.getPatientID());
        if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");

        Pratician practician = practicianService.findPracticianById(hospitalizationCreate.getPracticianID())
                .orElseThrow(() -> new ResourceNotFoundByIdException(" Practien inexistant"));

        GMHISHospitalization hospitalization = new GMHISHospitalization();
        hospitalization.setPatient(patient);
        hospitalization.setPractician(practician);
        hospitalization.setCode(generateHospitalizationNumber());
        BeanUtils.copyProperties(hospitalizationCreate,hospitalization,"id");
        hospitalization.setCreatedBy(getCurrentUser().getId());
        hospitalization.setStatus(GMHISHospitalizationStatus.IN_PROGRESS);
        hospitalization.setCreatedAt(new Date());
        GMHISHospitalizationPartial hospitalizationCreated = toPartial(hospitalizationRepository.save(hospitalization));

        protocoleService.create(hospitalizationCreated.getId(), hospitalizationCreated.getProtocole());

        return hospitalizationCreated;
    }

    public GMHISHospitalizationPartial retrieve(UUID hospitalizationID) throws ResourceNotFoundByIdException{
        GMHISHospitalization hospitalization = hospitalizationRepository.findById(hospitalizationID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" l'hospitalisation est  inexistante"));
        return toPartial(hospitalization);
    }

    public GMHISHospitalizationPartial closeHospitalization(UUID hospitalizationID, GMHISHospitalizationCreate hospitalizationCreate) throws ResourceNotFoundByIdException {
        GMHISHospitalization hospitalization = hospitalizationRepository.findById(hospitalizationID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" l'hospitalisation est  inexistante"));
        hospitalization.setEnd(hospitalizationCreate.getEnd());
        hospitalization.setConclusion(hospitalizationCreate.getConclusion());
        hospitalization.setStatus(GMHISHospitalizationStatus.FINISHED);

        return toPartial(hospitalizationRepository.save(hospitalization));
    }

    public GMHISHospitalizationPartial update(UUID hospitalizationID, GMHISHospitalizationCreate hospitalizationCreate) throws ResourceNotFoundByIdException{
        GMHISHospitalization hospitalization = hospitalizationRepository.findById(hospitalizationID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" Hospitalisation inexistante"));

        Patient patient = patientService.findById(hospitalizationCreate.getPatientID());
        if (patient == null) throw new ResourceNotFoundByIdException("Patient inexistant");

        Pratician practician = practicianService.findPracticianById(1L)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" Practien inexistant"));

        User nurse = userService.findUserById(hospitalizationCreate.getNurse());
        if (nurse == null) throw new ResourceNotFoundByIdException("Infirmier(e) inexistant");

        hospitalization.setPatient(patient);
        hospitalization.setPractician(practician);
        hospitalization.setNurse(nurse);
        BeanUtils.copyProperties(hospitalizationCreate,hospitalization,"id");
        hospitalization.setUpdatedBy(getCurrentUser().getId());
        hospitalization.setUpdatededAt(new Date());

        return toPartial(hospitalizationRepository.save(hospitalization));
    }


    public GMHISHospitalizationPartial addNurse(UUID hospitalizationID, Long nurseID) throws ResourceNotFoundByIdException{
        GMHISHospitalization hospitalization = hospitalizationRepository.findById(hospitalizationID)
                .orElseThrow(() -> new ResourceNotFoundByIdException(" Hospitalisation inexistante"));

        User nurse = userService.findUserById(nurseID);
        if (nurse == null) throw new ResourceNotFoundByIdException("Infirmier(e) inexistant");

        hospitalization.setNurse(nurse);
        hospitalization.setUpdatedBy(getCurrentUser().getId());
        hospitalization.setUpdatededAt(new Date());

        return toPartial(hospitalizationRepository.save(hospitalization));
    }

    public ResponseEntity<Map<String, Object>> search(Map<String, ?> hospitalizationSearch) {

        Map<String, Object> searchResult = new HashMap<>();

        int page = (int) hospitalizationSearch.get("page");
        String[] sort = (String[]) hospitalizationSearch.get("sort");
        int size = (int) hospitalizationSearch.get("size");


        Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));

        Page<GMHISHospitalization> hospitalizationPage = null;

        hospitalizationPage = hospitalizationRepository.findAll(pageable);

        if(hospitalizationSearch.get("patientID") != null) {
            long patientID = (long) hospitalizationSearch.get("patientID");
            hospitalizationPage = hospitalizationRepository.findHospitalizationBy(patientID, pageable);

        }

        List<GMHISHospitalization> hospitalizationList = hospitalizationPage.getContent();

        List<GMHISHospitalizationPartial> hospitalizations = this.map(hospitalizationList);

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
