package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.Marca;
import br.unitins.topicos1.exception.MarcaNaoEncontradaException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {

    public Marca findByNome(String nome) {
        return find("nome", nome).firstResultOptional()
                .orElseThrow(NotFoundException::new);
    }

}
