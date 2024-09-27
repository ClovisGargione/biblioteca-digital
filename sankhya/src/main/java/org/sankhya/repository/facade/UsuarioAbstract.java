package org.sankhya.repository.facade;

import java.util.List;
import java.util.Optional;

import org.sankhya.domain.Usuario;

public abstract class UsuarioAbstract {

    public abstract void adicionar(Usuario usuario) throws Exception;

    public abstract void alterar(Usuario usuario) throws Exception;

    public abstract void remover(Usuario usuario) throws Exception;

    public abstract List<Usuario> usuarios() throws Exception;

    public abstract Optional<Usuario> buscarPorId(Integer id) throws Exception;

    public abstract Optional<Usuario> buscarPorLogin(String login) throws Exception;
}
