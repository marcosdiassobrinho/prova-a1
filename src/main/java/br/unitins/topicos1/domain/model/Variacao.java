package br.unitins.topicos1.domain.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "variacao")
public class Variacao {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String descricao;
    private Double peso;
    private boolean usado;
    @Column(name = "valor_bruto")
    private Double valorBruto;

    @Column(name = "valor_liquido")
    private Double valorLiquido;

    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

}
