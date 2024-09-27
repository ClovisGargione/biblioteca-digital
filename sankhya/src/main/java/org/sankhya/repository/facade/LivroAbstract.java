package org.sankhya.repository.facade;

import java.util.Optional;

import org.sankhya.domain.Livro;

public abstract class LivroAbstract {

    public abstract Optional<Livro> buscarPorId(Integer id) throws Exception;

    public abstract void adicionar(Livro livro) throws Exception;

    public abstract void remover(Integer id) throws Exception;

    public abstract void alterar(Livro livro) throws Exception;
}
