package com.gmhis_backk.domain.quotation.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.quotation.GMHISQuotation;
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
@Table(name= "quotation_item")
public class GMHISQuotationItem {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String code;

    @JoinColumn(name="act_id")
    private Long actId;

    @JoinColumn(name="act_code")
    private String actCode;

    @JoinColumn(name="act_number")
    private int actNumber;

    private int quantity;

    @JoinColumn(name="unit_price")
    private Double unitPrice;

    @JoinColumn(name="total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name="quotation_id")
    private GMHISQuotation quotation;

    @JoinColumn(name="moderator_ticket")
    private Double moderatorTicket;

    @JoinColumn(name="cmu_amount")
    private Double cmuAmount;

    @JoinColumn(name="cmu_percent")
    private int cmuPercent;

    @JoinColumn(name="insurance_percent")
    private int insurancePercent;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="practician_id")
    private Pratician practician;

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
