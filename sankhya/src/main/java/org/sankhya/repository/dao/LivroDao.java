package org.sankhya.repository.dao;

import java.util.List;
import java.util.Optional;

import org.sankhya.domain.Livro;
import org.sankhya.repository.interfaces.ILivro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class LivroDao implements ILivro {

    @PersistenceContext
    private EntityManager entityManager;

    public LivroDao() {
	super();
    }

    @Override
    public Optional<Livro> buscarPorId(Integer id) {
	Livro livro = entityManager.find(Livro.class, id);
	return Optional.ofNullable(livro);
    }

    @Override
    public Optional<List<Livro>> livros() {
	TypedQuery<Livro> livros = entityManager.createQuery("select l from livro l", Livro.class);
	return Optional.ofNullable(livros.getResultList());
    }

    @Override
    @Transactional
    public void adicionar(Livro livro) {
	entityManager.persist(livro);
    }

    @Override
    @Transactional
    public void remover(Livro livro) {
	entityManager.remove(entityManager.contains(livro) ? livro : entityManager.merge(livro));
    }

    @Override
    @Transactional
    public void alterar(Livro livro) {
	entityManager.merge(livro);
    }

}
