package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.Reembolso;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReembolsoRepository implements PanacheRepository<Reembolso> {
}