/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.drschade.lebensversicherung.grobrechner.business.berechnung.service;

import de.drschade.lebensversicherung.grobrechner.business.berechnung.entity.Berechnung;
import de.drschade.lebensversicherung.grobrechner.business.kernel.BerechnungRUeGrobKernel;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author pschade
 */
public class BerechnungRUeGrobKernelTest {

    public BerechnungRUeGrobKernelTest() {
        //Elementartests
    }

    public void testBigDecimal() {

        // hier wird gerundet!!!!
        BigDecimal b1 = BigDecimal.valueOf((double) 8.6666666666666667);
        System.out.println("" + b1.toString());

        // Dies ist genauer
        BigDecimal b2 = new BigDecimal("8.6666666666666667");
        System.out.println("" + b2.toString());

        double falcherParamterTyp = 0.585;
        System.out.println("Vorsicht" + new BigDecimal(falcherParamterTyp));
        System.out.println("Richtig:" + BigDecimal.valueOf(falcherParamterTyp));
    }

    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testBerechneFall1() {

        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();
        
        System.out.println("#ID Standardfall");
        Berechnung berechnung = new Berechnung();
        //Rückgabewert initialisieren
        berechnung.setLandCode(2);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
      
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
  
        double Monatsbeitrag = 383.47;
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf(Monatsbeitrag));
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        
        berechnung.setVertragVersPGeburtsjahr(1964);
        berechnung.setVertragBeginnJahr(1990);
        berechnung.setVertragEndeJahr(2015);
        berechnung.setVertragKuendigungJahr(2015);
        double AuszahlBetrag = 167624.22;
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf(AuszahlBetrag));
        
        //Leistungsarten setzen
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungBUArt(0); //BU-Rente plus BU-BF
        double TodLeistBetrag = 115040.67;
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf(TodLeistBetrag));
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf(0));
        
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        
        //Ergebnisse pürfen
//        Assert.assertEquals((long)(178157), berechnung.getErgebnis().getRueErstattungApprox().longValue());
/*        Assert.assertEquals((long)(10620), berechnung.getResultRUeErstattungApproxSup().longValue());
        Assert.assertEquals((long)(110616), berechnung.getResultBeitragSummeApproxEcht().longValue());
*/
    }
 
    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testBerechneFall_VKIgrob() {

        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();
        
        System.out.println("#ID VKI-Initialtest");
        Berechnung berechnung = new Berechnung();
        berechnung.reset();
      
        berechnung.setLandCode(2);
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
  
        double Monatsbeitrag = 470.0;
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf(Monatsbeitrag));
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        
        berechnung.setVertragVersPGeburtsjahr(1959);
        berechnung.setVertragBeginnJahr(2006);
        berechnung.setVertragEndeJahr(2029);

        //Leistungsarten setzen
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungBUArt(0); //BU-Rente plus BU-BF
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)12972.0));
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        
        //Ergebnisse pürfen
        
//        Assert.assertEquals((long)(64502), berechnung.getErgebnis().getRueErstattungApprox().longValue());
/*        Assert.assertEquals((long)(60000), berechnung.getResultRUeErstattungApproxVon().longValue());
        Assert.assertEquals((long)(67100), berechnung.getResultRUeErstattungApproxBis().longValue());
        Assert.assertEquals((long)(55135), berechnung.getResultBeitragSummeApproxEcht().longValue());
        Assert.assertEquals((long)(124730), berechnung.getResultBeitragSummeApproxGesamt().longValue());
*/        
        //Tom-Test
        System.out.println("#ID Tom-Test");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragVersPGeburtsjahr(1970);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2020);
        berechnung.setVertragEndeJahrBeitrag(2020);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)100));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(2);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
//        Assert.assertEquals((long)(28863), berechnung.getErgebnis().getRueErstattungApprox().longValue());
             
        //Brunnert (Student, EBZ)
        System.out.println("#ID Brunnert");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1978);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(1996);
        berechnung.setVertragEndeJahr(2043);
        berechnung.setVertragEndeJahrBeitrag(2043);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)10.2258));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.06));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)6597.199));
        berechnung.setVertragLeistungUnfalltodArt(1);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)6597.199));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)3358.95));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-11-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
//        Assert.assertEquals((long)(4804), berechnung.getErgebnis().getRueErstattungApprox().longValue());

    }

    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testBerechneFall_VKI_Testseries_AT() {
        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();        
        Berechnung berechnung = new Berechnung();
                
        //#1;1;1960;0;;2008;2033;1905;2;70;;12;0;1;;1;30288;0;0;0;;4295,25;42186;;;0;0;;;1;6720;;;;;;;;;;;;;;;;;
        System.out.println("#ID 1 (AT)");
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2033);
        berechnung.setVertragEndeJahrBeitrag(2033);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)70.));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)30288.));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)4295.25));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2015-07-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungJahr(0);   
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)6720));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(6014), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        
        //9;1;1980;0;;2001;2040;1905;2;72,67;;12;0,04;1;;1;28506,96;0;0;0;;14026,39;42309;;;0;0;;;1;17310,7;;;;;;;;;;;;;;;;;
        System.out.println("#ID 9 (AT)");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1980);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2001);
        berechnung.setVertragEndeJahr(2040);
        berechnung.setVertragEndeJahrBeitrag(2040);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)72.67));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.04));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)28506.96));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)14026.39));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2015-11-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)17310.70));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(21014), berechnung.getErgebnis().getRueErstattungApprox().longValue());
      
        //34;1;1981;0;;2001;2031;1905;2;72,67;;12;0,05;1;2006;1;2515,5;0;0;0;;13474,56;42461;;;0;0;;;1;15464,46;;;;;;;;;;;;;;;;;
        System.out.println("#ID 34 (AT)");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1981);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2001);
        berechnung.setVertragEndeJahr(2031);
        berechnung.setVertragEndeJahrBeitrag(2031);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)72.67));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(2006);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)2515.5));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)13474.56));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-04-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)15464.46));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(20293), berechnung.getErgebnis().getRueErstattungApprox().longValue());
     
        //41;0;1981;0;;2002;2041;2041;2;25;;12;0,04;1;;1;6507,07;0;0;1;;;;;;1;4707,54;42552;2016;1;5487,56;;;;;;;;;;;;;;;;;
        System.out.println("#ID 41 (AT)");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragVersPGeburtsjahr(1981);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2002);
        berechnung.setVertragEndeJahr(2041);
        berechnung.setVertragEndeJahrBeitrag(2041);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)25));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.04));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(2016);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)6507.07));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)4707.54));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-07-01"));
        berechnung.setVertragKuendigungJahr(2016);   
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)5487.56));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(6418), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        System.out.println("#ID Rhythmustest (AT)");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)8.6666666666666667)); //100.*1.04/12.
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(2);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)862.03));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(998), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        Assert.assertEquals((long)(820), berechnung.getErgebnis().getBeitragSummeApproxEcht().longValue());
    
        System.out.println("#ID Einmalbeitrag (AT)");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)10000.0)); //
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.valueOf((double)10000.0));
        berechnung.setVertragBeitragPeriodischZahlweise(-1);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)0));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)10000.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(13282), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        Assert.assertEquals((long)(9615), berechnung.getErgebnis().getBeitragSummeApproxEcht().longValue());
        
        //ID Gernot-Sup-Test
        System.out.println("#ID Suptest (AT)");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(2000);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(2030);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)100));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)15000.0));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-01"));
        berechnung.setVertragKuendigungJahr(2016);   
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(24362), berechnung.getErgebnis().getRueErstattungApprox().longValue());

    }

    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testBerechneFall_VKI_Testseries_DE() {
        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();        
        Berechnung berechnung = new Berechnung();
                
        //#1;1;1960;0;;2008;2033;1905;2;70;;12;0;1;;1;30288;0;0;0;;4295,25;42186;;;0;0;;;1;6720;;;;;;;;;;;;;;;;;
        System.out.println("#ID 1");
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2033);
        berechnung.setVertragEndeJahrBeitrag(2033);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)70.));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)30288.));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)4295.25));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2015-07-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungJahr(0);   
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)6720));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(6314), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        
        //9;1;1980;0;;2001;2040;1905;2;72,67;;12;0,04;1;;1;28506,96;0;0;0;;14026,39;42309;;;0;0;;;1;17310,7;;;;;;;;;;;;;;;;;
        System.out.println("#ID 9");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1980);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2001);
        berechnung.setVertragEndeJahr(2040);
        berechnung.setVertragEndeJahrBeitrag(2040);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)72.67));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.04));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)28506.96));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)14026.39));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2015-11-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)17310.70));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(21886), berechnung.getErgebnis().getRueErstattungApprox().longValue());
      
        //34;1;1981;0;;2001;2031;1905;2;72,67;;12;0,05;1;2006;1;2515,5;0;0;0;;13474,56;42461;;;0;0;;;1;15464,46;;;;;;;;;;;;;;;;;
        System.out.println("#ID 34");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1981);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeginnJahr(2001);
        berechnung.setVertragEndeJahr(2031);
        berechnung.setVertragEndeJahrBeitrag(2031);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)72.67));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(2006);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)2515.5));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)13474.56));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-04-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)15464.46));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(21107), berechnung.getErgebnis().getRueErstattungApprox().longValue());
     
        //41;0;1981;0;;2002;2041;2041;2;25;;12;0,04;1;;1;6507,07;0;0;1;;;;;;1;4707,54;42552;2016;1;5487,56;;;;;;;;;;;;;;;;;
        System.out.println("#ID 41");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragVersPGeburtsjahr(1981);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2002);
        berechnung.setVertragEndeJahr(2041);
        berechnung.setVertragEndeJahrBeitrag(2041);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)25));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.04));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(2016);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)6507.07));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)4707.54));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-07-01"));
        berechnung.setVertragKuendigungJahr(2016);   
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)5487.56));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(6683), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        System.out.println("#ID Rhythmustest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)8.6666666666666667)); //100.*1.04/12.
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(2);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)862.03));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(1041), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        Assert.assertEquals((long)(853), berechnung.getErgebnis().getBeitragSummeApproxEcht().longValue());
    
        System.out.println("#ID Einmalbeitrag");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)10000.0)); //
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.valueOf((double)10000.0));
        berechnung.setVertragBeitragPeriodischZahlweise(-1);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)0));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)10000.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(13856), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        Assert.assertEquals((long)(10000), berechnung.getErgebnis().getBeitragSummeApproxEcht().longValue());
        
        //ID Gernot-Sup-Test
        System.out.println("#ID Suptest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(2000);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(2030);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)100));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)15000.0));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-01"));
        berechnung.setVertragKuendigungJahr(2016);   
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(25655), berechnung.getErgebnis().getRueErstattungApprox().longValue());
    }

    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testBerechneFall_Rhythmustest_AT() {
        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();        
        Berechnung berechnung = new Berechnung();
        
        System.out.println("#ID Rhythmustest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)8.6666666666666667)); //100.*1.04/12.
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(2);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)862.03));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(998), berechnung.getErgebnis().getRueErstattungApprox().longValue());
//        Assert.assertEquals((long)(995), berechnung.getResultBeitragSummeApproxEcht().longValue());
    
        System.out.println("#ID Einmalbeitrag");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)10000.0)); //
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.valueOf((double)10000.0));
        berechnung.setVertragBeitragPeriodischZahlweise(-1);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)0));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)10000.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
//        Assert.assertEquals((long)(13142), berechnung.getErgebnis().getRueErstattungApprox().longValue());
//        Assert.assertEquals((long)(9615), berechnung.getResultBeitragSummeApproxEcht().longValue());
        
        //ID Gernot-Sup-Test
        System.out.println("#ID Suptest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(2000);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(2030);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)100));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)15000.0));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-01"));
        berechnung.setVertragKuendigungJahr(2016);   
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
//        Assert.assertEquals((long)(24192), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        
        //ID Wendland-Test
        System.out.println("#ID Wendlandtest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1956);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(1999);
        berechnung.setVertragEndeJahr(2019);
        berechnung.setVertragEndeJahrBeitrag(2019);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)2220.95));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(2); //fixed
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)654551.26));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);   
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)426730.36));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2013-09-01"));
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)379782.39));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(408058), berechnung.getErgebnis().getRueErstattungApprox().longValue());

    }

    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testBerechneFall_Rhythmustest_DE() {
        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();        
        Berechnung berechnung = new Berechnung();
        
        System.out.println("#ID Rhythmustest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)8.6666666666666667)); //100.*1.04/12.
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(2);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)862.03));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(1041), berechnung.getErgebnis().getRueErstattungApprox().longValue());
    
        System.out.println("#ID Einmalbeitrag");
        berechnung.reset();    
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)10000.0)); //
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.valueOf((double)10000.0));
        berechnung.setVertragBeitragPeriodischZahlweise(-1);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)0));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-01"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)10000.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
//        Assert.assertEquals((long)(13142), berechnung.getErgebnis().getRueErstattungApprox().longValue());
//        Assert.assertEquals((long)(9615), berechnung.getResultBeitragSummeApproxEcht().longValue());
        
        //ID Gernot-Sup-Test
        System.out.println("#ID Suptest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(2000);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(2030);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)100));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)15000.0));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-01"));
        berechnung.setVertragKuendigungJahr(2016);   
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
//        Assert.assertEquals((long)(24192), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        
        //ID Wendland-Test
        System.out.println("#ID Wendlandtest");
        berechnung.reset();
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragVersPGeburtsjahr(1956);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeginnJahr(1999);
        berechnung.setVertragEndeJahr(2019);
        berechnung.setVertragEndeJahrBeitrag(2019);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)2220.95));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragLeistungTodArt(2); //fixed
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)654551.26));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigung(1);   
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)426730.36));
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2013-09-01"));
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)379782.39));
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(432833), berechnung.getErgebnis().getRueErstattungApprox().longValue());

    }
    
    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testPathofaelle() {
        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();        
        Berechnung berechnung = new Berechnung();
        
        //DE1;50;2.5;2;classic;1995;2020;2010;0;;;;;single_payment_fixed_amount;10000;premium_release;;1926;female;;;DE
        System.out.println("#ID DE1"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)50.0)); //100.*1.04/12.
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.025));
        berechnung.setVertragBeitragDynamikRhythmus(2);
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(1995);
        berechnung.setVertragEndeJahr(2020);
        berechnung.setVertragEndeJahrBeitrag(2010);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)10000.0));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragVersPGeburtsjahr(1926);
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(13140), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //DE2;75.23;5;1;not_sure;2000;2015;;1;2010-03-31;5030.50;;;not_sure;;none;;2000;male;12568.96;;DE
        System.out.println("#ID DE2"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)75.23)); 
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1); keine Ahnung (-1)
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2015);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2010-03-31"));
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)5030.50));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(-1);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(null);
        berechnung.setVertragVersPGeburtsjahr(2000);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)12568.96));
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(1);
        //berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(15512), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //DE3;100;1;0;fonds;2005;2030;;1;2016-09-30;;;;single_payment_contract_balance;;premium_release_annuity;486.27;1980;male;;;DE
        System.out.println("#ID DE3"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)100.0)); 
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.01));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(2005);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-30"));
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(3);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)486.27));
        berechnung.setVertragVersPGeburtsjahr(1980);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(13243), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //DE4;300;;;fonds;2000;2030;2020;0;;;35145.76;2016-08-31;none;;annuity;1500;1942;female;37200.24;;DE
        System.out.println("#ID DE4"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)300.0)); 
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.00));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(2020);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)35145.76));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-31"));
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(2);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)1500.0));
        berechnung.setVertragVersPGeburtsjahr(1942);
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)37200.24));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(35448), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //AT1;50;2.5;2;classic;1995;2020;2010;0;;;;;single_payment_fixed_amount;10000;premium_release;;1926;female;;5427.90;AT
        System.out.println("#ID AT1");          
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)50.0)); 
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.025));
        berechnung.setVertragBeitragDynamikRhythmus(2);
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(1995);
        berechnung.setVertragEndeJahr(2020);
        berechnung.setVertragEndeJahrBeitrag(2010);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)10000.0));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragVersPGeburtsjahr(1926);
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.valueOf((double)5427.90));
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(24117), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //AT2;75.23;5;1;not_sure;2000;2015;;1;2010-03-31;5030.50;;;not_sure;;none;;2000;male;12568.96;;AT
        System.out.println("#ID AT2"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)75.23)); 
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.05));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragTyp(-1); //klassisch (0) oder fondsgebunden (1); keine Ahnung (-1)
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2015);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2010-03-31"));
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)5030.50));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(-1);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragVersPGeburtsjahr(2000);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)12568.96));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(14205), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //AT3;100;1;0;fonds;2005;2030;;1;2016-09-30;;;;single_payment_contract_balance;;premium_release_annuity;486.27;1980;male;;;AT
        System.out.println("#ID AT3"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)100.0)); 
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.01));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(2005);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-30"));
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(3);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)486.27));
        berechnung.setVertragVersPGeburtsjahr(1980);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(12591), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //AT4;300;;;fonds;2000;2030;2020;0;;;35145.76;2016-08-31;none;;annuity;1500;1942;female;37200.24;;AT
        System.out.println("#ID AT4"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)300.0)); 
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.00));
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(2020);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)35145.76));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-31"));
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(2);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)1500.0));
        berechnung.setVertragVersPGeburtsjahr(1942);
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)37200.24));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(32420), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        //AT5;;;;fonds;2000;2030;;0;;;;;none;;none;;2000;male;;20000;AT;-1
        System.out.println("#ID AT5"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(null); 
        berechnung.setVertragBeitragDynamikProzent(null);
        berechnung.setVertragBeitragDynamikRhythmus(0);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2030);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(null);
        berechnung.setVertragVersPGeburtsjahr(2000);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(null);
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.valueOf(20000));
        berechnung.setLandCode(2);
        berechnung.setVertragBeitragPeriodischZahlweise(-1);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(null);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(36612), berechnung.getErgebnis().getRueErstattungApprox().longValue());
        
//VertragBeitragPeriodisch;VertragBeitragDynamikProzent;VertragBeitragDynamikRhythmus;VertragTyp;VertragBeginnJahr;VertragEndeJahr;VertragEndeJahrBeitrag;VertragKuendigung;VertragKuendigungEndeDatum;VertragKuendigungEndeBetrag;VertragAktuellRKWBetrag;VertragAktuellRKWDatum;VertragLeistungTodArt;VertragLeistungTodBetrag;VertragLeistungBUArt;VertragLeistungBUBetrag;VertragVersPGeburtsjahr;VertragVersPGeschlecht;VertragPlausiBeitragssummeBetrag;VertragBeitragAnfangseinlage;LandCode;VertragBeitragPeriodischZahlweise
/*
        System.out.println("#ID DE1"); 
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)0.0)); //100.*1.04/12.
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(2008);
        berechnung.setVertragEndeJahr(2016);
        berechnung.setVertragEndeJahrBeitrag(2016);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse(""));
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-01"));
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragVersPGeburtsjahr(1960);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        Assert.assertEquals((long)(995), berechnung.getErgebnis().getRueErstattungApprox().longValue());

*/
    } 

    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void testPathofaelle2() {
        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();        
        Berechnung berechnung = new Berechnung();
        
        berechnung.reset();
        System.out.println("#ID BE_DE1");
        berechnung.setVertragTyp(0);
        berechnung.setVertragBeginnJahr(new Integer("1995"));
        berechnung.setVertragEndeJahr(new Integer("2020"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("50"));
        berechnung.setVertragPlausiBeitragssummeBetrag(null);
        berechnung.setVertragEndeJahrBeitrag(new Integer("2010"));
        berechnung.setVertragBeitragDynamikProzent(new BigDecimal("0.25"));
        berechnung.setVertragBeitragDynamikRhythmus(new Integer("2"));
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(new BigDecimal("10000"));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(null);
        berechnung.setVertragVersPGeburtsjahr(new Integer("1926"));
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(1);
        berechnung.setVertragIsVerbundeneLeben(Boolean.TRUE);
        berechnung.setVertragPartnerGeburtsjahr(new Integer("1940"));
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(new Integer("2000"));
        berechnung.setVertragBeitragPeriodischReduktionEuro(new BigDecimal("40"));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(null);
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_DE2");
        berechnung.setVertragTyp(-1);
        berechnung.setVertragBeginnJahr(new Integer("2000"));
        berechnung.setVertragEndeJahr(new Integer("2015"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("75.23"));
        berechnung.setVertragPlausiBeitragssummeBetrag(new BigDecimal("12568.96"));
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragBeitragDynamikProzent(new BigDecimal("5"));
        berechnung.setVertragBeitragDynamikRhythmus(new Integer("1"));
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2010-03-31"));
        berechnung.setVertragKuendigungEndeBetrag(new BigDecimal("5030.50"));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(-1);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(null);
        berechnung.setVertragVersPGeburtsjahr(new Integer("2000"));
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(1);
        berechnung.setVertragIsVerbundeneLeben(Boolean.TRUE);
        berechnung.setVertragPartnerGeburtsjahr(new Integer("1990"));
        berechnung.setVertragBeitragPeriodischZahlweise(4);
        berechnung.setVertragBeitragDynamikKuendigungJahr(new Integer("2007"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(null);
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(null);
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_DE3");
        berechnung.setVertragTyp(1);
        berechnung.setVertragBeginnJahr(new Integer("2005"));
        berechnung.setVertragEndeJahr(new Integer("2030"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("100"));
        berechnung.setVertragPlausiBeitragssummeBetrag(null);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragBeitragDynamikProzent(new BigDecimal("1"));
        berechnung.setVertragBeitragDynamikRhythmus(new Integer("0"));
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-30"));
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(3);
        berechnung.setVertragLeistungBUBetrag(new BigDecimal("486.27"));
        berechnung.setVertragVersPGeburtsjahr(new Integer("1980"));
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(1);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeitragPeriodischZahlweise(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(new Integer("2012"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(new Integer("2010"));
        berechnung.setVertragBeitragPeriodischReduktionEuro(new BigDecimal("1500"));
        berechnung.setVertragLeistungUnfalltodArt(2);
        berechnung.setVertragLeistungUnfalltodBetrag(new BigDecimal("15000"));
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_DE4");
        berechnung.setVertragTyp(1);
        berechnung.setVertragBeginnJahr(new Integer("2000"));
        berechnung.setVertragEndeJahr(new Integer("2030"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("300"));
        berechnung.setVertragPlausiBeitragssummeBetrag(new BigDecimal("37200.24"));
        berechnung.setVertragEndeJahrBeitrag(new Integer("2020"));
        berechnung.setVertragBeitragDynamikProzent(null);
        berechnung.setVertragBeitragDynamikRhythmus(null);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(new BigDecimal("35145.76"));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-31"));
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(2);
        berechnung.setVertragLeistungBUBetrag(new BigDecimal("1500"));
        berechnung.setVertragVersPGeburtsjahr(new Integer("1942"));
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(1);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeitragPeriodischZahlweise(2);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(null);
        berechnung.setVertragLeistungUnfalltodArt(-1);
        berechnung.setVertragLeistungUnfalltodBetrag(null);
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_AT1");
        berechnung.setVertragTyp(0);
        berechnung.setVertragBeginnJahr(new Integer("1995"));
        berechnung.setVertragEndeJahr(new Integer("2020"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("50"));
        berechnung.setVertragPlausiBeitragssummeBetrag(null);
        berechnung.setVertragEndeJahrBeitrag(new Integer("2010"));
        berechnung.setVertragBeitragDynamikProzent(new BigDecimal("2.5"));
        berechnung.setVertragBeitragDynamikRhythmus(new Integer("2"));
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(new BigDecimal("10000"));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(null);
        berechnung.setVertragVersPGeburtsjahr(new Integer("1926"));
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragBeitragAnfangseinlage(new BigDecimal("5427.90"));
        berechnung.setLandCode(2);
        berechnung.setVertragIsVerbundeneLeben(Boolean.TRUE);
        berechnung.setVertragPartnerGeburtsjahr(new Integer("1940"));
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(new Integer("2000"));
        berechnung.setVertragBeitragPeriodischReduktionEuro(new BigDecimal("40"));
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(null);
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_AT2");
        berechnung.setVertragTyp(-1);
        berechnung.setVertragBeginnJahr(new Integer("2000"));
        berechnung.setVertragEndeJahr(new Integer("2015"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("365.28"));
        berechnung.setVertragPlausiBeitragssummeBetrag(new BigDecimal("12568.96"));
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragBeitragDynamikProzent(new BigDecimal("5"));
        berechnung.setVertragBeitragDynamikRhythmus(new Integer("1"));
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2010-03-31"));
        berechnung.setVertragKuendigungEndeBetrag(new BigDecimal("5030.50"));
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(-1);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(null);
        berechnung.setVertragVersPGeburtsjahr(new Integer("2000"));
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(2);
        berechnung.setVertragIsVerbundeneLeben(Boolean.TRUE);
        berechnung.setVertragPartnerGeburtsjahr(new Integer("1990"));
        berechnung.setVertragBeitragPeriodischZahlweise(4);
        berechnung.setVertragBeitragDynamikKuendigungJahr(new Integer("2007"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(null);
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(null);
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_AT3");
        berechnung.setVertragTyp(1);
        berechnung.setVertragBeginnJahr(new Integer("2005"));
        berechnung.setVertragEndeJahr(new Integer("2030"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("1200"));
        berechnung.setVertragPlausiBeitragssummeBetrag(null);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragBeitragDynamikProzent(new BigDecimal("1"));
        berechnung.setVertragBeitragDynamikRhythmus(new Integer("0"));
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2016-09-30"));
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(3);
        berechnung.setVertragLeistungBUBetrag(new BigDecimal("486.27"));
        berechnung.setVertragVersPGeburtsjahr(new Integer("1980"));
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(2);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeitragPeriodischZahlweise(1);
        berechnung.setVertragBeitragDynamikKuendigungJahr(new Integer("2012"));
        berechnung.setVertragBeitragPeriodischReduktionJahr(new Integer("2010"));
        berechnung.setVertragBeitragPeriodischReduktionEuro(new BigDecimal("1500"));
        berechnung.setVertragLeistungUnfalltodArt(2);
        berechnung.setVertragLeistungUnfalltodBetrag(new BigDecimal("15000"));
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_AT4");
        berechnung.setVertragTyp(1);
        berechnung.setVertragBeginnJahr(new Integer("2000"));
        berechnung.setVertragEndeJahr(new Integer("2030"));
        berechnung.setVertragBeitragPeriodisch(new BigDecimal("1800"));
        berechnung.setVertragPlausiBeitragssummeBetrag(new BigDecimal("37200.24"));
        berechnung.setVertragEndeJahrBeitrag(new Integer("2020"));
        berechnung.setVertragBeitragDynamikProzent(null);
        berechnung.setVertragBeitragDynamikRhythmus(null);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(new BigDecimal("35145.76"));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2016-08-31"));
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(2);
        berechnung.setVertragLeistungBUBetrag(new BigDecimal("1500"));
        berechnung.setVertragVersPGeburtsjahr(new Integer("1942"));
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragBeitragAnfangseinlage(null);
        berechnung.setLandCode(2);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeitragPeriodischZahlweise(2);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(null);
        berechnung.setVertragLeistungUnfalltodArt(-1);
        berechnung.setVertragLeistungUnfalltodBetrag(null);
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
        berechnung.reset();
        System.out.println("#ID BE_AT5");
        berechnung.setVertragTyp(1);
        berechnung.setVertragBeginnJahr(new Integer("2000"));
        berechnung.setVertragEndeJahr(new Integer("2030"));
        berechnung.setVertragBeitragPeriodisch(null);
        berechnung.setVertragPlausiBeitragssummeBetrag(null);
        berechnung.setVertragEndeJahrBeitrag(null);
        berechnung.setVertragBeitragDynamikProzent(null);
        berechnung.setVertragBeitragDynamikRhythmus(null);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(0);
        berechnung.setVertragLeistungTodBetrag(null);
        berechnung.setVertragLeistungBUArt(0);
        berechnung.setVertragLeistungBUBetrag(null);
        berechnung.setVertragVersPGeburtsjahr(new Integer("2000"));
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragBeitragAnfangseinlage(new BigDecimal("20000"));
        berechnung.setLandCode(2);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeitragPeriodischZahlweise(null);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(null);
        berechnung.setVertragLeistungUnfalltodArt(2);
        berechnung.setVertragLeistungUnfalltodBetrag(new BigDecimal("50000"));
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
        //Assert.assertEquals((long)(17841), berechnung.getResultRUeErstattungApprox().longValue());
        
    }

    /**
     * Test of berechne method, of class BerechnungRUeGrobKernel.
     */
    @Test
    public void test_Einzel() {
        BerechnungRUeGrobKernel kernel = new BerechnungRUeGrobKernel();        
        Berechnung berechnung = new Berechnung();
        
        //DE1;50;2.5;2;classic;1995;2020;2010;0;;;;;single_payment_fixed_amount;10000;premium_release;;1926;female;;;DE
/*
        System.out.println("#ID Einzeltest"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)50.0)); //100.*1.04/12.
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.025));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(1995);
        berechnung.setVertragEndeJahr(2020);
        berechnung.setVertragEndeJahrBeitrag(2010);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(null);
        berechnung.setVertragKuendigungEndeBetrag(null);
        berechnung.setVertragAktuellRKWBetrag(null);
        berechnung.setVertragAktuellRKWDatum(null);
        berechnung.setVertragLeistungTodArt(2);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)10000.0));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragVersPGeburtsjahr(1926);
        berechnung.setVertragVersPGeschlecht(Boolean.FALSE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(0);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
    //    Assert.assertEquals((long)(17841), berechnung.getErgebnis().getRueErstattungApprox().longValue());
*/
        System.out.println("#ID Einzeltest"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)1000.0)); //100.*1.04/12.
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.00002));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragTyp(0); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(2000);
        berechnung.setVertragEndeJahr(2020);
        berechnung.setVertragEndeJahrBeitrag(2020);
        berechnung.setVertragKuendigung(0);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2014-02-01"));
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)27786.1));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)27786.1));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2014-02-01"));
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragVersPGeburtsjahr(1968);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.ZERO);
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.ZERO);
//        berechnung.setVertragBeitragPeriodischReduktionJahr(2009);
//        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.valueOf((double)1.0));
        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
    //    Assert.assertEquals((long)(17841), berechnung.getErgebnis().getRueErstattungApprox().longValue());

        System.out.println("#ID Einzeltest/Pseftelis"); 
        berechnung.reset();
        berechnung.setVertragBeitragPeriodisch(BigDecimal.valueOf((double)153.39)); //100.*1.04/12.
        berechnung.setVertragBeitragDynamikProzent(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragDynamikRhythmus(1);
        berechnung.setVertragTyp(1); //klassisch (0) oder fondsgebunden (1)
        berechnung.setVertragBeginnJahr(1997);
        berechnung.setVertragEndeJahr(2028);
        berechnung.setVertragEndeJahrBeitrag(2028);
        berechnung.setVertragKuendigung(1);
        berechnung.setVertragKuendigungEndeDatum(LocalDate.parse("2013-06-01"));
        berechnung.setVertragKuendigungEndeBetrag(BigDecimal.valueOf((double)27786.1));
        berechnung.setVertragAktuellRKWBetrag(BigDecimal.valueOf((double)27786.1));
        berechnung.setVertragAktuellRKWDatum(LocalDate.parse("2013-06-01"));
        berechnung.setVertragLeistungTodArt(1);
        berechnung.setVertragLeistungTodBetrag(BigDecimal.valueOf((double)20000.));
        berechnung.setVertragLeistungBUArt(1);
        berechnung.setVertragLeistungBUBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragVersPGeburtsjahr(1968);
        berechnung.setVertragVersPGeschlecht(Boolean.TRUE);
        berechnung.setVertragPlausiBeitragssummeBetrag(BigDecimal.valueOf((double)0.0));
        berechnung.setVertragBeitragAnfangseinlage(BigDecimal.valueOf((double)283.79));
        berechnung.setLandCode(1);
        berechnung.setVertragBeitragPeriodischZahlweise(12);
        //Erweiterte Felder
        berechnung.setVertragLeistungUnfalltodArt(0);
        berechnung.setVertragLeistungUnfalltodBetrag(BigDecimal.valueOf((double)0.0));
//        berechnung.setVertragIsVerbundeneLeben(Boolean.TRUE);
//        berechnung.setVertragPartnerGeburtsjahr(1972);
        berechnung.setVertragIsVerbundeneLeben(Boolean.FALSE);
        berechnung.setVertragPartnerGeburtsjahr(null);
        berechnung.setVertragBeitragDynamikKuendigungJahr(null);
        berechnung.setVertragBeitragPeriodischReduktionJahr(2004);
        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.valueOf((double)0.0));
//        berechnung.setVertragBeitragPeriodischReduktionJahr(2009);
//        berechnung.setVertragBeitragPeriodischReduktionEuro(BigDecimal.valueOf((double)1.0));
//        berechnung.setVertragKuendigungJahr(0);   
        //Berechnung ausführen und Ergebnisse auslesen
        berechnung = kernel.berechne(berechnung);
        //Ergebnisse pürfen
    //    Assert.assertEquals((long)(17841), berechnung.getErgebnis().getRueErstattungApprox().longValue());
    }
    
    
}
