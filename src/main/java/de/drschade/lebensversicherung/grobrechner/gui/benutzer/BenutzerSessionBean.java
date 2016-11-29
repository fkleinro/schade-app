package de.drschade.lebensversicherung.grobrechner.gui.benutzer;

import de.drschade.lebensversicherung.grobrechner.business.benutzer.entity.Benutzer;
import de.drschade.lebensversicherung.grobrechner.business.benutzer.service.BenutzerService;
import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class BenutzerSessionBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BenutzerSessionBean.class);

    @Inject
    BenutzerService benutzerService;

    Benutzer benutzer;

    /**
     * Returns the server principal.
     *
     * @return
     */
    private Principal getPrincipal() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
    }

    /**
     * Returns a benutzer object from the session. Checks the benutzer is
     * authorized.
     *
     * @return
     */
    public Benutzer getBenutzer() {
        if (benutzer != null || isAuthorized()) {
            logger.info("Get a user from session scoped " + benutzer);
            return benutzer;
        }
        return null;
    }

    /**
     * 
     * @return 
     */
    public boolean isAuthorized() {
        if (getPrincipal() == null) {
            logout();
            return false;
        }

        if (benutzer == null) {
            Principal principal = getPrincipal();

            logger.info("Initialize by principal " + principal);
            if (principal != null) {

                logger.info("Lese Benutzerobjekt für " + principal.getName());
                if (principal.getName() != null) {
                    try {
                        benutzer = benutzerService.findBenutzer(principal.getName());
                        if (benutzer != null) {
                            logger.info("Set Benutzer name " + benutzer.getUsername());
                        } else {
                            logger.warn("No active Benutzer found by account number " + benutzer.getUsername());
                            logout();
                            return false;
                        }
                    } catch (Exception ex) {
                        logger.error("Read uni throws", ex);
                        logout();
                        return false;
                    }

                }
            }

        }

        return true;
    }

    /**
     * Invalidates the session of the benutzer. Redirects to the login page.
     */
    public void logout() {
        logger.warn("logout");
        logoutInternal("index.html");
    }

    private void logoutInternal(String view) {
        // session already timed out and destroyed. redirect to home page.
        // this will cause a login.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.invalidateSession();

        try {
            String homePage = "/Grobrechner/" + view + "?faces-redirect=true";
            externalContext.redirect(homePage);
            logger.info("Session timed out. Redirect to {}", homePage);
        } catch (Exception e) {
            logger.error("logout", e);
        }
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }
    
    public boolean isCurrentBenutzer(String ipAddress) {
        return (benutzer != null) && (benutzer.getIpAdresse().indexOf(ipAddress) != -1);
    }

}
