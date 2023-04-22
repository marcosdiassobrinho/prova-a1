package br.unitins.topicos1.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "bate_papo")
public class BatePapo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "perfil_comprador")
    private Perfil perfilComprador;

    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    @OneToMany(mappedBy = "batePapo", cascade = CascadeType.ALL)
    private List<ComentarioBatePapo> comentarios;
}
