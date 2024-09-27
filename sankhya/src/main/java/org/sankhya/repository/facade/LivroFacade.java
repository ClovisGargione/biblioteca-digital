package org.sankhya.repository.facade;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.sankhya.domain.Livro;
import org.sankhya.repository.dao.LivroDao;

import jakarta.inject.Inject;

public class LivroFacade extends LivroAbstract {

    private final static Logger logger = Logger.getLogger(LivroFacade.class);

    @Inject
    private LivroDao livroDao;

    public LivroFacade() {
	super();
    }

    @Override
    public Optional<Livro> buscarPorId(Integer id) throws Exception {
	Optional<Livro> livroOpt = Optional.empty();
	try {
	    livroOpt = livroDao.buscarPorId(id);
	} catch (Exception e) {
	    logger.error("Não foi possível localizar o livro id: " + id, e);
	    throw e;
	}
	return livroOpt;
    }

    @Override
    public void adicionar(Livro livro) throws Exception {
	try {
	    livroDao.adicionar(livro);
	    logger.info("Livro cadastrado com sucesso: " + livro.getTitulo());
	} catch (Exception e) {
	    logger.error("Não foi possível cadastrar o livro: " + livro.getTitulo(), e);
	    throw e;
	}
    }

    @Override
    public void remover(Integer id) throws Exception {
	try {
	    Optional<Livro> livroOpt = livroDao.buscarPorId(id);
	    if (livroOpt.isEmpty()) {
		logger.error("Livro não localizado id: " + id);
		throw new Exception("Livro não localizado id: " + id);
	    }
	    livroDao.remover(livroOpt.get());
	    logger.info("Livro removido com sucesso " + livroOpt.get().getTitulo());
	} catch (Exception e) {
	    logger.error("Não foi possível removido o livro id: " + id, e);
	    throw e;
	}

    }

    @Override
    public void alterar(Livro livro) throws Exception {
	try {
	    Optional<Livro> livroOpt = livroDao.buscarPorId(livro.getId());
	    if (livroOpt.isEmpty()) {
		logger.error("Livro não cadastrado: " + livro.getTitulo());
		throw new Exception("Livro não cadastrado: " + livro.getTitulo());
	    }
	    livroDao.alterar(livro);
	    logger.info("Livro atualizado com sucesso: " + livro.getTitulo());
	} catch (Exception e) {
	    logger.error("Não foi possível atualizar o livro: " + livro.getTitulo(), e);
	    throw e;
	}
    }

}
