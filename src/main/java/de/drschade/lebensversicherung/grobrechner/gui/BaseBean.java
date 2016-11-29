package de.drschade.lebensversicherung.grobrechner.gui;

import de.drschade.lebensversicherung.grobrechner.business.FachObjekt;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseBean {

    private static final Logger logger = LoggerFactory.getLogger(BaseBean.class);

    @Inject
    Validator validator;

    @Inject
    MainBean mainBean;

    /**
     * Sets a jsf message on the main form.
     *
     * @param message
     */
    public void setErrorOnMainForm(String message) {
        if (Faces.getContext() != null) { // It can be null during session invalidate!
            Messages.addInfo("mainForm", message);
            RequestContext.getCurrentInstance().update(":mainForm");
        }
    }

    /**
     * Adds an error as new faces message to the main form.
     *
     * @param errorMessage
     */
    public void showValidationError(String errorMessage) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
        FacesContext.getCurrentInstance().addMessage("mainForm", message);
    }

    /**
     * Validates an entity by bean validation. Returns true when no constrain
     * validiations exists.
     *
     * @param entity
     * @return
     */
    public boolean isValid(Object entity) {
        boolean valid = true;
        for (ConstraintViolation violation : this.validator.validate(entity)) {
            showValidationError(violation.getMessage());
            valid = false;
        }
        return valid;
    }

    /**
     * Delegates navigate to <code>MainBean</code>.
     *
     * @param page
     */
    public void navigate(String page) {
        logger.info("navigate to " + page);
        mainBean.navigate(page);
    }

    /**
     * Returns injected main bean.
     *
     * @return
     */
    public MainBean getMainBean() {
        return mainBean;
    }

    /**
     * Return injected validator.
     *
     * @return
     */
    public Validator getValidator() {
        return validator;
    }

    public FachObjekt getSessionObject() {
        return mainBean.getSessionObject();
    }

    public void setSessionObject(FachObjekt ergebnis) {
        logger.info("Set object to Session " + ergebnis);
        mainBean.setSessionObject(ergebnis);
    }

}
