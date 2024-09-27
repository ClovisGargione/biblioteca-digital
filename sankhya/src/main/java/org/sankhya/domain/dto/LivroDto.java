package org.sankhya.domain.dto;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDto {

    private Integer id;

    private String titulo;

    private String autor;

    @Range(min = 0, max = 10)
    private Integer nota;

    public LivroDto() {
	super();
    }

    public LivroDto(Integer id, String titulo, String autor, @Range(min = 0, max = 10) Integer nota) {
	super();
	this.id = id;
	this.titulo = titulo;
	this.autor = autor;
	this.nota = nota;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getTitulo() {
	return titulo;
    }

    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    public String getAutor() {
	return autor;
    }

    public void setAutor(String autor) {
	this.autor = autor;
    }

    public Integer getNota() {
	return nota;
    }

    public void setNota(Integer nota) {
	this.nota = nota;
    }
}
