package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class PagamentoNaoEncontradoException extends ServiceException {
    public PagamentoNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
