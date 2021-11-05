package com.meli.geolocalizacion.model.service;

import com.meli.geolocalizacion.model.dto.IpDirectionInfoResponse;
import com.meli.geolocalizacion.model.entity.IPDirection;
import com.meli.geolocalizacion.model.repository.IPDirectionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPDirectionServiceTest {

    @MockBean
    private IPDirectionRepository ipDirectionRepository;

    @Autowired
    private IPDirectionService ipDirectionService;

    private static final String IP_DIRECTION = "5.6.7.8";

    @Test
    public void validateResponseGetAllIPDirection() {
        IPDirection ip = IPDirection.builder().ipDirection(IP_DIRECTION).blocked(true).build();
        List<IPDirection> listIPDirection = Collections.singletonList(ip);
        when(ipDirectionRepository.findAll()).thenReturn(listIPDirection);
        assertThat(ipDirectionService.getAllIPDirection()).hasSize(1);
    }

    @Test
    public void validateSaveIPDirection() {
        IPDirection ip = IPDirection.builder().ipDirection(IP_DIRECTION).blocked(true).build();
        when(ipDirectionRepository.save(ip)).thenReturn(ip);
        assertThat(ipDirectionService.save(IP_DIRECTION)).isSameAs(ip);
    }

    @Test
    public void validateResponseGetInfoIPDirection() throws Exception {
        assertThat(ipDirectionService.getInfoIPDirection(IP_DIRECTION).getClass()).isSameAs(IpDirectionInfoResponse.class);
    }
}
