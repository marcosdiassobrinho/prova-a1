package br.unitins.topicos1.dto;

import br.unitins.topicos1.domain.model.enums.FormaPagamento;
import lombok.Value;

import java.util.List;
@Value
public class CompraDto {
    Long idPerfil;
    Long idAnuncio;
    List<Long> idVariacoes;
    FormaPagamento formaPagamento;
    Integer numeroParcelas;
}
