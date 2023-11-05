package com.gmhis_backk.domain.cautionTransaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gmhis_backk.domain.Patient;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "caution_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GMHISCautionTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    private String action;

    private Double amount;

    private Double patientAccountBalance;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

}
