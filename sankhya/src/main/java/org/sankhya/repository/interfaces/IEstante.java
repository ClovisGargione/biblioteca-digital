package org.sankhya.repository.interfaces;

import java.util.Optional;

import org.sankhya.domain.Estante;

public interface IEstante {

    Optional<Estante> buscarPorId(Integer id);

    void adicionar(Estante estante);

    void remover(Estante estante);

    void alterar(Estante estante);
}
