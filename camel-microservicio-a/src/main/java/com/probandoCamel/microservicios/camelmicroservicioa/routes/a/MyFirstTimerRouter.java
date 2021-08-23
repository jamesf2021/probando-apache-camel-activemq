package com.probandoCamel.microservicios.camelmicroservicioa.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

  @Autowired private GetCurrentTimeBean getCurrentTimeBean;

  @Autowired private SimpleLoggingProcessingComponent loggingComponent;

  @Override
  public void configure() throws Exception {
    // timer
    // transformation
    // database - log
    // Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
    from("timer:first-timer") //  null
        .log("${body}") // imprime null
        .transform()
        .constant("Mi mensaje constante")
        .log("${body}") // imprime Mi mensaje constante
        //.transform().constant("La fecha es " + LocalDateTime.now())
        //.bean("getCurrentTimeBean")

        // Processing
        // Transformation

        .bean(getCurrentTimeBean)
        .log("${body}") // imprime La fecha es 2021-08-17
        .bean(loggingComponent)
        .log("${body}")
        .process(new SimpleLoggingProcessor())
        .to("log:first-timer"); // database
  }
}

@Component
class GetCurrentTimeBean {
  public String getCurrentTime() {
    return "La fecha es " + LocalDateTime.now();
  }
}

@Component
class SimpleLoggingProcessingComponent {

  private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

  public void process(String mensaje) {
    logger.info("SimpleLoggingProcessingComponent {}", mensaje);
  }
}

class SimpleLoggingProcessor implements Processor {

  private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

  @Override
  public void process(Exchange exchange) throws Exception {
    logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
  }
}
