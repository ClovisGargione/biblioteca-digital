package org.sankhya.repository.facade;

import java.util.Optional;

import org.sankhya.domain.Estante;

public abstract class EstanteAbstract {

    public abstract Optional<Estante> buscarPorId(Integer id) throws Exception;

    public abstract void adicionar(Estante estante) throws Exception;

    public abstract void remover(Estante estante) throws Exception;

    public abstract void alterar(Estante estante) throws Exception;

}
