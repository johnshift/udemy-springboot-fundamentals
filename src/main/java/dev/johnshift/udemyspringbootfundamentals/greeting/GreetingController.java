package dev.johnshift.udemyspringbootfundamentals.greeting;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @Autowired
  Greeting greeting;

  AtomicLong counter = new AtomicLong();

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World!") String name) {

    greeting.setId(counter.incrementAndGet());
    greeting.setMessage("Hello, " + name);

    return greeting;
  }
}
