package com.gmhis_backk.domain.hospitalization;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Pratician;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "hospitalization")
public class GMHISHospitalization {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String code;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="practician_id")
    private Pratician practician;

    private String reason;

    private String bedroom;

    private Date start;

    private Date end;

    private  String protocole;

    private  String conclusion;

    private  String status;

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
