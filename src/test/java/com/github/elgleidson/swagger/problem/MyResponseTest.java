package com.github.elgleidson.swagger.problem;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Map;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MyResponseTest {

  static final Condition<Boolean> NOT_TRUE = new Condition<>(aBoolean -> aBoolean == null || !aBoolean, "not true");

  private static ResolvedSchema resolvedSchema;
  private static Map<String, Schema<?>> myResponseSchemaProperties;

  @BeforeAll
  static void setUp() {
    resolvedSchema = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(MyResponse.class));
    myResponseSchemaProperties = resolvedSchema.schema.getProperties();
  }

  @Test
  void requiredFields() {
    assertThat(resolvedSchema.schema.getRequired()).as("required")
            .containsExactlyInAnyOrder("nonNullableField", "nonNullableObjectField", "nonNullableList", "nonNullableObjectList");
  }

  @Test
  void nullableFieldIsNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nullableField");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("DOES NOT map to json object and DOES allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("string");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableFieldIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nonNullableField");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("DOES NOT map to json object and DOES NOT allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("string");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void nullableObjectFieldIsNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nullableObjectField");
    assertThat(fieldSchema.getDescription()).as("description").isNotEqualTo("DOES map to json object and DOES NOT allow null");
    assertThat(fieldSchema.get$ref()).as("$ref").isEqualTo("#/components/schemas/MyClass");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableObjectFieldIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nonNullableObjectField");
    assertThat(fieldSchema.getDescription()).as("description").isNotEqualTo("DOES map to json object and DOES allow null");
    assertThat(fieldSchema.get$ref()).as("$ref").isEqualTo("#/components/schemas/MyClass");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void nullableListIsNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nullableList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that DOES NOT map to json object and DOES allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableListIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nonNullableList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that DOES NOT map to json object and DOES NOT allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void nullableObjectListIsNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nullableObjectList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that DOES map to json object and DOES allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableObjectListIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemaProperties.get("nonNullableObjectList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that DOES map to json object and DOES NOT allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void objectSchema() {
    Schema<?> myClassSchema = resolvedSchema.referencedSchemas.get("MyClass");
    assertThat(myClassSchema.getDescription()).as("MyClass.description").isEqualTo("my class description");
    assertThat(myClassSchema.getNullable()).as("MyClass.nullable").is(NOT_TRUE);

    Map<String, Schema> myClassFields = myClassSchema.getProperties();
    Schema<?> fieldSchema = myClassFields.get("someField");
    assertThat(fieldSchema.getDescription()).as("someField.description").isEqualTo("my class field description");
    assertThat(fieldSchema.getType()).as("someField.type").isEqualTo("string");
    assertThat(fieldSchema.getNullable()).as("someField.nullable").is(NOT_TRUE);
  }

}