package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.dto.*;
import br.unitins.topicos1.exception.LoginConflitoException;
import br.unitins.topicos1.domain.model.Login;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.repository.LoginRepository;
import br.unitins.topicos1.domain.repository.PerfilRepository;
import br.unitins.topicos1.exception.LoginNaoEncontradoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class LoginService {
    @Inject
    LoginRepository loginRepository;
    @Inject
    PerfilRepository perfilRepository;

    @Transactional
    public CriarLoginResponseDto criarLogin(CriarLoginDto dto) {
        if (!dto.isMaiorIdade()) {
            throw new LoginConflitoException("Usuario menor de idade.");
        }
        if (loginRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new LoginConflitoException("Login já existe.");
        }
        Login login = new Login();
        login.setLogin(dto.getLogin());
        login.setEmail(dto.getEmail());
        login.setPassword(dto.getSenha());
        loginRepository.persist(login);
        Perfil perfil = new Perfil();
        perfil.setLogin(login);
        perfilRepository.persist(perfil);

        return new CriarLoginResponseDto(login.getLogin(), login.getEmail());
    }

    @Transactional
    public AlterarLoginResponseDto alterarSenha(AlterarSenhaDto dto) {
        Login login = loginRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new LoginNaoEncontradoException(String.format("Não foi encontrado o login: %s.", dto.getLogin())));

        if (!login.getPassword().equals(dto.getSenhaAntiga())) {
            throw new LoginConflitoException("Senha digitada incorreta.");
        }
        login.setPassword(dto.getSenhaNova());
        loginRepository.persist(login);

        return new AlterarLoginResponseDto(login.getLogin(), login.getEmail());
    }

    @Transactional
    public AlterarLoginResponseDto alteraEmail(AtualizarEmailDto dto) {
        Login login = loginRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new LoginNaoEncontradoException(String.format("Não foi encontrado o login: %s.", dto.getLogin())));


        login.setEmail(dto.getEmail());
        loginRepository.persist(login);

        return new AlterarLoginResponseDto(login.getLogin(), login.getEmail());
    }

    @Transactional
    public void excluirLogin(LoginDto dto) {
        Login login = loginRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new LoginNaoEncontradoException(String.format("Não foi encontrado o login: %s.", dto.getLogin())));
        if (!login.getPassword().equals(dto.getPassword())) {
            throw new LoginConflitoException("Senha digitada incorreta!");
        }
        Perfil perfil = perfilRepository.buscarPerfilPorLogin(login);
        perfilRepository.delete(perfil);
        loginRepository.deleteById(login.getId());
    }
}
