package br.unitins.topicos1.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Getter
@Setter
public class BatePapoResponseDto {
    Long id;
    String nomeVendedor;
    String nomeComprador;
    String tituloAnuncio;
    List<ComentarioBatePapoResponseDto> comentarioBatePapoResponse;

    public BatePapoResponseDto(Long id, String nomeVendedor, String nomeComprador, String tituloAnuncio) {
        this.id = id;
        this.nomeVendedor = nomeVendedor;
        this.nomeComprador = nomeComprador;
        this.tituloAnuncio = tituloAnuncio;
    }
}
