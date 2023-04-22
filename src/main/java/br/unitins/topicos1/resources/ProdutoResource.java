package br.unitins.topicos1.resources;

import br.unitins.topicos1.domain.service.ProdutoService;
import br.unitins.topicos1.domain.service.VariacaoService;
import br.unitins.topicos1.dto.ProdutoDto;
import br.unitins.topicos1.dto.AtualizarVariacaoRequestDto;
import br.unitins.topicos1.dto.VariacaoRequestDto;
import br.unitins.topicos1.exception.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/produto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    @Inject
    VariacaoService variacaoService;
    @Inject
    ProdutoService produtoService;

    @PATCH
    public Response atualizarProduto(@QueryParam("idAnuncio") Long idAnuncio, ProdutoDto dto) {
        try {
            return Response.ok().entity(produtoService.atualizar(dto, idAnuncio)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("variacoes/")
    public Response criarVariacao(@QueryParam("idProduto") Long idProduto, VariacaoRequestDto dto) {
        try {
            return Response.ok().entity(produtoService.adicionarVariacaoProduto(dto, idProduto)).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PATCH
    @Path("variacoes/{id}")
    public void atualizarVariacao(@PathParam("id") Long variacaoId, AtualizarVariacaoRequestDto dto) {
        variacaoService.atualizarVariacao(variacaoId, dto);
    }

    @DELETE
    @Path("variacoes/{id}")
    public Response deletarVariacao(@PathParam("id") Long variacaoId, @QueryParam("idProduto") Long idProduto) {
        try {
            variacaoService.deletarVariacao(variacaoId, idProduto);
            return Response.noContent().build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }
}


