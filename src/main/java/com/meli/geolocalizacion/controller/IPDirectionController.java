package com.meli.geolocalizacion.controller;

import com.meli.geolocalizacion.model.dto.IpDirectionInfoResponse;
import com.meli.geolocalizacion.model.entity.IPDirection;
import com.meli.geolocalizacion.model.service.IPDirectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ip-direction")
public class IPDirectionController {

    private final IPDirectionService ipDirectionService;

    public IPDirectionController(IPDirectionService ipDirectionService) {
        this.ipDirectionService = ipDirectionService;
    }

    @GetMapping("/get")
    public ResponseEntity<IpDirectionInfoResponse> getInfoIPDirection(@RequestParam(value = "ip") String ip) {
        try {
            return new ResponseEntity<IpDirectionInfoResponse>(ipDirectionService.getInfoIPDirection(ip), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.TEMPORARY_REDIRECT);
        }


    }

    @GetMapping("/list")
    public ResponseEntity<List<IPDirection>> getAllIPDirection() {
        return new ResponseEntity<List<IPDirection>>(ipDirectionService.getAllIPDirection(), HttpStatus.OK);
    }
}
