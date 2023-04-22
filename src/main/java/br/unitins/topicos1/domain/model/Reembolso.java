package br.unitins.topicos1.domain.model;


import br.unitins.topicos1.domain.model.Compra;
import br.unitins.topicos1.domain.model.enums.StatusReembolso;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reembolso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusReembolso statusReembolso;
    @OneToOne
    Compra compra;
    private Double valor;

}
