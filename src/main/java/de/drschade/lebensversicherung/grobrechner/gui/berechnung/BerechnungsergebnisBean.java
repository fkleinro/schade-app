package de.drschade.lebensversicherung.grobrechner.gui.berechnung;

import de.drschade.lebensversicherung.grobrechner.business.berechnung.entity.Berechnung;
import de.drschade.lebensversicherung.grobrechner.gui.BaseBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@ViewScoped
public class BerechnungsergebnisBean extends BaseBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BerechnungsergebnisBean.class);


    Berechnung ergebnis;

   
    @PostConstruct
    public void init() {
        logger.info("init on post construct with " + getSessionObject());
        this.ergebnis = (Berechnung)getSessionObject();       
    }

    /**
     * Only for logging. Logs when this class is destroyed.
     */
    @PreDestroy
    public void preDestroy() {
        logger.info("pre destroy on " + getSessionObject() + " on " + this);
    }

   
    public Berechnung getBerechnung() {
        return ergebnis;
    }

}
