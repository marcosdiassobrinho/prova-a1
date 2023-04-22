package br.unitins.topicos1.domain.model;

import br.unitins.topicos1.domain.model.enums.StatusPerfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "login_id")
    private Login login;
    @Transient
    private Long reputacao;
    @JsonIgnore
    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_perfil")
    private StatusPerfil statusPerfil = StatusPerfil.INATIVO;
    @OneToOne
    private Usuario usuario;
    @OneToOne
    private Carrinho carrinho;
}