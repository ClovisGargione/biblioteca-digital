package org.sankhya.repository.interfaces;

import java.util.List;
import java.util.Optional;

import org.sankhya.domain.Usuario;

public interface IUsuario {

    Optional<Usuario> buscarPorId(Integer id);

    Optional<Usuario> buscarPorLogin(String login);

    Optional<List<Usuario>> usuarios();

    void adicionar(Usuario usuario);

    void remover(Usuario usuario);

    void alterar(Usuario usuario);
}
