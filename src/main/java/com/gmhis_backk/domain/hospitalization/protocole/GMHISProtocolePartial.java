package com.gmhis_backk.domain.hospitalization.protocole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISProtocolePartial {
    private UUID id;

    private String description;
}
