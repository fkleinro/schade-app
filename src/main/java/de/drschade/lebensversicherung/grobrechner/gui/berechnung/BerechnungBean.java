package de.drschade.lebensversicherung.grobrechner.gui.berechnung;

import de.drschade.lebensversicherung.grobrechner.business.benutzer.entity.Benutzer;
import de.drschade.lebensversicherung.grobrechner.business.berechnung.entity.Berechnung;
import de.drschade.lebensversicherung.grobrechner.business.berechnung.service.BerechnungService;
import de.drschade.lebensversicherung.grobrechner.gui.BaseBean;
import de.drschade.lebensversicherung.grobrechner.gui.benutzer.BenutzerSessionBean;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fklein-robbenhaar
 */
@Named
@SessionScoped
public class BerechnungBean extends BaseBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BerechnungBean.class);

    @Inject
    BerechnungService berechnungService;

    @Inject
    BenutzerSessionBean benutzerSessionBean;

    Date vertragAktuellRKWDatum;
    Date vertragKuendigungEndeDatum;

    Berechnung berechnung;

    @PostConstruct
    public void init() {
        logger.info("init on new instance");
        berechnung = new Berechnung();
    }

    public void starteBerechnung(String navigation) {
        Set<ConstraintViolation<Berechnung>> violations = getValidator().validate(this.berechnung);
        for (ConstraintViolation<Berechnung> violation : violations) {
            this.showValidationError(violation.getMessage());
        }
        
        logger.info("Berechnungsparameter sind " + berechnung.toJsonString());
        /*
        if (berechnung.getVertragAktuellRKWBetrag()!= null && berechnung.getVertragAktuellRKWBetrag().doubleValue() > 0) {
            this.showValidationError("Bitte geben Sie ");
            return;
        }
         */

        if (violations.size() == 0) {
            Benutzer benutzer = benutzerSessionBean.getBenutzer();
            logger.info("Berechnung für Benutzer " + benutzer);

            berechnung = berechnungService.berechne(berechnung, benutzer);
            if (berechnung != null) {
                setSessionObject(berechnung);
                if (berechnung.getErgebnis().getPlausiCode() == -99) {
                    //Ggf. Kontigent überschritten 
                    navigate("outofscope");
                } else {
                    navigate(navigation);
                }
            } else {
                //Irgendwas anderes ging schief
                navigate("unauthorized");
            }
        }
    }

    public Berechnung getBerechnung() {
        return berechnung;
    }

    public void setBerechnung(Berechnung berechnung) {
        this.berechnung = berechnung;
    }

    /**
     * Only for logging. Logs when this class is destroyed.
     */
    @PreDestroy
    public void preDestroy() {
        logger.info("pre destroy on " + this);
    }

}
