package com.gmhis_backk.domain.hospitalization.protocole.service;

import com.gmhis_backk.domain.hospitalization.GMHISHospitalization;
import com.gmhis_backk.domain.hospitalization.protocole.GMHISProtocole;
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
@Table(name= "protocole_service")
public class GMHISProtocoleService {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String detail;

    private Date serviceDate;

    @ManyToOne
    @JoinColumn(name="protocole_id")
    private GMHISProtocole protocole;

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
