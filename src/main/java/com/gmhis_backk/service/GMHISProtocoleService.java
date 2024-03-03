package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.hospitalization.GMHISHospitalization;
import com.gmhis_backk.domain.hospitalization.protocole.GMHISProtocole;
import com.gmhis_backk.domain.hospitalization.protocole.GMHISProtocolePartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.GMHISHospitalizationRepository;
import com.gmhis_backk.repository.GMHISProtocoleRepository;
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
public class GMHISProtocoleService {

    private  final GMHISProtocoleRepository protocoleRepository;
    private  final GMHISHospitalizationRepository hospitalizationRepository;

    private final UserService userService;

    public GMHISProtocoleService(GMHISProtocoleRepository protocoleRepository, GMHISHospitalizationRepository hospitalizationRepository, UserService userService) {
        this.protocoleRepository = protocoleRepository;
        this.hospitalizationRepository = hospitalizationRepository;
        this.userService = userService;
    }

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }

    GMHISProtocolePartial toPartial(GMHISProtocole protocole) {
        GMHISProtocolePartial protocolePartial = new GMHISProtocolePartial();
        protocolePartial.setId(protocole.getId());
        protocolePartial.setDescription(protocole.getDescription());
        return protocolePartial;
    }


     public void create(UUID hospitalizationID, String protocoleDescription) throws ResourceNotFoundByIdException {
        GMHISHospitalization hospitalization = hospitalizationRepository.findById(hospitalizationID).orElse(null);
        GMHISProtocole protocole = new GMHISProtocole();

        log.info("Here");
        if(hospitalization != null ) {
            protocole.setHospitalization(hospitalization);
        }
        protocole.setDescription(protocoleDescription);
        protocole.setCreatedAt(new Date());
        protocole.setCreatedBy(getCurrentUser().getId());
       protocoleRepository.save(protocole);
    }


    public List<GMHISProtocolePartial> findProtocoles(UUID hospitalizationID) {
        List<GMHISProtocolePartial> protocoles = new ArrayList<>();
        protocoleRepository.findProtocoles(hospitalizationID).forEach( item -> {
            protocoles.add(toPartial(item));
        });

        return protocoles;
    }

}
