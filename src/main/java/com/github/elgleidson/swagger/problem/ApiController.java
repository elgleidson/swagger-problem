package com.github.elgleidson.swagger.problem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ApiController {

  @GetMapping
  public Mono<ResponseEntity<MyResponse>> get() {
    return Mono.just(ResponseEntity.ok(new MyResponse()));
  }

}
