package br.unitins.topicos1.dto;

import lombok.Value;
import org.jboss.logging.Logger;

@Value
public class AlterarSenhaDto {
    String login;
    String senhaNova;
    String senhaAntiga;
}
