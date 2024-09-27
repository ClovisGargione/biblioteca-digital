package org.sankhya.endpoint;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.sankhya.auth.AuthUtils;
import org.sankhya.domain.Estante;
import org.sankhya.domain.Livro;
import org.sankhya.domain.Usuario;
import org.sankhya.domain.dto.LivroDto;
import org.sankhya.repository.facade.LivroFacade;
import org.sankhya.repository.facade.UsuarioFacade;

import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/secure/livro")
public class LivroRest {

    private static final Logger logger = Logger.getLogger(LivroRest.class);

    @Inject
    private LivroFacade livroFacade;

    @Inject
    private UsuarioFacade usuarioFacade;

    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLivrosPorUsuario(@Context HttpServletRequest request) {
	List<Livro> livros = Collections.emptyList();
	try {
	    Usuario usuario = getUsuarioPorToken(request);
	    if (nonNull(usuario.getEstante())) {
		livros = usuario.getEstante().getLivros();
	    }
	    return Response.ok().entity(livros).build();
	} catch (Exception e) {
	    logger.error("Nao foi possivel buscar a lista de livros", e);
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		.entity("Não foi possível buscar a lista de livros").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLivro(@PathParam("id") Integer id) {
	try {
	    Optional<Livro> livroOpt = livroFacade.buscarPorId(id);
	    if (livroOpt.isEmpty()) {
		logger.error("Não foi localizar o livro id: " + id);
		throw new Exception("Não foi localizar o livro id: " + id);
	    }
	    Livro livro = livroOpt.get();
	    return Response.ok().entity(livro).build();
	} catch (Exception e) {
	    logger.error("Não foi possivel localizar o livro id: " + id, e);
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		.entity("Não foi possivel localizar o livro id: " + id).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionarLivro(LivroDto livroDto, @Context HttpServletRequest request) {
	try {
	    Usuario usuario = getUsuarioPorToken(request);
	    if (isNull(usuario.getEstante())) {
		Estante estante = new Estante();
		estante.setUsuario(usuario);
		usuario.setEstante(estante);
	    }
	    Livro livro = new Livro(livroDto.getTitulo(), livroDto.getAutor(), livroDto.getNota());
	    usuario.getEstante().adicionar(livro);
	    usuarioFacade.alterar(usuario);
	    return Response.ok().entity(livro).build();
	} catch (Exception e) {
	    logger.error("Não foi possivel cadastrar o livro: " + livroDto.getTitulo(), e);
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		.entity("Não foi possivel cadastrar o livro: " + livroDto.getTitulo()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarLivro(LivroDto livroDto) {
	try {
	    Optional<Livro> livroOpt = livroFacade.buscarPorId(livroDto.getId());
	    Livro livro = livroOpt.get();
	    livro.setAutor(livroDto.getAutor());
	    livro.setTitulo(livroDto.getTitulo());
	    livro.setNota(livroDto.getNota());
	    livroFacade.alterar(livro);
	    return Response.ok().entity(livro).build();
	} catch (Exception e) {
	    logger.error("Não foi possivel atualizar o livro: " + livroDto.getTitulo(), e);
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		.entity("Não foi possivel cadastrar o livro: " + livroDto.getTitulo()).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerLivro(LivroDto livroDto) {
	try {
	    livroFacade.remover(livroDto.getId());
	    return Response.ok().entity("Livro removido com sucesso").build();
	} catch (Exception e) {
	    logger.error("Não foi possivel remover o livro: " + livroDto.getTitulo(), e);
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		.entity("Não foi possivel remover o livro: " + livroDto.getTitulo()).build();
    }

    @GET
    @Path("/tem-livros")
    @Produces(MediaType.APPLICATION_JSON)
    public Response temLivros(@Context HttpServletRequest request) {
	try {
	    Usuario usuario = getUsuarioPorToken(request);
	    boolean temLivros = false;
	    if (nonNull(usuario.getEstante()) && usuario.getEstante().temLivros()) {
		temLivros = true;
	    }
	    return Response.ok().entity(temLivros).build();
	} catch (Exception e) {
	    logger.error("Não foi possivel localizar a estante de livros", e);
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		.entity("Não foi possivel localizar a estante de livros").build();
    }

    private Usuario getUsuarioPorToken(HttpServletRequest request) throws Exception {
	try {
	    String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
	    JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
	    String id = (String) claimSet.getSubject();
	    Optional<Usuario> usuarioOpt = usuarioFacade.buscarPorId(Integer.valueOf(id));
	    if (usuarioOpt.isEmpty()) {
		logger.error("Não foi localizar o usuário id: " + id);
		throw new Exception("Não foi localizar o usuário id: " + id);
	    }
	    return usuarioOpt.get();
	} catch (Exception e) {
	    throw e;
	}
    }

}
