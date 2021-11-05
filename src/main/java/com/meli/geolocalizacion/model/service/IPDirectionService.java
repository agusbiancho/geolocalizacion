package com.meli.geolocalizacion.model.service;

import com.meli.geolocalizacion.model.dto.IpDirectionInfoResponse;
import com.meli.geolocalizacion.model.entity.IPDirection;

import java.util.List;

public interface IPDirectionService {

    public IpDirectionInfoResponse getInfoIPDirection(String ip) throws Exception;

    public List<IPDirection> getAllIPDirection();

    public boolean isBlockedIPDirection(String ip);

    public IPDirection save(String ip);
}
