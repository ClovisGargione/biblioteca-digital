package org.sankhya.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    private String nome;

    @JsonIgnore
    private String senha;

    @OneToOne(mappedBy = "usuario", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JsonBackReference
    private Estante estante;

    public Usuario() {
	super();
    }

    public Usuario(Integer id, String login, String nome, String senha) {
	super();
	this.id = id;
	this.login = login;
	this.nome = nome;
	this.senha = senha;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

    public Estante getEstante() {
	return estante;
    }

    public void setEstante(Estante estante) {
	this.estante = estante;
    }

}
