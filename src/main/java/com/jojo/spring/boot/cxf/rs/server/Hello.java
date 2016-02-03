/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.jojo.spring.boot.cxf.rs.server;

/**
 *
 * @author kkosittaruk
 */
public class Hello {

  private String name;

  private String world;

  public Hello() {}

  public Hello( String name , String world ) {
    this.name = name;
    this.world = world;
  }

  public String getName() {
    return name;
  }

  public String getWorld() {
    return world;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public void setWorld( String world ) {
    this.world = world;
  }

  @Override
  public String toString() {
    return super.toString(); // To change body of generated methods, choose Tools | Templates.
  }


}
