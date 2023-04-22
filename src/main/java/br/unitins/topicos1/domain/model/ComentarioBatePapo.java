package br.unitins.topicos1.domain.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentario_bate_papo")
@Data
public class ComentarioBatePapo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "bate_papo_id")
    @ManyToOne
    private BatePapo batePapo;
    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    private String comentario;

    private boolean denunciado;

    private boolean vendedor;

    @CreationTimestamp
    private LocalDateTime dataCriacao;
}