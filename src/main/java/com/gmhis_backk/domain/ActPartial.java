package com.gmhis_backk.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActPartial {
    private Long id;

    private String name;

    private String code;

    private int coefficient;

    private int codValue;
}
