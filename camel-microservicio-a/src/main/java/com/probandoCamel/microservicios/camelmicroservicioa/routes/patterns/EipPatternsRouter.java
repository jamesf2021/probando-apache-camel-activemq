package com.probandoCamel.microservicios.camelmicroservicioa.routes.patterns;

import java.util.List;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.probandoCamel.microservicios.camelmicroservicioa.CambioActual;

@Component
public class EipPatternsRouter extends RouteBuilder {

  @Autowired private SplitterComponent splitter;

  @Autowired DynamicRouterBean dynamicRouterBean;

  @Override
  public void configure() throws Exception {

    // Pipeline
    // Content Based Routing - choice()
    // Multicast

    /*    from("timer:multicast?period=10000")
    .multicast()
    .to("log:algunacosa1", "log:algunacosa2", "log:algunacosa3");*/

    // from("file:files/csv").unmarshal().csv().split(body()).to("activemq:split-cola");

    // Mensaje, mensaje2, mensaje3
    /*from("file:files/csv")
    .convertBodyTo(String.class)
    //.split(body(), ",")
    .split(method(splitter))
    .to("activemq:split-cola");*/

    // Aggregate
    // Mensajes => Agreggate => Endpoint
    from("file:files/aggregate-json")
        .unmarshal()
        .json(JsonLibrary.Jackson, CambioActual.class)
        .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
        .completionSize(3)
        //.completionTimeout(HIGHEST)
        .to("log:aggregate-json");

    String routingSlip = "direct:endpoint1,direct:endpoint2";
    //String routingSlip = "direct:endpoint1,direct:endpoint2,direct:endpoint3";

    from("timer:routingSlip?period=10000")
        .transform()
        .constant("Mi mensaje codificado")
        .routingSlip(simple(routingSlip));

    // Dynamic routing

    // Paso 1, paso 2, paso 3

    from("timer:dynamicRouting?period={{timePeriod}}")
        .transform()
        .constant("Mi mensaje codificado")
        .dynamicRouter(method(dynamicRouterBean));

    // endpoint1
    // endpoint2
    // endpoint3

    from("direct:endpoint1").to("{{endpoint-for-logging}}");

    from("direct:endpoint2").to("log:directendpoint2");

    from("direct:endpoint3").to("log:directendpoint3");

    // routing slip
    /*    from("timer:multicast?period=10000")
    .multicast()
    .to("log:algunacosa1", "log:algunacosa2", "log:algunacosa3");*/
  }
}

@Component
class SplitterComponent {
  public List<String> splitInput(String body) {
    return List.of("Abc", "Def", "Ghi");
  }
}

@Component
class DynamicRouterBean {

  Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);

  int invocations;

  public String decideTheNextEndpoint(
      @ExchangeProperties Map<String, String> properties,
      @Headers Map<String, String> headers,
      @Body String body) {

    logger.info("{} {} {}", properties, headers, body);
    invocations++;

    if (invocations % 3 == 0) return "direct:endpoint1";

    if (invocations % 3 == 1) return "direct:endpoint2,direct:endpoint3";

    return null;
  }
}
