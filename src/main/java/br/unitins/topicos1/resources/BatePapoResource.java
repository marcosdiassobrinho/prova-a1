package br.unitins.topicos1.resources;

import br.unitins.topicos1.domain.service.BatePapoService;
import br.unitins.topicos1.exception.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/bate-papos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BatePapoResource {
    @Inject
    BatePapoService batePapoService;

    @POST
    public Response criarBatePapo(@QueryParam("perfilId") Long perfilId, @QueryParam("anuncioId") Long anuncioId) {
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(batePapoService.salvar(perfilId, anuncioId)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }

    }
    @GET
    @Path("/{id}")
    public Response buscarBatePapos(@PathParam("id") Long perfilId) {
        try {
            return Response.ok(batePapoService.buscarBatePapos(perfilId)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    public Response buscarBatePapo(@QueryParam("perfilId") Long perfilId, @QueryParam("anuncioId") Long anuncioId) {
        try {
            return Response.ok(batePapoService.buscarBatePapo(perfilId, anuncioId)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

}
