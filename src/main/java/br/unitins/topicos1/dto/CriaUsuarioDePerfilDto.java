package br.unitins.topicos1.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Value
public class CriaUsuarioDePerfilDto {
    @NotNull
    Long loginId;
    @NotNull
    String nome;
    @NotNull
    String cpf;
    @NotNull
    Date dataDeNascimento;
}
