package com.yourcompany.paymentservice.model;

public class ElectricityService extends Service {
  private static final String SERVICE_NAME = "ELECTRICITY";
  private static final String SERVICE_DESCRIPTION = "Electricity service";

  public ElectricityService() {
    super(SERVICE_NAME, SERVICE_DESCRIPTION);
  }
}
