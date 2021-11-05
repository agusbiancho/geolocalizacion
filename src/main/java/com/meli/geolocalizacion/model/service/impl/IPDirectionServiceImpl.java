package com.meli.geolocalizacion.model.service.impl;

import com.meli.geolocalizacion.model.dto.IpDirectionInfoResponse;
import com.meli.geolocalizacion.model.entity.IPDirection;
import com.meli.geolocalizacion.model.repository.IPDirectionRepository;
import com.meli.geolocalizacion.model.service.IPDirectionService;
import com.meli.geolocalizacion.model.service.InfoCountryService;
import com.meli.geolocalizacion.model.service.InfoIPDirectionService;
import com.meli.geolocalizacion.model.service.InfoQuotationService;
import com.meli.geolocalizacion.util.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IPDirectionServiceImpl implements IPDirectionService {

    @Value("${currency-base}")
    private String currencyBase;

    private final IPDirectionRepository ipDirectionRepository;

    private final InfoCountryService infoCountryService;

    private final InfoIPDirectionService infoIPDirectionService;

    private final InfoQuotationService infoQuotationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(IPDirectionServiceImpl.class);

    public IPDirectionServiceImpl(IPDirectionRepository ipDirectionRepository,
                                  InfoIPDirectionService infoIPDirectionService,
                                  InfoCountryService infoCountryService,
                                  InfoQuotationService infoQuotationService) {
        this.ipDirectionRepository = ipDirectionRepository;
        this.infoIPDirectionService = infoIPDirectionService;
        this.infoCountryService = infoCountryService;
        this.infoQuotationService = infoQuotationService;
    }

    @Override
    public IpDirectionInfoResponse getInfoIPDirection(String ip) throws Exception {
        IpDirectionInfoResponse ipDirectionInfoResponse = new IpDirectionInfoResponse();
        ipDirectionInfoResponse.setIpDirection(ip);
        String infoIpDirection = infoIPDirectionService.getInfoIPDirection(ip);
        if(infoIpDirection.contains("CIRCUIT-BREAKER") || infoIpDirection.contains("circuit-breaker")){
            LOGGER.error("Enabled circuit-breaker InfoIPDirection.");
            throw new Exception();
        }
        setInfoIP(infoIpDirection, ipDirectionInfoResponse);
        String infoCountry = infoCountryService.getInfoCountry(ipDirectionInfoResponse.getNameISO());
        setInfoCountry(infoCountry, ipDirectionInfoResponse);
        String infoQuotation = infoQuotationService.getInfoQuotation(ipDirectionInfoResponse.getCurrency());

        setInfoQuotation(infoQuotation, ipDirectionInfoResponse, ipDirectionInfoResponse.getCurrency());

        return ipDirectionInfoResponse;
    }

    @Override
    public List<IPDirection> getAllIPDirection() {
        try {
            return ipDirectionRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Error findAllIPDirection: " + e.getCause() + "/n" + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isBlockedIPDirection(String ip) {
        IPDirection ipDirection = null;
        try {
            ipDirection = ipDirectionRepository.findByIpDirection(ip);
        } catch (Exception e) {
            LOGGER.error("Error findByIpDescription: " + e.getCause() + "/n" + e.getMessage());
        }

        return ipDirection == null;
    }

    @Override
    public IPDirection save(String ip) {
        try {
            return ipDirectionRepository.save(IPDirection.builder().ipDirection(ip).blocked(true).build());
        } catch (Exception e) {
            LOGGER.error("Error saveIPDirection: " + e.getCause() + "/n" + e.getMessage());
            throw e;
        }
    }

    private void setInfoIP(String bodyResponse, IpDirectionInfoResponse ipDirectionInfoResponse) {
        ipDirectionInfoResponse.setNameISO(Parser.getValueByKey(bodyResponse, "countryName", false));
        ipDirectionInfoResponse.setCodeISO(Parser.getValueByKey(bodyResponse, "countryCode", false));
    }

    private void setInfoCountry(String bodyResponse, IpDirectionInfoResponse ipDirectionInfoResponse) {
        ipDirectionInfoResponse.setCurrency(Parser.getValueByKeyFromArray(bodyResponse, "currencies"));
    }

    private void setInfoQuotation(String bodyResponse, IpDirectionInfoResponse ipDirectionInfoResponse, String currency) {
        String quotation = "Currency '" + currency + "' = ";
        quotation = quotation + Parser.getValueByKeyFromObject(bodyResponse, "rates", currencyBase, true) + " '" + currencyBase+ "'";
        ipDirectionInfoResponse.setQuotation(quotation);
    }


}
