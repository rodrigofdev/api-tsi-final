package org.movies;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/atores")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtorResource {

    @GET
    public List<Ator> listarTodos() {
        return Ator.listAll();
    }

    @GET
    @Path("/{id}")
    public Ator buscarPorId(@PathParam("id") Long id) {
        return Ator.findById(id);
    }

    @POST
    @Transactional
    public Response criar(Ator ator) {
        ator.persist();
        return Response.status(Response.Status.CREATED).entity(ator).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Ator atorAtualizado) {
        Ator atorExistente = Ator.findById(id);
        if (atorExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        atorExistente.nome = atorAtualizado.nome;
        atorExistente.genero = atorAtualizado.genero;

        return Response.ok(atorExistente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Ator.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
