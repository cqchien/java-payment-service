package com.yourcompany.paymentservice.model;

public class Service {
  private long id;
  private String name;
  private String description;

  public Service(String name, String description) {
    this.id = System.currentTimeMillis();
    this.name = name;
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Service{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
