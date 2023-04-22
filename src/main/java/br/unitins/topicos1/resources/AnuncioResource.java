package br.unitins.topicos1.resources;

import br.unitins.topicos1.domain.service.AnuncioService;
import br.unitins.topicos1.dto.*;
import br.unitins.topicos1.exception.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/anuncios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnuncioResource {
    @Inject
    AnuncioService anuncioService;

    @GET
    public Response buscarAnuncios() {
        List<AnunciosResponseDto> listaAnuncios = anuncioService.buscarAnuncios();
        return Response.status(listaAnuncios.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaAnuncios)
                .build();
    }

    @GET
    @Path("{id}/")
    public Response buscarAnuncio(@PathParam("id") Long id) {
        try {
            return Response.ok(anuncioService.buscarDtoPorID(id)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    public Response criarAnuncio(AnuncioDto dto) {
        try {
            return Response.status(Response.Status.CREATED).entity(anuncioService.salvar(dto)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PATCH
    @Path("{id}/")
    public Response atualizarAnuncio(@PathParam("id") Long anuncioId, AtualizarAnuncioDto dto) {
        try {
            return Response.ok(anuncioService.atualizar(anuncioId, dto)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    public Response deletarAnuncio(@QueryParam("anuncioId") Long anuncioId) {
        try {
            anuncioService.deletar(anuncioId);
            return Response.noContent().build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }
}
