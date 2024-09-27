package org.sankhya.auth;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.HttpMethod;



public class AuthFilter implements Filter {

	private static final String AUTH_ERROR_MSG = "Certifique-se de que sua solicitação tenha um cabeçalho de autorização",
			EXPIRE_ERROR_MSG = "Token expirado", JWT_ERROR_MSG = "Não foi possível analisar o JWT";
	private static final String INVALIDTOKEN_ERROR_MSG = "Token inválido";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String authHeader = httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY);

		if (!httpRequest.getMethod().equals(HttpMethod.OPTIONS)) {

			if (StringUtils.isBlank(authHeader) || authHeader.split(" ").length != 2) {

				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AUTH_ERROR_MSG);
			} else {
				JWTClaimsSet claimSet = null;
				try {
					claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
				} catch (ParseException e) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_ERROR_MSG);
				} catch (JOSEException e) {
					e.printStackTrace();
				}

				// verificando se o token expirou
				if (AuthUtils.tokenExpirado(claimSet)) {
					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, EXPIRE_ERROR_MSG);
				}

				if (!AuthUtils.palavraSecretaValida(authHeader)) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALIDTOKEN_ERROR_MSG);
				}

				if (claimSet.getClaim("login") == null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALIDTOKEN_ERROR_MSG);
				}
			}
			chain.doFilter(request, response);

		}

	}

}
