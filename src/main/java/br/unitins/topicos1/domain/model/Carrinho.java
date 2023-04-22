package br.unitins.topicos1.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private Perfil perfil;

    @OneToMany(mappedBy = "carrinho")
    private List<CarrinhoVariacao> carrinhoVariacoes;

}
