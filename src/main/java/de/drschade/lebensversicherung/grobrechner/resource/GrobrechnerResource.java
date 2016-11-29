package de.drschade.lebensversicherung.grobrechner.resource;

import de.drschade.lebensversicherung.grobrechner.business.berechnung.entity.Berechnung;
import de.drschade.lebensversicherung.grobrechner.business.berechnung.service.BerechnungService;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resources are:
 * http://localhost:8080/Grobrechner/rest/berechnung/
 *
 * @author fklein-robbenhaar
 */
@Stateless
@Path("berechnung")
public class GrobrechnerResource {

    private static final Logger logger = LoggerFactory.getLogger(GrobrechnerResource.class);
   
    @Inject
    BerechnungService berechnungService;

    @PostConstruct
    public void init() {
    }

    /**
     * http://localhost:8080/Grobrechner/rest/berechnung/1
     * @param berechnungId
     * @return 
     */
    @GET
    @Path("/{berechnungId}")
    @Produces("application/json;charset=UTF-8")
    public Response find(@PathParam("berechnungId") String berechnungId) {
        Berechnung berechnung = berechnungService.findBerechnung(Integer.valueOf(berechnungId));
        if (berechnung == null) {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "Berechnug existiert nicht").build();
        } else {
            return Response.ok(berechnung).build();
        }
    }

    /**
     * http://localhost:8080/Grobrechner/rest/berechnung/iptest
     * @return 
     */
    @GET
    @Path("/iptest")
    @Produces("application/json;charset=UTF-8")
    public Response getip() {
        LocalDate iptestdate = LocalDate.now();
        return Response.ok(iptestdate).build();
    }

    
    /**
     * http://localhost:8080/Grobrechner/rest/berechnung/neu?geschlecht=1&vertragart=0&vertragmonatsbeitrag=383.47&vertrageinmalzahlung=0&vertragzahlweise=12&geburtsjahr=1964&vertragbeginnjahr=1990&vertragendejahr=2015&vertragjahrkuendigung=2015&vertragauszahlbetrag=167624.22&vertragleistungsarttod=2&betragtodesfallleistung=115040.67&vertragleistungsartbu=0&betragbuleistungmonatsrente=0
     * @param landcode
     * @param vertragtyp
     * @param vertragbeitraganfangseinlage
     * @param vertragbeitragperiodisch
     * @param vertragbeitragperiodischzahlweise
     * @param vertragbeitragdynamikeuro
     * @param vertragbeitragdynamikprozent
     * @param vertragbeitragdynamikrhythmus
     * @param vertragbeitragdynamikkuendigungjahr
     * @param vertragverspgeschlecht
     * @param vertragverspgeburtsjahr
     * @param vertragpartnergeburtsjahr
     * @param vertragbeginnjahr 
     * @param vertragendejahr
     * @param vertragbeginndatum
     * @param vertragendedatum
     * @param vertragendejahrbeitrag
     * @param vertragkuendigung
     * @param vertragkuendigungjahr
     * @param vertragkuendigungendebetrag
     * @param vertragkuendigungendedatum
     * @param vertragleistungtodart
     * @param vertragleistungtodbetrag
     * @param vertragleistungbuart
     * @param vertragleistungbubetrag
     * @param vertragleistungunfalltodart
     * @param vertragleistungunfalltodbetrag
     * @param vertragisverbundeneleben 
     * @param vertragaktuellrkwbetrag 
     * @param vertragaktuellrkwdatum 
     * @param vertragbeitragperiodischreduktionjahr 
     * @param vertragbeitragperiodischreduktioneuro 
     * @param vertragplausibeitragssummebetrag 
     * @return  
     */
    @GET
    @Path("/neu")
    @Consumes({"application/json;charset=UTF-8"})
    @Produces("application/json;charset=UTF-8")
    public Response input(
        @QueryParam("landcode") String landcode,
        @QueryParam("vertragtyp") String vertragtyp,
        @QueryParam("vertragbeitragperiodisch") String vertragbeitragperiodisch,
        @QueryParam("vertragbeitragperiodischzahlweise") String vertragbeitragperiodischzahlweise,
        @QueryParam("vertragbeitraganfangseinlage") String vertragbeitraganfangseinlage,
        @QueryParam("vertragbeitragdynamikeuro") String vertragbeitragdynamikeuro,
        @QueryParam("vertragbeitragdynamikprozent") String vertragbeitragdynamikprozent,
        @QueryParam("vertragbeitragdynamikrhythmus") String vertragbeitragdynamikrhythmus,
        @QueryParam("vertragbeitragdynamikkuendigungjahr") String vertragbeitragdynamikkuendigungjahr,
        @QueryParam("vertragverspgeburtsjahr") String vertragverspgeburtsjahr,
        @QueryParam("vertragverspgeschlecht") String vertragverspgeschlecht,
        @QueryParam("vertragpartnergeburtsjahr") String vertragpartnergeburtsjahr,
        @QueryParam("vertragbeginnjahr") String vertragbeginnjahr, 
        @QueryParam("vertragendejahr") String vertragendejahr,
        @QueryParam("vertragbeginndatum") String vertragbeginndatum, 
        @QueryParam("vertragendedatum") String vertragendedatum,
        @QueryParam("vertragendejahrbeitrag") String vertragendejahrbeitrag,
        @QueryParam("vertragkuendigung") String vertragkuendigung,
        @QueryParam("vertragkuendigungjahr") String vertragkuendigungjahr,
        @QueryParam("vertragkuendigungendebetrag") String vertragkuendigungendebetrag,
        @QueryParam("vertragkuendigungendedatum") String vertragkuendigungendedatum,       
        @QueryParam("vertragleistungtodart") String vertragleistungtodart,
        @QueryParam("vertragleistungtodbetrag") String vertragleistungtodbetrag,
        @QueryParam("vertragleistungbuart") String vertragleistungbuart,
        @QueryParam("vertragleistungbubetrag") String vertragleistungbubetrag,
        @QueryParam("vertragleistungunfalltodart") String vertragleistungunfalltodart,
        @QueryParam("vertragleistungunfalltodbetrag") String vertragleistungunfalltodbetrag,
        @QueryParam("vertragisverbundeneleben") String vertragisverbundeneleben,
        @QueryParam("vertragaktuellrkwbetrag") String vertragaktuellrkwbetrag,
        @QueryParam("vertragaktuellrkwdatum") String vertragaktuellrkwdatum,
        @QueryParam("vertragbeitragperiodischreduktionjahr") String vertragbeitragperiodischreduktionjahr,
        @QueryParam("vertragbeitragperiodischreduktioneuro") String vertragbeitragperiodischreduktioneuro,
        @QueryParam("vertragplausibeitragssummebetrag") String vertragplausibeitragssummebetrag)
    {       
        //Berechnungsobjekt bauen
        Berechnung berechnung = new Berechnung();
        berechnung.reset();
       
        //jetzt Properties setzen
        if (landcode != null) {
            if (!landcode.isEmpty()) {
                switch (landcode.toUpperCase()) { 
                    case "DE": berechnung.setLandCode(1); break;
                    case "AT": berechnung.setLandCode(2); break;
                    default: berechnung.setLandCode(1); break;
                }
            }
        }
        if (vertragverspgeschlecht != null) {
            switch (vertragverspgeschlecht.toLowerCase()) {
                case "female": berechnung.setVertragVersPGeschlecht(Boolean.FALSE); break;
                case "male": berechnung.setVertragVersPGeschlecht(Boolean.TRUE); break;
                default: berechnung.setVertragVersPGeschlecht(Boolean.TRUE); break;
            }
        }
        if (vertragtyp != null) {
            switch (vertragtyp.toLowerCase()) {
                case "classic": berechnung.setVertragTyp(0); break;
                case "fonds": berechnung.setVertragTyp(1); break;
                default: berechnung.setVertragTyp(0); break;
            }
        }
        if (vertragbeitragperiodisch != null) {
            if (!vertragbeitragperiodisch.isEmpty()) {
                berechnung.setVertragBeitragPeriodisch(new BigDecimal(vertragbeitragperiodisch));
            }
        }
        if (vertragbeitraganfangseinlage != null) {
            if (vertragbeitraganfangseinlage.isEmpty()) {
                berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
            } else {
                berechnung.setVertragBeitragAnfangseinlage(new BigDecimal(vertragbeitraganfangseinlage));
            }
        }
        if (vertragbeitragperiodischzahlweise != null) {
            if (vertragbeitragperiodischzahlweise.isEmpty()) {
                berechnung.setVertragBeitragPeriodischZahlweise(new Integer(12));
            } else {
                berechnung.setVertragBeitragPeriodischZahlweise(new Integer(vertragbeitragperiodischzahlweise));
            }   
        }
        if (vertragbeitragdynamikeuro != null) {
            if (!vertragbeitragdynamikeuro.isEmpty()) {
                berechnung.setVertragBeitragDynamikEuro(new BigDecimal(vertragbeitragdynamikeuro));
            }
        }
        if (vertragbeitragdynamikprozent != null) {
            if (!vertragbeitragdynamikprozent.isEmpty()) {
                berechnung.setVertragBeitragDynamikProzent(new BigDecimal(vertragbeitragdynamikprozent).divide(BigDecimal.valueOf(100)));
            }
        }
        if (vertragbeitragdynamikrhythmus != null) {
            if (!vertragbeitragdynamikrhythmus.isEmpty()) {
                berechnung.setVertragBeitragDynamikRhythmus(new Integer(vertragbeitragdynamikrhythmus));
            }
        }
        if (vertragbeitragdynamikkuendigungjahr != null) {
            if (!vertragbeitragdynamikkuendigungjahr.isEmpty()) {
                berechnung.setVertragBeitragDynamikKuendigungJahr(new Integer(vertragbeitragdynamikkuendigungjahr));
            }
        }
        if (vertragverspgeburtsjahr != null) {
            if (!vertragverspgeburtsjahr.isEmpty()) {
                berechnung.setVertragVersPGeburtsjahr(new Integer(vertragverspgeburtsjahr));
            }
        }
        if (vertragpartnergeburtsjahr != null) {
            if (!vertragpartnergeburtsjahr.isEmpty()) {
                berechnung.setVertragPartnerGeburtsjahr(new Integer(vertragpartnergeburtsjahr));
            }
        }
        if (vertragbeginnjahr != null) {
            if (!vertragbeginnjahr.isEmpty()) {
                berechnung.setVertragBeginnJahr(new Integer(vertragbeginnjahr));
            }
        }
        if (vertragendejahr != null) {
            if (!vertragendejahr.isEmpty()) {
                berechnung.setVertragEndeJahr(new Integer(vertragendejahr));
            }    
        }
        if (vertragendejahrbeitrag != null) {
            if (!vertragendejahrbeitrag.isEmpty()) {
                berechnung.setVertragEndeJahrBeitrag(new Integer(vertragendejahrbeitrag));
            }
        }
        if (vertragkuendigung != null) {
            if (!vertragkuendigung.isEmpty()) {
                berechnung.setVertragKuendigung(new Integer(vertragkuendigung));
            }
        }
        if (vertragkuendigungjahr != null) {
            if (!vertragkuendigungjahr.isEmpty()) {
                berechnung.setVertragKuendigungJahr(new Integer(vertragkuendigungjahr));            
            } else {
                berechnung.setVertragKuendigungJahr(null);
            }
        }
        if (vertragkuendigungendebetrag != null) {
            if (!vertragkuendigungendebetrag.isEmpty()) {
                berechnung.setVertragKuendigungEndeBetrag(new BigDecimal(vertragkuendigungendebetrag));
            }
        }
        //hier noch das KuendigungEndeDatum klar machen
        if (vertragkuendigungendedatum != null) {
            if (!vertragkuendigungendedatum.isEmpty()) {
                LocalDate date = LocalDate.parse(vertragkuendigungendedatum);
                berechnung.setVertragKuendigungEndeDatum(date);
                berechnung.setVertragKuendigungJahr(date.getYear());
            }
        }
//        System.out.println("from_restapi:"+berechnung.getVertragKuendigungEndeDatum().toString());
        
        if (vertragleistungtodart != null) {
            if (!vertragleistungtodart.isEmpty()) {
                switch (vertragleistungtodart.toLowerCase()) {
                    case "none": berechnung.setVertragLeistungTodArt(0); break;
                    case "single_payment_contract_balance": berechnung.setVertragLeistungTodArt(1);  break;
                    case "single_payment_fixed_amount": berechnung.setVertragLeistungTodArt(2); break;
                    case "single_payment_percentage_premium_paid": berechnung.setVertragLeistungTodArt(3); break;                
                    case "not_sure": berechnung.setVertragLeistungTodArt(-1);  break;
                    default: berechnung.setVertragLeistungTodArt(0); break;
                }
            }    
        }
        if (vertragleistungtodbetrag != null) {
            if (!vertragleistungtodbetrag.isEmpty()) {
                berechnung.setVertragLeistungTodBetrag(new BigDecimal(vertragleistungtodbetrag));
            }
        }
        if (vertragleistungbuart != null) {
            if (!vertragleistungbuart.isEmpty()) {
                switch (vertragleistungbuart.toLowerCase()) {
                    case "none": berechnung.setVertragLeistungBUArt(0); break;
                    case "premium_release": berechnung.setVertragLeistungBUArt(1);  break;
                    case "annuity": berechnung.setVertragLeistungBUArt(2);  break;
                    case "premium_release_annuity": berechnung.setVertragLeistungBUArt(3); break;
                    case "not_sure": berechnung.setVertragLeistungBUArt(-1); break;
                    default: berechnung.setVertragLeistungBUArt(0); break;
                }
            }    
        }
        if (vertragleistungbubetrag != null) {
            if (!vertragleistungbubetrag.isEmpty()) {
                berechnung.setVertragLeistungBUBetrag(new BigDecimal(vertragleistungbubetrag));
            }
        }
        if (vertragleistungunfalltodart != null) {
            if (!vertragleistungunfalltodart.isEmpty()) {
                switch (vertragleistungunfalltodart.toLowerCase()) {
                    case "none": berechnung.setVertragLeistungUnfalltodArt(0); break;
                    case "single_payment_fixed_amount": berechnung.setVertragLeistungUnfalltodArt(1); break;
                    case "not_sure": berechnung.setVertragLeistungUnfalltodArt(-1); break;
                    default: berechnung.setVertragLeistungUnfalltodArt(0); break;
                }
            }
        }
        if (vertragleistungunfalltodbetrag != null) {
            if (!vertragleistungunfalltodbetrag.isEmpty()) {
                berechnung.setVertragLeistungUnfalltodBetrag(new BigDecimal(vertragleistungunfalltodbetrag));
            }
        }
        if (vertragisverbundeneleben != null) {
            if (!vertragisverbundeneleben.isEmpty()) {
                if ("0".equals(vertragisverbundeneleben)) {
                    berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
                } else {
                    berechnung.setVertragIsVerbundeneLeben(Boolean.TRUE);
                }
            }
        }
        if (vertragaktuellrkwbetrag != null) {
            if (!vertragaktuellrkwbetrag.isEmpty()) {
                berechnung.setVertragAktuellRKWBetrag(new BigDecimal(vertragaktuellrkwbetrag));
            }
        }
        if (vertragaktuellrkwdatum != null) {
            if (!vertragaktuellrkwdatum.isEmpty()) {
                LocalDate date = LocalDate.parse(vertragaktuellrkwdatum);
                berechnung.setVertragAktuellRKWDatum(date);
            }
        }
        if (vertragbeitragperiodischreduktionjahr != null) {
            if (!vertragbeitragperiodischreduktionjahr.isEmpty()) {
                berechnung.setVertragBeitragPeriodischReduktionJahr(new Integer(vertragbeitragperiodischreduktionjahr));
            }
        }
        if (vertragbeitragperiodischreduktioneuro != null) {
            if (!vertragbeitragperiodischreduktioneuro.isEmpty()) {
                berechnung.setVertragBeitragPeriodischReduktionEuro(new BigDecimal(vertragbeitragperiodischreduktioneuro));
            }
        }
        if (vertragplausibeitragssummebetrag != null) {
            if (!vertragplausibeitragssummebetrag.isEmpty()) {
                berechnung.setVertragPlausiBeitragssummeBetrag(new BigDecimal(vertragplausibeitragssummebetrag));
            }
        }
        //Berechnungsroutine aufrufen
        berechnung = berechnungService.berechne(berechnung, null);
        
        //Ausgabe testweise
//        System.out.println(""+berechnung.getVertragMonatsbeitrag());
//        System.out.println(""+berechnung.getVertragBeitragZahlweise());
       
        //Exceptionbehandlung
        if (berechnung == null) {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "name does not exist").build();
        } else {
            return Response.ok(berechnung).build();
        }
    }
 
}
