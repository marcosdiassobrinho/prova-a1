package br.unitins.topicos1.resources;

import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.dto.AdicionarComentarioPerfilRequestDto;
import br.unitins.topicos1.dto.ComentariosResponseDto;
import br.unitins.topicos1.dto.CriaUsuarioDePerfilDto;
import br.unitins.topicos1.exception.ServiceException;
import br.unitins.topicos1.domain.service.PerfilService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource {
    @Inject
    PerfilService perfilService;

    @PUT
    @Path("/usuario")
    public Response atualizarUsuario(CriaUsuarioDePerfilDto dto) {
        perfilService.atualizarUsuarioPerfil(dto);
        return Response.ok().build();
    }


    @PATCH
    @Path("/{id}")
    public Response finalizarCadastro(@PathParam("id") Long id) {
        perfilService.ativarPerfil(id);
        return Response.ok().build();
    }


    @GET
    @Path("comentarios/{id}")
    public Response buscarComentarios(@PathParam("id") Long id) {
        try {
            List<ComentariosResponseDto> comentarios = perfilService.buscarComentarios(id);
            return Response.ok(comentarios).build();
        } catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("comentarios/{id}")
    public Response adicionarComentario(@PathParam("id") Long id, AdicionarComentarioPerfilRequestDto dto) {
        try {
            perfilService.adicionarComentario(dto, id);
            return Response.ok().build();
        }catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }
    @DELETE
    @Path("comentarios/{id}")
    public Response removerComentario(@PathParam("id") Long id){
        try {
            perfilService.removerComentario(id);
            return Response.ok().build();
        }catch (ServiceException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }
    @GET
    public Perfil buscarPerfil(@QueryParam("id") Long idPerfil){
        return perfilService.buscarPorId(idPerfil);
    }
}
