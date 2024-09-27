package org.sankhya.repository.facade;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.sankhya.domain.Estante;
import org.sankhya.repository.dao.EstanteDao;

import jakarta.inject.Inject;

public class EstanteFacade extends EstanteAbstract {

    private final static Logger logger = Logger.getLogger(EstanteFacade.class);

    @Inject
    private EstanteDao estanteDao;

    public EstanteFacade() {
	super();
    }

    @Override
    public Optional<Estante> buscarPorId(Integer id) throws Exception {
	Optional<Estante> estanteOpt = Optional.empty();
	try {
	    estanteOpt = estanteDao.buscarPorId(id);
	} catch (Exception e) {
	    logger.error("Não foi possível localizar a estante id: " + id, e);
	    throw e;
	}

	return estanteOpt;
    }

    @Override
    public void adicionar(Estante estante) throws Exception {
	try {
	    estanteDao.adicionar(estante);
	    logger.info("Estante cadastrada com sucesso id: " + estante.getId());
	} catch (Exception e) {
	    logger.error("Não foi possível cadastrar a estante", e);
	    throw e;
	}
    }

    @Override
    public void remover(Estante estante) throws Exception {
	try {
	    estanteDao.remover(estante);
	    logger.info("Estante removida com sucesso id: " + estante.getId());
	} catch (Exception e) {
	    logger.error("Não foi possível remover a estante id:" + estante.getId(), e);
	    throw e;
	}
    }

    @Override
    public void alterar(Estante estante) throws Exception {
	try {
	    estanteDao.alterar(estante);
	    logger.info("Estante atualizada com sucesso id: " + estante.getId());
	} catch (Exception e) {
	    logger.error("Não foi possível atualizar a estante id: " + estante.getId(), e);
	    throw e;
	}
    }
}
