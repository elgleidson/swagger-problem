package com.github.elgleidson.swagger.problem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

  @GetMapping
  public ResponseEntity<MyResponse> get() {
    return ResponseEntity.ok(new MyResponse());
  }

}
