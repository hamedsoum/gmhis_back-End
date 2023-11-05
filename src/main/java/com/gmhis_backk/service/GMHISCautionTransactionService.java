package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.cautionTransaction.GMHISCautionTransaction;
import com.gmhis_backk.domain.cautionTransaction.GMHISCautionTransactionCreate;
import com.gmhis_backk.domain.cautionTransaction.GMHISCautionTransactionPartial;
import com.gmhis_backk.exception.domain.InvalidInputException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.GMHISCautionTransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class GMHISCautionTransactionService extends GMHISBaseService<GMHISCautionTransaction, GMHISCautionTransactionCreate, GMHISCautionTransactionPartial> {

    private final UserService userService;

    private final PatientService patientService;

    private final GMHISCautionTransactionRepository cautionTransactionRepository;

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }

    public GMHISCautionTransactionService(UserService userService, PatientService patientService, GMHISCautionTransactionRepository cautionTransactionRepository) {
        this.userService = userService;
        this.patientService = patientService;
        this.cautionTransactionRepository = cautionTransactionRepository;
    }

    public GMHISCautionTransactionPartial create(GMHISCautionTransactionCreate cautionTransactionCreate) throws Exception {
        Patient patient = patientService.findById(cautionTransactionCreate.getPatientID());
        if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");

        if(cautionTransactionCreate.getAmount() < 0) throw new InvalidInputException("Montant Négatif");

        GMHISCautionTransaction cautionTransaction = new GMHISCautionTransaction();
        cautionTransaction.setPatient(patient);
        BeanUtils.copyProperties(cautionTransactionCreate,cautionTransaction,"id");
        cautionTransaction.setCreatedBy(getCurrentUser().getId());
        cautionTransaction.setCreatedAt(LocalDateTime.now());

        Double solde = (Objects.equals(cautionTransactionCreate.getAction(), "debit")) ? patient.getSolde() - cautionTransactionCreate.getAmount() : patient.getSolde() + cautionTransactionCreate.getAmount();
        cautionTransaction.setPatientAccountBalance(solde);
        GMHISCautionTransactionPartial cautionTransactionPartial =  toPartial(cautionTransactionRepository.save(cautionTransaction));

        patientService.updateSolde(cautionTransactionPartial.getPatientID() ,solde);

        return cautionTransactionPartial;
    }

    public List<GMHISCautionTransactionPartial> cautionTransactions(Long patientID) {
        List<GMHISCautionTransactionPartial> cautionTransactions = new ArrayList<>();
        List<GMHISCautionTransaction> cautionTransactionList = cautionTransactionRepository.findCautionTransactions(patientID);
        cautionTransactionList.forEach((cautionTransaction) -> {
            cautionTransactions.add(toPartial(cautionTransaction));
        });

        return cautionTransactions;
    }

    @Override
    public GMHISCautionTransactionPartial toPartial(GMHISCautionTransaction cautionTransaction) {
        GMHISCautionTransactionPartial cautionTransactionPartial = new GMHISCautionTransactionPartial();
        cautionTransactionPartial.setId(cautionTransaction.getId());
        cautionTransactionPartial.setLibelle(cautionTransaction.getLibelle());
        cautionTransactionPartial.setAction(cautionTransaction.getAction());
        cautionTransactionPartial.setAmount(cautionTransaction.getAmount());
        cautionTransactionPartial.setPatientAccountBalance(cautionTransaction.getPatientAccountBalance());
        cautionTransactionPartial.setDate(cautionTransaction.getCreatedAt());
        cautionTransactionPartial.setPatientName(new GMHISName(cautionTransaction.getPatient().getFirstName(), cautionTransaction.getPatient().getLastName()));
        cautionTransactionPartial.setPatientID(cautionTransaction.getPatient().getId());
        return cautionTransactionPartial;
    }
}

