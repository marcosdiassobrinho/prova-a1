package br.unitins.topicos1.dto;

import lombok.Value;

@Value
public class LoginUsuarioDto {
    String nome;
    String senha;
    String email;
    boolean maiorIdade;
}
