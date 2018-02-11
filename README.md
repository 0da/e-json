# e-json

This library works with annotations.
If you want to work with the object field, add the **@JsonValue(*"some_json_name"*)** annotation.
```java 
public class Person {
    
    @JsonValue("person_id")
    private int id ;
    
    @JsonValue("person_name")
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
        this.id = name.hashCode();
    }
    
    @Override
    public String toString() {
        return id + " " + name;
    }
}
```

## To encode object
```java
  final Person person = new Person("John Doe");
  final String json = Json.encode(person);
  System.out.println(json);
```
#### Output
```json
{"person_id":-1367319387,"person_name":"John Doe"}
```

## To decode object
```java
  final String json = "{\"person_id\":-1367319387,\"person_name\":\"John Doe\"}";
  final JsonStruct decode = Json.decode(json);
  System.out.println(decode);
  ```
#### Output
>   {person_name=John Doe, person_id=-1367319387}

JsonStruct is interface for:
* JsonArray *extends ArrayList\<JsonStruct\>*
* JsonObject *extends HashMap\<String, JsonStruct\>*
* JsonBoolean
* JsonString
* JsonNumber
  
In this case JsonStruct is JsonObject or JsonArray. You can know this by using decode.isJsonObject();

JsonStruct has the following methods:
* asChar()
* asBoolean() 
* asByte() 
* asShort() 
* asInt() 
* asLong() 
* asFloat() 
* asDouble() 
* asBigInteger() 
* asBigDecimal() 
* asArray() 
* isJsonObject() 

## To deserialize object
Deserializable class should have default public constructor
```java
  final String json = "{\"person_id\":-1367319387,\"person_name\":\"John Doe\"}";
  final Person person = Json.decode(json, Person.class);
  System.out.println(person);
  ```
  #### Output
>   -1367319387 John Doe

## InputStream
*Json.decode(... , Object.class)* and *Json.decode(...)* also work with **InputStream**
