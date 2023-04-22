package br.unitins.topicos1.domain.model;

import br.unitins.topicos1.domain.model.enums.StatusParcela;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer numero;

    private double valor;

    private LocalDateTime dataVencimento;

    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    @Enumerated(EnumType.STRING)
    private StatusParcela status;
}
