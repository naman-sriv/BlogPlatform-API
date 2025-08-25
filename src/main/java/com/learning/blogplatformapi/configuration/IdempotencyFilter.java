package com.learning.blogplatformapi.configuration;

import com.learning.blogplatformapi.service.IdempotencyService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class IdempotencyFilter implements Filter {

    private final IdempotencyService idempotencyService;
    private final HttpServletResponse httpServletResponse;

    public IdempotencyFilter(IdempotencyService idempotencyService, HttpServletResponse httpServletResponse){
        this.idempotencyService=idempotencyService;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String idemKey = ((HttpServletRequest) request).getHeader("X-Idempotency-Key");

        if(idemKey != null && "POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String cachedResp = idempotencyService.getResponse(idemKey);
            if(cachedResp!=null){
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                httpResponse.getWriter().write(cachedResp);
                return;
            }

            if(!idempotencyService.setIfNotExists(idemKey)) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
                httpResponse.getWriter().write("A request with this idempotency key is already in progress.");
                return;
            }

            try {
                chain.doFilter(request, response);
            }finally {
                idempotencyService.saveResponse(idemKey, cachedResp);
            }
        }else{
            chain.doFilter(request, response);
        }
    }
}
