package com.probandoCamel.microservicios.camelmicroservicioa.routes.c;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class ActiveMqSenderRouter extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    // defino mi ruta
    /*    from("timer:active-mq-ruta?period=10000")
    .transform()
    .constant("Mi mensaje para el Active MQ")
    .log("${body}")
    .to("activemq:mi-activemq-cola");*/

    //from("file:files/json").log("${body}").to("activemq:mi-activemq-cola");

    from("file:files/xml").log("${body}").to("activemq:mi-activemq-xml-cola");
  }
}
