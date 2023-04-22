package br.unitins.topicos1.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "anuncio")
public class Anuncio {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    String titulo;
    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
    private boolean ativo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @Transient
    private Integer quantidadeVendida;

}
