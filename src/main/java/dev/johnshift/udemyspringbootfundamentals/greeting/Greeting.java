package dev.johnshift.udemyspringbootfundamentals.greeting;

import org.springframework.stereotype.Component;

@Component
public class Greeting {
  private long id;
  private String message;

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
