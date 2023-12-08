package com.gmhis_backk.domain.hospitalization.protocole.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISProtocoleServicePartial {
    private UUID id;

    private String detail;

    private Date serviceDate;
}
