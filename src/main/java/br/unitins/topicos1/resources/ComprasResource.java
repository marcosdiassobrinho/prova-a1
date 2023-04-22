package br.unitins.topicos1.resources;

import br.unitins.topicos1.domain.service.AvaliacaoCompraService;
import br.unitins.topicos1.domain.service.CompraService;
import br.unitins.topicos1.dto.AvaliacaoCompraDto;
import br.unitins.topicos1.dto.CompraResponseDto;
import br.unitins.topicos1.dto.CompraDto;
import br.unitins.topicos1.exception.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ComprasResource {
    @Inject
    CompraService compraService;
    @Inject
    AvaliacaoCompraService avaliacaoCompraService;

    @POST
    public Response criarCompra(CompraDto dto) {
        try {
            compraService.realizarTransacao(dto);
            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    public Response buscarCompras(@QueryParam("idPerfil") Long idPerfil) {
        List<CompraResponseDto> listaCompras = compraService.buscarCompras(idPerfil);
        return Response.status(listaCompras.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaCompras)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarCompra(@PathParam("id") Long idCompra) {
        try {
            return Response.ok().entity(compraService.buscarCompra(idCompra)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/{id}")
    public Response criarAvaliacao(@PathParam("id") Long idCompra, AvaliacaoCompraDto dto) {
        try {
            avaliacaoCompraService.criarAvaliacaoCompra(dto, idCompra);
            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }
}

//    @PATCH
//    @Path("/{idCompra}/pagar-parcela")
//    public Response pagarParcela(@PathParam("idCompra") Long idCompra, @QueryParam("idPerfil") Long idPerfil) {
//        try {
//            transacaoService.atualizarStatusParcela(idCompra, idPerfil);
//            return Response.ok().build();
//        } catch (ServiceException e) {
//            return Response.status(e.getResponse().getStatus())
//                    .entity(e.getMessage())
//                    .build();
//        }
//    }
//
//    @PATCH
//    @Path("{idCompra}/pagar-compra")
//    public Response pagarCompra(@PathParam("idCompra") Long idCompra, @QueryParam("idPerfil") Long idPerfil, StatusPagamento statusPagamento) {
//        try {
//            transacaoService.atualizarStatusPagamento(idCompra, idPerfil, statusPagamento);
//            return Response.ok().build();
//        } catch (ServiceException e) {
//            return Response.status(e.getResponse().getStatus())
//                    .entity(e.getMessage())
//                    .build();
//        }
//    }


