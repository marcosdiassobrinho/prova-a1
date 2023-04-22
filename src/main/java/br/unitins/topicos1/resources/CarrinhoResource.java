package br.unitins.topicos1.resources;

import br.unitins.topicos1.domain.repository.CarrinhoDto;
import br.unitins.topicos1.domain.service.CarrinhoService;
import br.unitins.topicos1.exception.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/carrinho")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarrinhoResource {
    @Inject
    CarrinhoService carrinhoService;

    @POST
    public Response adicionarProduto(CarrinhoDto dto) {
        try {
            carrinhoService.adicionar(dto);
            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    public Response buscar(@QueryParam("idPerfil") Long idPerfil) {
        try {
            return Response.ok(carrinhoService.buscarResponse(idPerfil)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

}
