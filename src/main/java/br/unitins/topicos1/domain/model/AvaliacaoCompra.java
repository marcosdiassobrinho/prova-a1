package br.unitins.topicos1.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "avaliacao_compra")
public class AvaliacaoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private boolean recomenda;

    private LocalDateTime dataAvaliacao;

    private boolean vendedor;

    private boolean denunciado;

    private String comentario;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "compra_id")
    private Compra compra;
}
