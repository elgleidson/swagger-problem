package com.github.elgleidson.swagger.problem;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Value;

@Value
public class MyResponse {

  @Schema(nullable = false, description = "DOES NOT map to json object and CANNOT BE null")
//  @NotNull
  String nonNullableField;

  @Schema(nullable = true, description = "DOES NOT map to json object and CAN BE null")
  String nullableField;

  @Schema(nullable = false, description = "DOES map to json object and CANNOT BE null")
//  @NotNull
  MyClass nonNullableObjectField;

  @Schema(nullable = true, description = "DOES map to json object and CAN BE null")
  MyClass nullableObjectField;

  @ArraySchema(arraySchema = @Schema(nullable = false, description = "list whose items DO NOT map to json object and CANNOT BE null"))
//  @NotNull
  List<String> nonNullableList;

  @ArraySchema(arraySchema = @Schema(nullable = true, description = "list whose items DO NOT map to json object and CAN BE null"))
  List<String> nullableList;

  @ArraySchema(arraySchema = @Schema(nullable = false, description = "list whose items DO map to json object and CANNOT BE null"))
//  @NotNull
  List<MyClass> nonNullableObjectList;

  @ArraySchema(arraySchema = @Schema(nullable = true, description = "list whose items DO map to json object and CAN BE null"))
  List<MyClass> nullableObjectList;

}
