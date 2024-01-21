package com.yourcompany.paymentservice.model;

public class WaterService extends Service {
  private static final String SERVICE_NAME = "WATER";
  private static final String SERVICE_DESCRIPTION = "Water service";

  public WaterService() {
    super(SERVICE_NAME, SERVICE_DESCRIPTION);
  }
}
