package br.unitins.topicos1.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "compra")
public class Compra {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @OneToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compra_variacao",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "variacao_id"))
    private List<Variacao> variacao;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    @Column(name = "quantidade_compra")
    private Integer quantidadeCompra;

    @Column(name = "valor")
    private double valor;
}
