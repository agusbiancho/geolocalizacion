package com.meli.geolocalizacion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IpDirectionInfoResponse {

    private String ipDirection;
    private String nameISO;
    private String codeISO;
    private String currency;
    private String quotation;

}
