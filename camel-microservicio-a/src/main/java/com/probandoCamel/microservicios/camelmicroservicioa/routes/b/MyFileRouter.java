package com.probandoCamel.microservicios.camelmicroservicioa.routes.b;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class MyFileRouter extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file:files/input").log("${body}").to("file:files/output");
  }
}
