package de.drschade.lebensversicherung.grobrechner.gui.security;

import de.drschade.lebensversicherung.grobrechner.business.benutzer.entity.Benutzer;
import de.drschade.lebensversicherung.grobrechner.business.benutzer.service.BenutzerService;
import de.drschade.lebensversicherung.grobrechner.gui.benutzer.BenutzerSessionBean;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.ResourceHandler;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.omnifaces.util.Servlets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://localhost:8080/Grobrechner/views/ipprotected/main.jsf
 * @author fklein-robbenhaar
 */
//@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}, urlPatterns = {"/views/ipprotected/*"})
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST}, urlPatterns = {"/views/ipprotected/*"})
public class IPAddressLoginFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(IPAddressLoginFilter.class);

    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

    private FilterConfig config = null;

    @Inject
    BenutzerSessionBean benutzerSessionBean;

    @Inject
    BenutzerService benutzerService;

    List<Benutzer> benutzerListe;
    Map<LocalDateTime, String> unauthorizedIPs = new ConcurrentHashMap<LocalDateTime, String>();

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        this.pattern = Pattern.compile(IPADDRESS_PATTERN);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress != null) {
            logger.info("dofilter: cares only about the first IP if there is a list " + ipAddress);
            ipAddress = ipAddress.replaceFirst(",.*", "");  // cares only about the first IP if there is a list
        } else {
            ipAddress = httpServletRequest.getRemoteAddr();
             logger.info("ip address by remote address "+ipAddress);
        }

        boolean loggedIn = false;

        boolean resourceRequest = httpServletRequest.getRequestURI().startsWith(httpServletRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
        boolean ajaxRequest = "partial/ajax".equals(httpServletRequest.getHeader("Faces-Request"));

        logger.info("dofilter: requested ip address " + ipAddress);

        if (ipAddress != null && isValidIPAddress(ipAddress)) {
            if (!benutzerSessionBean.isCurrentBenutzer(ipAddress)) {
                logger.info("Current user by IP address is unknown");
               
                this.benutzerListe = benutzerService.findAllBenutzerWithIpAdresse();
                logger.info("init: read Benutzer objects with size " + benutzerListe.size());
                for (Benutzer benutzer : benutzerListe) {
                    logger.info("check " + ipAddress + " includes" + benutzer.getIpAdresse());
                    if (benutzer.getIpAdresse() != null && ipAddress.indexOf(benutzer.getIpAdresse()) != -1) {
                        benutzerSessionBean.setBenutzer(benutzer);
                       
                        loggedIn = true;
                        break;
                    }
                }
                if (!loggedIn) {
                    unauthorizedIPs.put(LocalDateTime.now(), ipAddress);
                    unauthorized(httpServletRequest, httpServletResponse, ipAddress);
                    for (LocalDateTime next : unauthorizedIPs.keySet()) {
                        logger.info(next.toString() + " " + unauthorizedIPs.get(next));
                    }
                    if (unauthorizedIPs.size() > 1000) {
                        logger.info("dofilter: clearing unauthorized IPs");
                        unauthorizedIPs.clear();
                    }
                }
            } else {
                loggedIn = true;
                logger.info("doFilter: authorized current user " + benutzerSessionBean.getBenutzer().getName());
            }
        } else {
            unauthorized(httpServletRequest, httpServletResponse);
        }

        if (loggedIn || resourceRequest) {
            if (!resourceRequest) { // Prevent browser from caching restricted resources. See also http://stackoverflow.com/q/4194207/157882
                httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                httpServletResponse.setDateHeader("Expires", 0); // Proxies.
            }

            chain.doFilter(request, httpServletResponse); // So, just continue request.
        } else {
            try {
                Servlets.facesRedirect(httpServletRequest, httpServletResponse, "/Grobrechner/unauthorized.html");
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void destroy() {
    }

    private void unauthorized(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"Protected\"");
        try {
            Servlets.facesRedirect(request, response, "/Grobrechner/unauthorized.html");
        } catch (Exception e) {
        }
    }

    private void unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        unauthorized(request, response, "Unauthorized");
    }

    private static final String IPADDRESS_PATTERN
            = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public Boolean isValidIPAddress(String address) {
        if (this.pattern == null) {
            this.pattern = Pattern.compile(IPADDRESS_PATTERN);
        }
        matcher = pattern.matcher(address);
        return matcher.matches();
    }
}
