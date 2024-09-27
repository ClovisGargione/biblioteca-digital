package org.sankhya.repository.interfaces;

import java.util.List;
import java.util.Optional;

import org.sankhya.domain.Livro;

public interface ILivro {

	Optional<Livro> buscarPorId(Integer id);
	Optional<List<Livro>> livros();
    void adicionar(Livro usuario);
    void remover(Livro usuario);
    void alterar(Livro usuario);
}
