package dev.johnshift.udemyspringbootfundamentals.controllers;

import org.springframework.stereotype.Component;

@Component
public class AddBookResponse {
  public String message;
  public String id;

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
