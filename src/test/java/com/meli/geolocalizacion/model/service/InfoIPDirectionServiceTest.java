package com.meli.geolocalizacion.model.service;

import com.meli.geolocalizacion.util.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoIPDirectionServiceTest {

    @Autowired
    private InfoIPDirectionService infoIPDirectionService;

    private static final String IP_DIRECTION = "5.6.7.8";

    private static final String IP_DIRECTION_FALSE = "5.6.7.8.9";

    private static final String KEY = "countryName";

    @Test
    public void validateResponseInfoIPDirectionService() {
        String country = Parser.getValueByKey(
                infoIPDirectionService.getInfoIPDirection(IP_DIRECTION),
                KEY,
                false);
        assertThat(country.equals("Germany")).isEqualTo(true);
    }

    @Test
    public void validateBadRequestInfoIPDirectionService() {
        String bodyResponse = infoIPDirectionService.getInfoIPDirection(IP_DIRECTION_FALSE);
        assertThat(bodyResponse.contains("Bad Request")).isEqualTo(true);
    }
}
