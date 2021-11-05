package com.meli.geolocalizacion.controller;

import com.meli.geolocalizacion.model.entity.IPDirection;
import com.meli.geolocalizacion.model.service.IPDirectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(IPDirectionController.class)
public class IPDirectionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IPDirectionService ipDirectionService;

    @Test
    public void validateResponseJsonGetListIPDirection() throws Exception {
        IPDirection ip = IPDirection.builder().ipDirection("5.6.7.8").blocked(true).build();
        List<IPDirection> listIPDirection = Collections.singletonList(ip);

        given(ipDirectionService.getAllIPDirection()).willReturn(listIPDirection);

        mvc.perform(get("/ip-direction/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].ipDirection", is(ip.getIpDirection())));
    }
}
