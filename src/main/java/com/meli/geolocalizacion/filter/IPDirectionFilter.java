package com.meli.geolocalizacion.filter;

import com.meli.geolocalizacion.model.service.IPDirectionService;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class IPDirectionFilter implements Filter {

    private final IPDirectionService ipDirectionService;

    public IPDirectionFilter(IPDirectionService ipDirectionService) {
        this.ipDirectionService = ipDirectionService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(((RequestFacade) servletRequest).getRequestURI().equals("/ip-direction/get")) {
            String ip = servletRequest.getParameter("ip");
            if(ip != null && !ip.equals("") && validateIPDirection(ip)) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.sendError(401);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean validateIPDirection(String ip) {
        if(!ipDirectionService.isBlockedIPDirection(ip)) {
            return true;
        } else {
            ipDirectionService.save(ip);
            return false;
        }
    }
}
