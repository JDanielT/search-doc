package br.edu.ufca.search.doc.rest;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author daniel
 */
public class LocalDateDeserializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String retVal = fmt.format(src);
        return new JsonPrimitive(retVal);
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        LocalDate data = null;

        if (json != null && json.getAsString() != null
                && !json.getAsString().isEmpty() && !json.getAsString().equals("NÃO")) {

            String text;

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            text = json.toString().replaceAll("\"", "");

            if (text != null) {

                try {
                    data = LocalDate.parse(text, fmt);
                } catch (Exception ex) {
                    fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    if (text.length() >= 10) {
                        data = LocalDate.parse(text.substring(0, 10), fmt);
                    }
                }

            }

        }

        return data;
    }

}
