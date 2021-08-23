package com.probandoCamel.microservicios.camelmicroservicioa.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RestApiConsumerRouter extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    // defino mi ruta
    /*    from("timer:active-mq-ruta?period=10000")
    .transform()
    .constant("Mi mensaje para el Active MQ")
    .log("${body}")
    .to("activemq:mi-activemq-cola");*/

    //from("file:files/json").log("${body}").to("activemq:mi-activemq-cola");

    restConfiguration().host("localhost").port(8000);

    from("timer:rest-api-consumer?period=10000")
        .setHeader("from", () -> "EUR")
        .setHeader("to", () -> "INR")
        .log("${body}")
        .to("rest:get:/cambi-actual/from/{from}/to/{to}")
        .log("${body}");
  }
}
