package com.github.elgleidson.swagger.problem;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Value
public class MyResponse {

  @NotBlank
  @Schema(nullable = false, description = "DOES NOT map to json object and DOES NOT allow null")
  private final String nonNullableField;

  @Schema(nullable = true, description = "DOES NOT map to json object and DOES allow null")
  private final String nullableField;

  @Schema(nullable = false, description = "DOES map to json object and DOES NOT allow null")
  @NotNull
  private final MyClass nonNullableObjectField;

  @Schema(nullable = true, description = "DOES map to json object and DOES allow null")
  private final MyClass nullableObjectField;

  @ArraySchema(arraySchema = @Schema(nullable = false, description = "list that DOES NOT map to json object and DOES NOT allow null"))
  @NotEmpty
  private final List<String> nonNullableList;

  @ArraySchema(arraySchema = @Schema(nullable = true, description = "list that DOES NOT map to json object and DOES allow null"))
  private final List<String> nullableList;

  @ArraySchema(arraySchema = @Schema(nullable = false, description = "list that DOES map to json object and DOES NOT allow null"))
  @NotEmpty
  private final List<MyClass> nonNullableObjectList;

  @ArraySchema(arraySchema = @Schema(nullable = true, description = "list that DOES map to json object and DOES allow null"))
  private final List<MyClass> nullableObjectList;

}
