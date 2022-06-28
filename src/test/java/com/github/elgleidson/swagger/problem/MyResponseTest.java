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

  private static Map<String, Schema<?>> myResponseSchemas;

  @BeforeAll
  static void setUp() {
    ResolvedSchema resolvedSchema = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(MyResponse.class));
    myResponseSchemas = resolvedSchema.schema.getProperties();
  }

  @Test
  void nullableFieldIsNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nullableField");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("DO NOT map to json object and allows null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("string");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableFieldIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nonNullableField");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("DO NOT map to json object and DO NOT allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("string");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void nullableObjectFieldIsNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nullableObjectField");
    assertThat(fieldSchema.getDescription()).as("description").isNotEqualTo("map to json object and DOES NOT allow null");
    assertThat(fieldSchema.get$ref()).as("$ref").isEqualTo("#/components/schemas/MyClass");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableObjectFieldIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nonNullableObjectField");
    assertThat(fieldSchema.getDescription()).as("description").isNotEqualTo("map to json object and allows null");
    assertThat(fieldSchema.get$ref()).as("$ref").isEqualTo("#/components/schemas/MyClass");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void nullableListIsNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nullableList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that DOES NOT map to json object and allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableListIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nonNullableList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that DOES NOT map to json object and DOES NOT allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void nullableObjectListIsNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nullableObjectList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that map to json object and allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").isTrue();
  }

  @Test
  void nonNullableObjectListIsNotNullable() {
    Schema<?> fieldSchema = myResponseSchemas.get("nonNullableObjectList");
    assertThat(fieldSchema.getDescription()).as("description").isEqualTo("list that map to json object and DOES NOT allow null");
    assertThat(fieldSchema.getType()).as("type").isEqualTo("array");
    assertThat(fieldSchema.getNullable()).as("nullable").is(NOT_TRUE);
  }

  @Test
  void objectSchema() {
    ResolvedSchema resolvedSchema = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(MyResponse.class));
    Schema<?> myClassSchema = resolvedSchema.referencedSchemas.get("MyClass");
    assertThat(myClassSchema.getDescription()).as("MyClass.description").isEqualTo("my class description");
    assertThat(myClassSchema.getNullable()).as("MyClass.nullable").isNull();

    Map<String, Schema> myClassFields = myClassSchema.getProperties();
    Schema<?> fieldSchema = myClassFields.get("someField");
    assertThat(fieldSchema.getDescription()).as("someField.description").isEqualTo("my class field description");
    assertThat(fieldSchema.getType()).as("someField.type").isEqualTo("string");
    assertThat(fieldSchema.getNullable()).as("someField.nullable").is(NOT_TRUE);
  }

  static final Condition<Boolean> NOT_TRUE = new Condition<>(aBoolean -> aBoolean == null || !aBoolean, "not true");

}