package de.drschade.lebensversicherung.grobrechner.gui.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("currencyConverter")
public class CurrencyConverter implements Converter {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String stringValue) {

        if (null == stringValue || stringValue.isEmpty()) {
            return null;
        }

        // Fall 666.50
        if (stringValue.lastIndexOf(".") != -1 && stringValue.lastIndexOf(",") == -1) {
            if (stringValue.lastIndexOf(".")+3 == stringValue.length()) {
                stringValue = stringValue.replaceAll("\\.", ",");
            }
        }
        
        BigDecimal currencyValue = null;

        try {
            NumberFormat fmt = NumberFormat.getNumberInstance(Locale.GERMANY);

            // By default, fmt.parse() returns a Number that is a Double.
            // However, some decimals, such as 0.10, cannot be represented
            // exactly in floating point, so it is considered best practice to
            // use BigDecimal for storing monetary values.
            ((DecimalFormat) fmt).setParseBigDecimal(true);
            return (BigDecimal) fmt.parse(stringValue);

        } catch (Exception e) {
            logger.info("no Currency String " + stringValue);
        }

        try {
            currencyValue = new BigDecimal(stringValue.trim());
            logger.info("Parse BigDecimal" + stringValue);
        } catch (NumberFormatException e) {
            logger.error("Error in parsing to BigDecimal" + stringValue);
        }

        return currencyValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object currencyValue) {

        if (null == currencyValue) {
            return "";
        }

        try {
            NumberFormat fmt = NumberFormat.getNumberInstance(Locale.GERMANY);
            return fmt.format(((BigDecimal) currencyValue).doubleValue());
        } catch (Exception e) {
            logger.error("Cant format to currency" + currencyValue);
        }

        return currencyValue.toString();
    }

}
