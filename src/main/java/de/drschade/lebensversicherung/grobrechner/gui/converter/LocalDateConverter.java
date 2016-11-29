package de.drschade.lebensversicherung.grobrechner.gui.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("localDateTimeConverter")
public class LocalDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String stringValue) {

        if (null == stringValue || stringValue.isEmpty()) {
            return null;
        }

        LocalDate localDate;

        try {
            localDate = LocalDate.parse(
                    stringValue,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        } catch (DateTimeParseException e) {

            throw new ConverterException("Falsches Format");
        }

        return localDate;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object localDateValue) {

        if (null == localDateValue) {
            return "";           
        }

        return ((LocalDate) localDateValue).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
