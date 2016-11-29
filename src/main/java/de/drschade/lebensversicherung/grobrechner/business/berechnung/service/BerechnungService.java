package de.drschade.lebensversicherung.grobrechner.business.berechnung.service;

import de.drschade.lebensversicherung.grobrechner.business.benutzer.entity.Benutzer;
import de.drschade.lebensversicherung.grobrechner.business.berechnung.entity.Berechnung;
import de.drschade.lebensversicherung.grobrechner.business.kernel.BerechnungRUeGrobKernel;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fklein-robbenhaar
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BerechnungService {

    @PersistenceContext(unitName = "GrobrechnerPU")
    private EntityManager em;

    BerechnungRUeGrobKernel kernelGrob = new BerechnungRUeGrobKernel();

    @PostConstruct
    public void init() {
    }

    /**
     *
     * @param berechnung
     * @return
     */
    public Berechnung berechne(Berechnung berechnung, Benutzer benutzer) {

        if (benutzer != null) {
            berechnung.setBenutzer(benutzer);
            if (benutzer.getKontingent() == null || (benutzer.getKontingent() != null && benutzer.getKontingent() > 0)) {

                if (benutzer.getKontingent() != null) {
                    benutzer.setKontingent(benutzer.getKontingent() - 1);
                    em.merge(benutzer);
                }
            } else {
                //Kontigngent ist erschöpft
                berechnung.getErgebnis().setPlausiCode(-99);
                return berechnung;
            }
        }

        // TODO: dies muss oben, nur bei Benutzer != null, stehen.
        berechnung = kernelGrob.berechne(berechnung);
        // setze die Id auf Null, damit immer ein neuer Datensatz angelegt wird
        berechnung.setId(null);
        berechnung.getErgebnis().setId(null);
        em.persist(berechnung);

        return berechnung;

    }

    public Berechnung findBerechnung(Integer id) {
        return new Berechnung();
    }
}
