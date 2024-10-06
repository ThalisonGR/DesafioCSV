package com.ifrs.DesafioCSV.util;
import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ExpectHeaderFilter implements Filter {

    /*
        


     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Verifica e ignora o cabeçalho 'Expect'
        if (httpRequest.getHeader("Expect") != null) {
            chain.doFilter(request, response);  // Permite a requisição passar sem interrupção
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
