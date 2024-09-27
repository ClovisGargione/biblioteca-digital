package org.sankhya.repository.facade;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.sankhya.domain.Usuario;
import org.sankhya.repository.dao.UsuarioDAO;

import jakarta.inject.Inject;

public class UsuarioFacade extends UsuarioAbstract {

    private final static Logger logger = Logger.getLogger(UsuarioFacade.class);

    @Inject
    private UsuarioDAO usuarioDao;

    public UsuarioFacade() {
	super();
    }

    @Override
    public void adicionar(Usuario usuario) throws Exception {
	try {
	    Optional<Usuario> u = usuarioDao.buscarPorLogin(usuario.getLogin());
	    if (u.isPresent()) {
		logger.error("Usuário já cadastrado");
		throw new Exception("Usuário já cadastrado");
	    }
	    usuarioDao.adicionar(usuario);
	    logger.info("Usuário adicionado com sucesso: " + usuario.getLogin());
	} catch (Exception e) {
	    logger.error("Nao foi possivel cadastrar o usuario " + usuario.getLogin());
	    throw e;
	}
    }

    @Override
    public void alterar(Usuario usuario) throws Exception {
	try {
	    Optional<Usuario> u = usuarioDao.buscarPorId(usuario.getId());
	    if (u.isEmpty()) {
		logger.error("Usuário não cadastrado: " + usuario.getLogin());
		throw new Exception("Usuário não cadastrado: " + usuario.getLogin());
	    }
	    usuarioDao.alterar(usuario);
	    logger.info("Usuário atualizado com sucesso: " + usuario.getLogin());
	} catch (Exception e) {
	    logger.error("Nao foi possivel atualizar o usuario " + usuario.getLogin());
	    throw e;
	}
    }

    @Override
    public void remover(Usuario usuario) throws Exception {
	try {
	    Optional<Usuario> u = usuarioDao.buscarPorId(usuario.getId());
	    if (u.isEmpty()) {
		logger.error("Usuário não localizado: " + usuario.getLogin());
		throw new Exception("Usuário não localizado: " + usuario.getLogin());
	    }
	    usuarioDao.remover(usuario);
	    logger.info("Usuário removido com sucesso " + usuario.getLogin());
	} catch (Exception e) {
	    logger.error("Nao foi possivel remover o usuario " + usuario.getLogin());
	    throw e;
	}
    }

    @Override
    public List<Usuario> usuarios() throws Exception {
	Optional<List<Usuario>> usuarios = Optional.empty();
	try {
	    usuarios = usuarioDao.usuarios();
	} catch (Exception e) {
	    logger.error("Não foi possível obter a lista de usuários");
	    throw e;
	}
	return usuarios.orElse(Collections.emptyList());
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) throws Exception {
	Optional<Usuario> usuario = Optional.empty();
	try {
	    usuario = usuarioDao.buscarPorId(id);
	} catch (Exception e) {
	    logger.error("Não foi possível obter o usuário id: " + id);
	    throw e;
	}
	return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) throws Exception {
	Optional<Usuario> usuario = Optional.empty();
	try {
	    usuario = usuarioDao.buscarPorLogin(login);
	} catch (Exception e) {
	    logger.error("Não foi possível obter o usuário: " + login);
	    throw e;
	}
	return usuario;
    }
}
