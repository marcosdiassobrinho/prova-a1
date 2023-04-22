package br.unitins.topicos1.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;
    private String rua;
    private String Bairro;

    private String estado;

    private String cidade;

}
