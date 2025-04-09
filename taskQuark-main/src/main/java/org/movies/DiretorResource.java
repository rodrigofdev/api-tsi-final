package org.movies;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/diretores")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiretorResource {

    @GET
    public List<Diretor> listarTodos() {
        return Diretor.listAll();
    }

    @GET
    @Path("/{id}")
    public Diretor buscarPorId(@PathParam("id") Long id) {
        return Diretor.findById(id);
    }

    @POST
    @Transactional
    public Response criar(Diretor diretor) {
        diretor.persist();
        return Response.status(Response.Status.CREATED).entity(diretor).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Diretor diretorAtualizado) {
        Diretor diretorExistente = Diretor.findById(id);
        if (diretorExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        diretorExistente.nome = diretorAtualizado.nome;

        return Response.ok(diretorExistente).build();
    }
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Diretor.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        throw new NotFoundException("Diretor não encontrado com o ID: " + id);
    }
}
