package br.unitins.topicos1.dto;

import br.unitins.topicos1.domain.model.Variacao;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarrinhoResponseDto {
    Long idAnuncio;
    String nomeAnuncio;
    String nomeProduto;
    Double preco = 0.0;
    Integer quantidade = 0;
    List<Variacao> variacoes;

    public CarrinhoResponseDto(Long idAnuncio, String nomeAnuncio, String nomeProduto) {
        this.idAnuncio = idAnuncio;
        this.nomeAnuncio = nomeAnuncio;
        this.nomeProduto = nomeProduto;
    }
}
