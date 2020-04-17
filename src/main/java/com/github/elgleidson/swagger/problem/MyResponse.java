package com.github.elgleidson.swagger.problem;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Value;

@Value
public class MyResponse {

  @Schema(nullable = false, description = "DO NOT map to json object and DO NOT allow null")
  private final String nonNullableField = "not null";

  @Schema(nullable = true, description = "DO NOT map to json object and allows null")
  private final String nullableField = null;

  @Schema(nullable = false, description = "map to json object and DOES NOT allow null")
  private final MyClass nonNullableObjectField = new MyClass(nonNullableField);

  @Schema(nullable = true, description = "map to json object and allows null")
  private final MyClass nullableObjectField = null;

  @ArraySchema(arraySchema = @Schema(nullable = false, description = "list that DOES NOT map to json object and DOES NOT allow null"))
  private final List<String> nonNullableList = List.of(nonNullableField);

  @ArraySchema(arraySchema = @Schema(nullable = true, description = "list that DOES NOT map to json object and allow null"))
  private final List<String> nullableList = null;

  @ArraySchema(arraySchema = @Schema(nullable = false, description = "list that map to json object and DOES NOT allow null"))
  private final List<MyClass> nonNullableObjectList = List.of(nonNullableObjectField);

  @ArraySchema(arraySchema = @Schema(nullable = true, description = "list that map to json object and allow null"))
  private final List<MyClass> nullableObjectList = null;

}
