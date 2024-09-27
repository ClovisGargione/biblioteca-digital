package org.sankhya.repository.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.sankhya.domain.Usuario;
import org.sankhya.repository.interfaces.IUsuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class UsuarioDAO implements IUsuario {
	
	private final static Logger logger = Logger.getLogger(UsuarioDAO.class);
		
	@PersistenceContext
    private EntityManager entityManager;
    
    public UsuarioDAO() {
    	super();
    }

	@Override
	public Optional<Usuario> buscarPorId(Integer id) {
		Usuario usuario = entityManager.find(Usuario.class, id);
        return Optional.ofNullable(usuario);
	}

	@Override
	public Optional<Usuario> buscarPorLogin(String login) {
		Optional<Usuario> usuarioOpt = Optional.empty();
		try {
			TypedQuery<Usuario> query = entityManager.createQuery("select u from usuario u where u.login = :login", Usuario.class);
	        query.setParameter("login", login);
	        usuarioOpt = Optional.ofNullable(query.getSingleResult());
		} catch(NoResultException e) {
			logger.info("Usuário não encontrado");
		}
        return usuarioOpt;
	}

	@Override
	public Optional<List<Usuario>> usuarios() {
		TypedQuery<Usuario> usuarios = entityManager.createQuery("select u from usuario u",Usuario.class);
        return Optional.ofNullable(usuarios.getResultList());
	}

	@Override
	@Transactional
	public void adicionar(Usuario usuario) {
		entityManager.persist(usuario);
	}

	@Override
	@Transactional
	public void remover(Usuario usuario) {
		entityManager.remove(usuario);
	}

	@Override
	@Transactional
	public void alterar(Usuario usuario) {
		entityManager.merge(usuario);
	}
}
