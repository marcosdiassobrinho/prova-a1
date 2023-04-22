package br.unitins.topicos1.dto;

import lombok.Value;

@Value
public class AdicionarComentarioPerfilRequestDto {
    Long idUsuario;
    String Comentario;
}
