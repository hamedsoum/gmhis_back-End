package com.gmhis_backk.constant;

import lombok.Getter;


@Getter
public enum GMHISAdmissionType {
    EMERGENCY("emergency"),
    NORMAL("normal");

    private final String value;
    GMHISAdmissionType(String value) {
    this.value = value;
    }

}
