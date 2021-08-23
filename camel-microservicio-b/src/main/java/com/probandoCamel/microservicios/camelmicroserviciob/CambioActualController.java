package com.probandoCamel.microservicios.camelmicroserviciob;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CambioActualController {

  @GetMapping("/cambi-actual/from/{from}/to/{to}")
  public CambioActual findConversionValue(@PathVariable String from, @PathVariable String to) {
    return new CambioActual(1001L, from, to, BigDecimal.TEN);
  }
}
