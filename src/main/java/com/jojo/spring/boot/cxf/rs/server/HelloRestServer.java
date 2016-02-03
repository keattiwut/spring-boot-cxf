/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.jojo.spring.boot.cxf.rs.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author kkosittaruk
 */
@Path( "/hello" )
public class HelloRestServer {

  @POST
  @Path( "/{msg}" )
  public Response hello( @PathParam( "msg" ) String msg) {
    return Response.ok( "Hello ".concat( msg ) ).build();
  }

  @GET
  @Path( "/" )
  @Produces( MediaType.APPLICATION_JSON )
  public Response getHello() {
    Hello h = new Hello( "Test" , "WORLD!!" );
    return Response.ok( h ).build();
  }

}
