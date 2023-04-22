package br.unitins.topicos1.domain.repository;

import lombok.Value;

@Value
public class CarrinhoDto {
    Long perfilId;
    Long variacaoId;
    Integer quantidade;
}
