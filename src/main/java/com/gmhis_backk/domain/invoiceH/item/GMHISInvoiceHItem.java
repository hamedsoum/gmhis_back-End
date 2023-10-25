package com.gmhis_backk.domain.invoiceH.item;

import com.gmhis_backk.domain.invoiceH.GMHISInvoiceH;
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
@Table(name= "invoice_h_item")
public class GMHISInvoiceHItem {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String code;

    @JoinColumn(name="act_id")
    private Long actId;

    @JoinColumn(name="act_code_id")
    private Long actCodeId;

    @JoinColumn(name="act_coefficient")
    private int actCoefficient;

    private int quantity;

    @JoinColumn(name="unit_code_value")
    private Double actCodeValue;

    @Column(name="act_code")
    private String actCode;

    @JoinColumn(name="total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name="invoice_h_id")
    private GMHISInvoiceH invoiceH;

    @JoinColumn(name="moderator_ticket")
    private Double moderatorTicket;

    @JoinColumn(name="cmu_amount")
    private Double cmuAmount;

    @JoinColumn(name="cmu_percent")
    private int cmuPercent;

    @JoinColumn(name="insurance_percent")
    private int insurancePercent;


    @JoinColumn(name="practician_id")
    private Long practicianId;

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
