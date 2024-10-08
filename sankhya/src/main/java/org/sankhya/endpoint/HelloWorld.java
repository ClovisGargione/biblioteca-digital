package org.sankhya.endpoint;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/helloworld")
public class HelloWorld {

    @GET
    @Produces({ MediaType.TEXT_PLAIN })
    public String hello() {
	return "Olá mundo REST!";
    }
}
