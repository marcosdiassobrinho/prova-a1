package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class CarrinhoNaoEncontradoException extends ServiceException {
    public CarrinhoNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
