/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.jojo.spring.boot.cxf.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author kkosittaruk
 */
@WebService( serviceName = "HelloWorldService" )
public class HelloWorldService {

  /**
   * This is a sample web service operation
   */
  @WebMethod( operationName = "hello" )
  public String hello( @WebParam( name = "name" ) String txt) {
    return "Hello " + txt + " !";
  }
}
