# What's this project about?

This is just a simple demo project to demonstrate the swagger problem with nullable fields in a API.


## Problem:

Swagger doesn't generate correct documentation when using `nullable` on a field whose type is not mapped to a primitive json type. 


## Steps to reproduce the problem:

1. run the project: `./mvn spring-boot:run`
2. go to http://localhost:8080/v3/api-docs

Description: 

* `nonNullableField` and `nullableField` have the same type and are mapped to a primitive json type (`string` in this case):
  * `nonNullableField` **cannot** be `null`.
  * `nullableField` can be `null`.

* `nonNullableList` and `nullableList` are `array` of the same type and are mapped to a primitive json type (`string` in this case):
  * `nonNullableList` **cannot** be `null`.
  * `nullableList` can be `null`.
  
The same for `*Object` and `*ObjectList` fields. The difference is that their types are mapped to json object.

Example of valid request: _all nullable fields are set to `null`_
```json
{
  "nonNullableField": "some string",
  "nullableField": null,
  "nonNullableObjectField": {
    "someField": "some string"
  },
  "nullableObjectField": null,
  "nonNullableList": [
    "some string"
  ],
  "nullableList": null,
  "nonNullableObjectList": [
    {
      "someField": "some string"
    }
  ],
  "nullableObjectList": null
}
```

### What is the resulting api doc?
Only the `schemas` object:

```json
{
  "MyResponse": {
    "type": "object",
    "properties": {
      "nonNullableField": {
        "type": "string",
        "description": "DOES NOT map to json object and CANNOT BE null"
      },
      "nullableField": {
        "type": "string",
        "description": "DOES NOT map to json object and CAN BE null"
      },
      "nonNullableObjectField": {
        "$ref": "#/components/schemas/MyClass",
        "description": "DOES map to json object and CANNOT BE null"
      },
      "nullableObjectField": {
        "$ref": "#/components/schemas/MyClass",
        "description": "DOES map to json object and CAN BE null"
      },
      "nonNullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and CANNOT BE null",
        "items": {
          "type": "string"
        }
      },
      "nullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and CAN BE null",
        "items": {
          "type": "string"
        }
      },
      "nonNullableObjectList": {
        "type": "array",
        "description": "list that DOES map to json object and CANNOT BE null",
        "items": {
          "$ref": "#/components/schemas/MyClass"
        }
      },
      "nullableObjectList": {
        "type": "array",
        "description": "list that DOES map to json object and CAN BE null",
        "items": {
          "$ref": "#/components/schemas/MyClass"
        }
      }
    }
  },
  "MyClass": {
    "type": "object",
    "description": "my class description",
    "properties": {
      "someField": {
        "type": "string",
        "description": "my class field description"
      }
    }
  }
}
```

As you can see, the `nullable` property is missing completely.

If you uncomment the Jakarta's `@NotNull` annotation you'll have the same result, but there will be an `required` array with all non-nullable fields.
```json
{
  "MyResponse": {
    ...
    "required": [
      "nonNullableField",
      "nonNullableList",
      "nonNullableObjectField",
      "nonNullableObjectList"
    ]
  }
```

### What should be the result: 

The `nullable` field should be placed in field definition.

```json
{
  "MyResponse": {
    "type": "object",
    "properties": {
      "nonNullableField": {
        "type": "string",
        "description": "DOES NOT map to json object and CANNOT BE null"
      },
      "nullableField": {
        "type": "string",
        "description": "DOES NOT map to json object and CAN BE null",
        "nullable": true
      },
      "nonNullableObjectField": {
        "$ref": "#/components/schemas/MyClass",
        "description": "DOES map to json object and CANNOT BE null"
      },
      "nullableObjectField": {
        "$ref": "#/components/schemas/MyClass",
        "description": "DOES map to json object and CAN BE null",
        "nullable": true
      },
      "nonNullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and CANNOT BE null",
        "items": {
          "type": "string"
        }
      },
      "nullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and CAN BE null",
        "items": {
          "type": "string"
        },
        "nullable": true
      },
      "nonNullableObjectList": {
        "type": "array",
        "description": "list that DOES map to json object and CANNOT BE null",
        "items": {
          "$ref": "#/components/schemas/MyClass"
        }
      },
      "nullableObjectList": {
        "type": "array",
        "description": "list that DOES map to json object and CAN BE null",
        "items": {
          "$ref": "#/components/schemas/MyClass"
        },
        "nullable": true
      }
    }
  },
  "MyClass": {
    "type": "object",
    "description": "my class description",
    "properties": {
      "someField": {
        "type": "string",
        "description": "my class field description"
      }
    }
  }
}
```
