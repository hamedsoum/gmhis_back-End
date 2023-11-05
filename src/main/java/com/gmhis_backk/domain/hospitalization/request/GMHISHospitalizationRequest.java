package com.gmhis_backk.domain.hospitalization.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gmhis_backk.domain.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "hospitalization_request")
public class GMHISHospitalizationRequest {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String code;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="practician_id")
    private Pratician practician;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="examination_id")
    private Examination examination;

    @JoinColumn(name="admission_id")
    private Long admission_id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="insured_id")
    private Insured insured;

    private String reason;

    private String protocole;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_Date")
    private Date startDate;

    @Column(name="day_number")
    private int dayNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;

    @Column(name="created_by")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatededAt;

    @Column(name="updated_by")
    private Long updatedBy;
}
