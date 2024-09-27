package org.sankhya.endpoint;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.sankhya.auth.AuthUtils;
import org.sankhya.auth.PasswordService;
import org.sankhya.auth.Token;
import org.sankhya.domain.Usuario;
import org.sankhya.domain.dto.LoginDto;
import org.sankhya.domain.dto.UsuarioDto;
import org.sankhya.repository.facade.UsuarioFacade;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AutenticacaoRest {

	private static final Logger logger = Logger.getLogger(AutenticacaoRest.class);
    public static final String LOGING_ERROR_MSG = "Login ou senha incorretos";
    
    @Inject
    private UsuarioFacade usuarioFacade;
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object login(LoginDto informacoesLogin, @Context HttpServletRequest request) {
        try {
            Optional<Usuario> foundUser = usuarioFacade.buscarPorLogin(informacoesLogin.getLogin());

            if(foundUser.isPresent() && PasswordService.checkPassword(informacoesLogin.getSenha(), foundUser.get().getSenha())){
            	Usuario usuario = foundUser.get();

                Token token = AuthUtils.createToken(request.getRemoteHost(), usuario.getId(),  usuario.getLogin());

                return Response.ok().entity(token).build();
            }

        } catch(Exception e) {
            logger.error("Nao foi possivel fazer o login do usuario "+informacoesLogin.getLogin()+":"+e);
        }

        logger.info("Usuario "+informacoesLogin.getLogin()+" tentou efetuar o login");

        return Response.status(Response.Status.UNAUTHORIZED).entity(LOGING_ERROR_MSG).build();
    }
    
    @POST
    @Path("signup")
    public Response signup(@Valid UsuarioDto informacoesUsuario, @Context HttpServletRequest request) throws Exception{
        try {
    	Usuario usuario = new Usuario();
        usuario.setLogin(informacoesUsuario.getLogin());
        usuario.setNome(informacoesUsuario.getNome());
        usuario.setSenha(PasswordService.hashPassword(informacoesUsuario.getSenha()));
        usuarioFacade.adicionar(usuario);
        Token token = AuthUtils.createToken(request.getRemoteHost(), usuario.getId(),  usuario.getLogin());
        logger.info("Usuário "+usuario.getLogin() + " efetuou login");
        return Response.ok().entity(token).build();
        } catch(Exception e) {
        	logger.error("Nao foi possivel cadastrar o usuario "+ informacoesUsuario.getLogin()+": "+e);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Nao foi possivel cadastrar o usuario").build();
    }
}
