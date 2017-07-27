package br.com.zone.search.doc.rest;

import br.com.zone.search.doc.entidades.ConstaObito;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author daniel
 */
public class ConstaObitoDeserializer implements JsonSerializer<ConstaObito>, JsonDeserializer<ConstaObito> {

    @Override
    public JsonElement serialize(ConstaObito src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getDescricao());
    }

    @Override
    public ConstaObito deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        ConstaObito consta = null;

        if (json != null && json.getAsString() != null && !json.getAsString().isEmpty()) {
            consta = ConstaObito.buscarPorDescricao(json.getAsString());
        }

        return consta;
    }
    
}
