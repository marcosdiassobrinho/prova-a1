package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class VariacaoNaoEncontradoException extends ServiceException {
    public VariacaoNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
