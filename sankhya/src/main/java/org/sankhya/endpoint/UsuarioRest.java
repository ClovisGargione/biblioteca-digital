package org.sankhya.endpoint;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.sankhya.auth.AuthUtils;
import org.sankhya.auth.PasswordService;
import org.sankhya.domain.Usuario;
import org.sankhya.domain.dto.UsuarioDto;
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

@Path("/secure/usuario")
public class UsuarioRest {
	
	private static final Logger logger = Logger.getLogger(UsuarioRest.class);
	
	@Inject
    private UsuarioFacade usuarioFacade;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("id") Integer id) {
		try {
			Optional<Usuario> usuario = usuarioFacade.buscarPorId(id);
			return Response.ok().entity(usuario.orElse(null)).build();
		} catch (Exception e) {
			logger.error("Nao foi possivel buscar o usuário:" + e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível encontrar o usuário").build();
	}
	
	@GET
	@Path("{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorLogin(@PathParam("login") String login) {
		try {
			Optional<Usuario> usuario = usuarioFacade.buscarPorLogin(login);
			return Response.ok().entity(usuario.orElse(null)).build();
		} catch (Exception e) {
			logger.error("Nao foi possivel buscar o usuário:" + e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível encontrar o usuário").build();
	}
	
	@GET
	@Path("/token")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorToken(@Context HttpServletRequest request) {
		try {
			String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
	        JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
	        String id = (String) claimSet.getSubject();
	        Optional<Usuario> usuario = usuarioFacade.buscarPorId(Integer.valueOf(id));
			return Response.ok().entity(usuario.orElse(null)).build();
		} catch (Exception e) {
			logger.error("Nao foi possivel buscar o usuário:" + e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível encontrar o usuário").build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUsuario(UsuarioDto usuarioDto) {
		try {
			Usuario usuario = new Usuario(null, usuarioDto.getLogin(), usuarioDto.getNome(), PasswordService.hashPassword(usuarioDto.getSenha()));
			usuarioFacade.adicionar(usuario);
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("Nao foi possivel cadastrar o usuário:" + e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível cadastrar o usuário").build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarUsuario(UsuarioDto usuarioDto) {
		try {
			Optional<Usuario> usuarioOpt = usuarioFacade.buscarPorId(usuarioDto.getId());
			if(usuarioOpt.isPresent()) {
				Usuario usuario = usuarioOpt.get();
				usuario.setLogin(usuarioDto.getLogin());
				usuario.setNome(usuarioDto.getNome());
				if(Objects.nonNull(usuarioDto.getSenha())) {
					usuario.setSenha(PasswordService.hashPassword(usuarioDto.getSenha()));
				}
				usuarioFacade.alterar(usuario);
				return Response.ok().entity(usuario).build();
			}
		} catch (Exception e) {
			logger.error("Nao foi possivel atualizar o usuário:" + e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível atualizar o usuário").build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removerUsuario(UsuarioDto usuarioDto) {
		try {
			Optional<Usuario> usuarioOpt = usuarioFacade.buscarPorId(usuarioDto.getId());
			if(usuarioOpt.isPresent()) {
				Usuario usuario = usuarioOpt.get();
				usuarioFacade.remover(usuario);
				return Response.ok().build();
			}
		} catch (Exception e) {
			logger.error("Nao foi possivel remover o usuário:" + e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível remover o usuário").build();
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarios() {
		try {
		List<Usuario> usuario = usuarioFacade.usuarios();
		return Response.ok().entity(usuario).build();
		} catch (Exception e) {
			logger.error("Nao foi possivel buscar a lista de usuários:" + e.getMessage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível buscar a lista de usuários").build();
	}

}
