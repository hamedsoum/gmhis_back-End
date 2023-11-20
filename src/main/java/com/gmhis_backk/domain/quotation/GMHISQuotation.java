package com.gmhis_backk.domain.quotation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name= "quotation")
public class GMHISQuotation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String code;

    private String quotationNumber;

    private  String affection;

    private  String indication;

    @JoinColumn(name="insurance_id")
    private Long insuranceId;

    @JoinColumn(name="insurance_name")
    private String insuranceName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @Column(name="total_amount")
    private Double totalAmount ;

    @Column(name="moderator_ticket")
    private Double moderatorTicket;

    @Column(name="cmu_part")
    private Double cmuPart;

    private Double discount;

    @Column(name="net_to_pay")
    private Double netToPay;

    @Column(name="insurance_part")
    private Double insurancePart;

    private String status;

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
