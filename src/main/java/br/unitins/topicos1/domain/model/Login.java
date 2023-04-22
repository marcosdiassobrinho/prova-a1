package br.unitins.topicos1.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Login {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "login", nullable = false, length = 117)
    private String login;

    @Column(name = "password", nullable = false, length = 17)
    private String password;

    @Column(name = "email", nullable = false, length = 117)
    private String email;
}
