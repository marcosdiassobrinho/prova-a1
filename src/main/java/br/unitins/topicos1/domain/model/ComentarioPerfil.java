package br.unitins.topicos1.domain.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comentario_perfil")
public class ComentarioPerfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
    private String comentario;
    @CreationTimestamp
    @Column(name = "data_comentario")
    private LocalDateTime dataComentario;
    private boolean denunciado;

    @PrePersist
    public void prePersist() {
        this.dataComentario = LocalDateTime.now();
    }
}
