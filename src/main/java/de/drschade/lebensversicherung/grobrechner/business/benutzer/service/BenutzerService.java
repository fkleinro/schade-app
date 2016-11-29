package de.drschade.lebensversicherung.grobrechner.business.benutzer.service;

import de.drschade.lebensversicherung.grobrechner.business.benutzer.entity.Benutzer;
import de.drschade.lebensversicherung.grobrechner.security.SecurityUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fklein-robbenhaar
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BenutzerService {

    private static final Logger logger = LoggerFactory.getLogger(BenutzerService.class);

    @PersistenceContext(unitName = "GrobrechnerPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
    }

    public List<Benutzer> findAllBenutzerWithIpAdresse() {
        Query query = em.createQuery("FROM Benutzer WHERE rolle = 'IP'");   
        return query.getResultList();
    }

    public Benutzer findBenutzer(String username) {
        Query query = em.createQuery("FROM Benutzer WHERE username='" + username + "' AND rolle = 'WEB'");
        if (query.getResultList().size() > 0) {
            Benutzer foundBenutzer = (Benutzer) query.getResultList().get(0);
            return foundBenutzer;
        } 
        return null;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public Benutzer authentifiziere(String username, String password) {
        Query query = em.createQuery("FROM Benutzer WHERE username='" + username + "' AND rolle = 'REST'");
        if (query.getResultList().size() > 0) {
            Benutzer foundBenutzer = (Benutzer) query.getResultList().get(0);
            String encryptedPassword = SecurityUtil.getPasswordHash(password);
            if (encryptedPassword != null && encryptedPassword.equals(password)) {
                return foundBenutzer;
            }
        }

        return null;
    }

}
