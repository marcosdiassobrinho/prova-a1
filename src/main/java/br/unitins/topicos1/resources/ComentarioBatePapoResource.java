package br.unitins.topicos1.resources;

import br.unitins.topicos1.domain.service.ComentarioBatePapoService;
import br.unitins.topicos1.dto.ComentarioBatePapoDto;
import br.unitins.topicos1.exception.ServiceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/comentario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ComentarioBatePapoResource {
    @Inject
    ComentarioBatePapoService comentarioBatePapoService;

    @POST
    public Response criarComentario(@QueryParam("id") Long batePapoId, ComentarioBatePapoDto dto) {
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(comentarioBatePapoService.salvar(batePapoId, dto)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }
    @GET
    @Path("/{id}")
    public Response buscarComentario(@PathParam("id") Long idComentario, ComentarioBatePapoDto dto) {
        try {
            return Response.ok(comentarioBatePapoService.buscarComentarioBatePapo(idComentario)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

}
