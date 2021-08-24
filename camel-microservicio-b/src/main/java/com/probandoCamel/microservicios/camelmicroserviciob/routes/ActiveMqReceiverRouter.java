package com.probandoCamel.microservicios.camelmicroserviciob.routes;

import java.math.BigDecimal;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.probandoCamel.microservicios.camelmicroserviciob.CambioActual;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

  @Autowired MiCambioActualProcessor miCambioActualProcessor;

  @Autowired MiCambioActualTransformer miCambioActualTransformer;

  @Override
  public void configure() throws Exception {
    /*from("activemq:mi-activemq-cola")
    .unmarshal()
    .json(JsonLibrary.Jackson, CambioActual.class)
    .bean(miCambioActualProcessor)
    .bean(miCambioActualTransformer)
    .to("log:mensaje-recivido-de-active-mq");*/
    /* from("activemq:mi-activemq-xml-cola")
    .unmarshal()
    .jacksonxml(CambioActual.class)
    .to("log:mensaje-recivido-de-active-mq");*/

    from("activemq:split-cola").to("log:mensaje-recivido-de-active-mq");
  }
}

@Component
class MiCambioActualProcessor {

  Logger logger = LoggerFactory.getLogger(ActiveMqReceiverRouter.class);

  public void mensajeProcess(CambioActual cambioActual) {
    logger.info(
        "Realiza algun proceso con cambioActual.getConversionMultiple() cuyo valor es {}",
        cambioActual.getConversionMultiple());
  }
}

@Component
class MiCambioActualTransformer {

  Logger logger = LoggerFactory.getLogger(ActiveMqReceiverRouter.class);

  public CambioActual mensajeProcess(CambioActual cambioActual) {

    cambioActual.setConversionMultiple(
        cambioActual.getConversionMultiple().multiply(BigDecimal.TEN));

    return cambioActual;
  }
}
