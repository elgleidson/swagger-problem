package com.github.elgleidson.swagger.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(description = "my class description")
public class MyClass {

  @Schema(description = "my class field description")
  private final String someField;

}
