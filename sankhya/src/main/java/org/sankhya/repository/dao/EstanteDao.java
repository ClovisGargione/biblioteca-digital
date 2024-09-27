package org.sankhya.repository.dao;

import java.util.Optional;

import org.sankhya.domain.Estante;
import org.sankhya.repository.interfaces.IEstante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class EstanteDao implements IEstante {
		
	@PersistenceContext
    private EntityManager entityManager;
	
	public EstanteDao() {
		super();
	}

	@Override
	public Optional<Estante> buscarPorId(Integer id) {
		Estante estante = entityManager.find(Estante.class, id);
		return Optional.ofNullable(estante);
	}

	@Override
	@Transactional
	public void adicionar(Estante estante) {
		entityManager.persist(estante);

	}

	@Override
	@Transactional
	public void remover(Estante estante) {
		entityManager.remove(estante);

	}

	@Override
	@Transactional
	public void alterar(Estante estante) {
		entityManager.merge(estante);

	}
}
