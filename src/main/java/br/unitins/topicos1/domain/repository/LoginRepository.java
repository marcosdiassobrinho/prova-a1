package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.Login;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped

public class LoginRepository implements PanacheRepository<Login> {

    public Optional<Login> findByLogin(String login) {
        return find("login", login).firstResultOptional();
    }
}
