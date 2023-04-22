package br.unitins.topicos1.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "carrinho_variacao")
@Data
@NoArgsConstructor
public class CarrinhoVariacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Carrinho carrinho;
    @ManyToOne
    private Variacao variacao;
    @ManyToOne
    private Anuncio anuncio;

    private Integer quantidade;

    public CarrinhoVariacao(Carrinho carrinho, Variacao variacao, Integer quantidade, Anuncio anuncio) {
        this.carrinho = carrinho;
        this.variacao = variacao;
        this.anuncio = anuncio;
        this.quantidade = quantidade;
    }

}
