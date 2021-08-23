package com.probandoCamel.microservicios.camelmicroserviciob;

import java.math.BigDecimal;

public class CambioActual {
  private Long id;
  private String from;
  private String to;
  private BigDecimal conversionMultiple;

  public CambioActual() {}

  public CambioActual(Long id, String from, String to, BigDecimal conversionMultiple) {
    super();
    this.id = id;
    this.from = from;
    this.to = to;
    this.conversionMultiple = conversionMultiple;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public BigDecimal getConversionMultiple() {
    return conversionMultiple;
  }

  public void setConversionMultiple(BigDecimal conversionMultiple) {
    this.conversionMultiple = conversionMultiple;
  }

  @Override
  public String toString() {
    return "CambioActual [id="
        + id
        + ", from="
        + from
        + ", to="
        + to
        + ", conversionMultiple="
        + conversionMultiple
        + "]";
  }
}
