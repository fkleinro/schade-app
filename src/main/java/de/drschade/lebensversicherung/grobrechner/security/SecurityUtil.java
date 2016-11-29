package de.drschade.lebensversicherung.grobrechner.security;

import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;
import org.jboss.security.auth.spi.Util;

/**
 * 
 * @author fklein-robbenhaar
 */
public class SecurityUtil {

    static final String HASH_ALGORITHMUS = "SHA-1";
    static final String ENCODING = "HEX";

    /**
     * Creates a basic authentification string by user and password.
     * The format is '<user>:<password>'.
     * @param user
     * @param password
     * @return 
     */
    public String getBasicAuthentication(String user, String password) {
        String token = user + ":" + password;
        try {
            return "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode with UTF-8", ex);
        }
    }
    
    /**
     * Create a hashed password by delegating to the method <createPasswordHash> in 
     * <code>org.jboss.security.auth.spi.Util</code>.
     * Uses the constants HASH_ALGORITHMUS and ENCODING of this class.
     * @param password
     * @return 
     */
    public static String getPasswordHash(String password) {
        return Util.createPasswordHash(HASH_ALGORITHMUS, ENCODING, null, null, password);
    }

}
