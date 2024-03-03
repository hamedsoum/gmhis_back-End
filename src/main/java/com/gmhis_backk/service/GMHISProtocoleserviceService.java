package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.hospitalization.protocole.GMHISProtocole;
import com.gmhis_backk.domain.hospitalization.protocole.service.GMHISProtocoleService;
import com.gmhis_backk.domain.hospitalization.protocole.service.GMHISProtocoleServiceCreate;
import com.gmhis_backk.domain.hospitalization.protocole.service.GMHISProtocoleServicePartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.GMHISProtocoleRepository;
import com.gmhis_backk.repository.GMHISProtocoleServiceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Log4j2
public class GMHISProtocoleserviceService {

    private final GMHISProtocoleServiceRepository protocoleServiceRepository;

    private  final GMHISProtocoleRepository protocoleRepository;

    private final UserService userService;

    public GMHISProtocoleserviceService(GMHISProtocoleServiceRepository protocoleServiceRepository, GMHISProtocoleRepository protocoleRepository, UserService userService) {
        this.protocoleServiceRepository = protocoleServiceRepository;
        this.protocoleRepository = protocoleRepository;
        this.userService = userService;
    }

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }


    GMHISProtocoleServicePartial toPartial(GMHISProtocoleService service) {
        GMHISProtocoleServicePartial protocoleServicePartial = new GMHISProtocoleServicePartial();
        protocoleServicePartial.setDetail(service.getDetail());
        protocoleServicePartial.setServiceDate(service.getServiceDate());
        protocoleServicePartial.setId(service.getId());
        User nurse = this.userService.findUserById(service.getCreatedBy());
        protocoleServicePartial.setNurse(new GMHISName(nurse.getFirstName(), nurse.getLastName()));
        return protocoleServicePartial;
    }

    public void create(UUID protocoleID, GMHISProtocoleServiceCreate protocoleServiceCreate) throws ResourceNotFoundByIdException {
        GMHISProtocole protocole = protocoleRepository.findById(protocoleID).orElse(null);
        GMHISProtocoleService pService = new GMHISProtocoleService();

        log.info("here");

        if(protocole != null ) {
            pService.setProtocole(protocole);
        }
        pService.setServiceDate(protocoleServiceCreate.getServiceDate());
        pService.setDetail(protocoleServiceCreate.getDetail());
        pService.setCreatedBy(getCurrentUser().getId());
        pService.setCreatedAt(new Date());
        protocoleServiceRepository.save(pService);
    }

    public List<GMHISProtocoleServicePartial> findProtocoleServices(UUID protocoleID) {
        List<GMHISProtocoleServicePartial> pServices = new ArrayList<>();
        protocoleServiceRepository.findServices(protocoleID).forEach( item -> {
            pServices.add(toPartial(item));
        });

        return pServices;
    }
}
