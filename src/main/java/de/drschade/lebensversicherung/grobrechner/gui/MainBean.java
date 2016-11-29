package de.drschade.lebensversicherung.grobrechner.gui;

import de.drschade.lebensversicherung.grobrechner.business.FachObjekt;
import de.drschade.lebensversicherung.grobrechner.business.berechnung.entity.Berechnung;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class MainBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(MainBean.class);

    private static String PROTECTED_FOLDER = "../protected/";

    private String currentFolder;

    private String activePage;

    private static String XHTML_EXTENSION = ".xhtml";

    private FachObjekt selectedSessionObject;

    @PostConstruct
    public void init() {
    }

    /**
     * Returns the active page to display this page in th GUI.
     *
     * @return
     */
    public String getActivePage() {
        return activePage;
    }

    public String getProtectedPage() {
        if (activePage == null) {
            logger.info("active page is null");
            setActivePage("berechnung");
        }
        currentFolder = "";
        logger.info("protected page  " + currentFolder + activePage);
        return currentFolder + activePage;

    }

    public String getIpProtectedPage() {
        if (activePage == null) {
            logger.info("active page is null");
            setActivePage("berechnung");
        }
        currentFolder = PROTECTED_FOLDER;

        logger.info("ip protected page  " + currentFolder + activePage);
        return currentFolder + activePage;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }

    /**
     * Sets the active page to the attribute <code>activePage</code>.
     *
     * @param newPage
     */
    private void setActivePage(String newPage) {

        this.activePage = getNameWithExtension(newPage);
        logger.info("set active page to " + activePage);
    }

    public void navigate(String navigateToPage) {

        logger.info("navigate to " + navigateToPage);
        setActivePage(navigateToPage);

        // Delete all viewscoped beans
        //Faces.setViewRoot(Faces.getViewId());

        RequestContext.getCurrentInstance().update(":mainForm :navigationForm");
    }

    public void navigate(String navigateToPage, Berechnung ergebnis) {

        logger.info("navigate to " + navigateToPage);
        setActivePage(navigateToPage);
        this.selectedSessionObject = ergebnis;

        // Delete all viewscoped beans
        Faces.setViewRoot(Faces.getViewId());

        RequestContext.getCurrentInstance().update(":mainForm :navigationForm");
    }

    public boolean isActive(String page) {
        if (page != null && getNameWithExtension(page).equals(this.activePage)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the name of the JSF page with the extension '*.xhtml'.
     *
     * @param name
     * @return
     */
    private String getNameWithExtension(String name) {
        if (name.endsWith(XHTML_EXTENSION)) {
            return name;
        } else {
            return name + XHTML_EXTENSION;
        }
    }

    public FachObjekt getSessionObject() {
        return selectedSessionObject;
    }

    public void setSessionObject(FachObjekt sessionObject) {
        this.selectedSessionObject = sessionObject;
    }
    
     /**
     * Only for logging. Logs when this class is destroyed.
     */
    @PreDestroy
    public void preDestroy() {
        logger.info("pre destroy on " + this);
    }

}
