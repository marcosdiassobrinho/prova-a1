package br.unitins.topicos1.dto;

import lombok.Value;

@Value
public class CriarLoginDto {
    String login;
    String senha;
    String email;

    boolean maiorIdade;

}
