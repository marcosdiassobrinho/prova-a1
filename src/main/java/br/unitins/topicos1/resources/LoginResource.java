package br.unitins.topicos1.resources;

import br.unitins.topicos1.dto.*;
import br.unitins.topicos1.exception.LoginException;
import br.unitins.topicos1.domain.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
    @Inject
    LoginService loginService;

    @POST
    public Response criarLogin(CriarLoginDto dto) {
        try {
            CriarLoginResponseDto responseDto = loginService.criarLogin(dto);
            return Response.ok(responseDto).build();
        } catch (LoginException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PATCH
    public Response atualizarSenha(AlterarSenhaDto dto) {
        try {
            AlterarLoginResponseDto alterarSenhaDto = loginService.alterarSenha(dto);
            return Response.ok(alterarSenhaDto).build();
        } catch (LoginException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PATCH
    public Response atualizarEmail(AtualizarEmailDto dto) {
        try {
            AlterarLoginResponseDto alterarSenhaDto = loginService.alteraEmail(dto);
            return Response.ok(alterarSenhaDto).build();
        } catch (LoginException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    public Response excluirConta(LoginDto dto) {
        try {
            loginService.excluirLogin(dto);
        } catch (LoginException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
