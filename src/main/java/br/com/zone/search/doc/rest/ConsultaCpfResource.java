package br.com.zone.search.doc.rest;

import br.com.zone.search.doc.entidades.ConstaObito;
import br.com.zone.search.doc.entidades.PessoaFisica;
import br.com.zone.search.doc.services.ConsultaCpfService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author daniel
 */
@Path("/service")
public class ConsultaCpfResource {

    @Inject
    private ConsultaCpfService service;

    @GET
    @Path("/{cpf}/{nascimento}")
    @Produces("application/json; charset=UTF-8")
    public Response buscar(@PathParam("cpf") String cpf, @PathParam("nascimento") String nascimento) {

        PessoaFisica pf = service.buscarPessoa(cpf, nascimento, null);

        if (pf != null && pf.getNumeroCpf() != null && !pf.getNumeroCpf().isEmpty()) {

            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new AnnotationExclusionStrategy())
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .registerTypeAdapter(ConstaObito.class, new ConstaObitoDeserializer())
                    .create();

            String json = gson.toJson(pf);
                      
            return Response.ok(json).build();
        }
        
        return Response.status(Status.NOT_FOUND).build();

    }

}
