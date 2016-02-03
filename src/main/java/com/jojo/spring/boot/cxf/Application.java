/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.jojo.spring.boot.cxf;

import com.jojo.spring.boot.cxf.rs.server.HelloRestServer;
import com.jojo.spring.boot.cxf.ws.server.HelloWorldService;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author kkosittaruk
 */
@SpringBootApplication
@ImportResource( { "classpath:META-INF/cxf/cxf.xml" } )
public class Application extends SpringBootServletInitializer {

  public static void main( String[] args ) {
    SpringApplication.run( Application.class , args );
  }

  @Override
  protected SpringApplicationBuilder configure( SpringApplicationBuilder builder ) {
    return builder.sources( Application.class );
  }

  @Autowired
  private ApplicationContext applicationContext;

  // Replaces the need for web.xml
  @Bean
  public ServletRegistrationBean servletRegistrationBean( ApplicationContext context ) {
    return new ServletRegistrationBean( new CXFServlet() , "/api/*" );
  }

  @Bean
  public Bus getBus() {
    return ( Bus ) applicationContext.getBean( Bus.DEFAULT_BUS_ID );
  }

  // Replaces cxf-servlet.xml
  @Bean
  // <jaxws:endpoint id="helloWorld" implementor="demo.spring.service.HelloWorldImpl"
  // address="/HelloWorld"/>
  public EndpointImpl helloWorldService() {
    EndpointImpl endpoint = new EndpointImpl( getBus() , new HelloWorldService() );
    endpoint.publish( "/helloservice" );
    endpoint.getServer().getEndpoint().getInInterceptors().add( new LoggingInInterceptor() );
    endpoint.getServer().getEndpoint().getOutInterceptors().add( new LoggingOutInterceptor() );
    return endpoint;
  }

  @Bean
  public Server helloRestService() {
    JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
    endpoint.setServiceBean( new HelloRestServer() );
    endpoint.setAddress( "/jaxrs" );
    endpoint.setBus( getBus() );
    endpoint.setProvider( new JacksonJsonProvider() );
    // endpoint.getServer().getEndpoint().getInInterceptors().add( new LoggingInInterceptor() );
    // endpoint.getServer().getEndpoint().getOutInterceptors().add( new LoggingOutInterceptor() );
    return endpoint.create();
  }

  // Configure the embedded tomcat to use same settings as default standalone tomcat deploy
  @Bean
  public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
    // Made to match the context path when deploying to standalone tomcat- can easily be kept in
    // sync w/ properties
    TomcatEmbeddedServletContainerFactory factory =
        new TomcatEmbeddedServletContainerFactory( "/ws-server-1.0" , 8080 );
    return factory;
  }

}
