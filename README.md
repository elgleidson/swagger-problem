# What's this project about?

This is just a simple demo project to demonstrate the swagger problem with nullable fields in a API.


## Problem:

Swagger doesn't generate correct documentation when using `nullable` on a field whose type is not mapped to a primitive json type. 


## Steps to reproduce the problem:

1. run the project: `./mvn spring-boot:run`
2. go to http://localhost:8080/v3/api-docs

## Example of resulting object 

You can go to http://localhost:8080/api and the result will be:

```json
{
  "nonNullableField": "not null",
  "nullableField": null,
  "nonNullableObjectField": {
    "someField": "some value"
  },
  "nullableObjectField": null,
  "nonNullableList": [
    "not null"
  ],
  "nullableList": null,
  "nonNullableObjectList": [
    {
      "someField": "some value"
    }
  ],
  "nullableObjectList": null
}
```

Description: 

* `nonNullableField` and `nullableField` have the same type and are mapped to a primitive json type (`string` in this case):
  * `nonNullableField` **does** **not** allow `null`.
  * `nullableField` allows `null`.

* `nonNullableList` and `nullableList` are `array` of the same type and are mapped to a primitive json type (`string` in this case):
  * `nonNullableList` **does** **not** allow `null`.
  * `nullableList` allows `null`.
  
The same for `*Object` and `*ObjectList` fields. The difference is that their types are mapped to json object. 

### What is the resulting api doc?
Only the `schemas` object:

```json
{
  "MainResponse": {
    "type": "object",
    "properties": {
      "nonNullableField": {
        "type": "string",
        "description": "DO NOT map to json object and DO NOT allow null"
      },
      "nullableField": {
        "type": "string",
        "description": "DO NOT map to json object and allows null",
        "nullable": true
      },
      "nonNullableObjectField": {
        "$ref": "#/components/schemas/MyClass"
      },
      "nullableObjectField": {
        "$ref": "#/components/schemas/MyClass"
      },
      "nonNullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and DOES NOT allow null",
        "items": {
          "type": "string"
        } 
      },
      "nullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and allow null",
        "nullable": true,
        "items": {
          "type": "string"
        }
      },
      "nonNullableObjectList": {
        "type": "array",
        "description": "list that map to json object and DOES NOT allow null",
        "items": {
          "$ref": "#/components/schemas/MyClass"
        }
      },
      "nullableObjectList": {
        "type": "array",
        "description": "list that map to json object and allow null",
        "nullable": true,
        "items": {
          "$ref": "#/components/schemas/MyClass"
        }
      }
    }
  },
  "MyClass": {
    "type": "object",
    "properties": {
      "someField": {
        "type": "string",
        "description": "my class field description"
      }
    },
    "description": "my class description",
    "nullable": true
  }
}
```

As you can see, the `nullable` property was put in object definition instead of field definition. 
The `array` fields and `string` fields (non-`object` type) are generate correctly, with `nullable` in the field itself, as expected.

### What should be the result: 

The `nullable` field should be placed in field definition.

```json
{
  "MainResponse": {
    "type": "object",
    "properties": {
      "nonNullableField": {
        "type": "string",
        "description": "DO NOT map to json object and DO NOT allow null"
      },
      "nullableField": {
        "type": "string",
        "description": "DO NOT map to json object and allows null",
        "nullable": true
      },
      "nonNullableObjectField": {
        "$ref": "#/components/schemas/MyClass",
        "description": "map to json object and DOES NOT allow null"
      },
      "nullableObjectField": {
        "$ref": "#/components/schemas/MyClass",
        "description": "map to json object and allows null",
        "nullable": true
      },
      "nonNullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and DOES NOT allow null",
        "items": {
          "type": "string"
        } 
      },
      "nullableList": {
        "type": "array",
        "description": "list that DOES NOT map to json object and allow null",
        "nullable": true,
        "items": {
          "type": "string"
        }
      },
      "nonNullableObjectList": {
        "type": "array",
        "description": "list that map to json object and DOES NOT allow null",
        "items": {
          "$ref": "#/components/schemas/MyClass"
        }
      },
      "nullableObjectList": {
        "type": "array",
        "description": "list that map to json object and allow null",
        "nullable": true,
        "items": {
          "$ref": "#/components/schemas/MyClass"
        }
      }      
    }
  },
  "MyClass": {
    "type": "object",
    "properties": {
      "someField": {
        "type": "string",
        "description": "my class field description"
      }
    },
    "description": "my class description"
  }
}
```
