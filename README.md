 **DESCRIPTION**
 
This is a Spring Boot application that hosts a REST web service for managing hockey 
items and persists that data in a MySQL 8.0 database.
It is built with Gradle, uses Java 14, uses Log4J2 for logging instead of SLF4J.
It communicates with the MySQL database using Spring Data JPA and Flyway for schema 
creations and migration.

The REST webservice manages items in the below format. (Create, Read, Update, Delete)

```json{
  "id": "26937741-15a2-435b-82b0-39cd0539ed5e",  :uuid
  "name": "Item Name",                           :string (0-20)
  "description": "Item description",             :string (0-200)
  "type": "hockey_pads"                          :enum["hockey_pads","hockey_skates","hockey_stick"]
  "cost": 20.00,                                 :double (>0.00)
  "created_at": "2022-03-10T14:46:55.372283Z",   :timestamp (iso8601)
  "updated_at": "2022-03-10T14:46:55.372283Z",   :timestamp (iso8601)
  "deleted_at": null                             :timestamp (iso8601)
}
```

**ENHANCEMENTS:**
1) It also contains a request filter that logs the IP address of each incoming request.
2) It has a basic search endpoint that returns a list of matching items.
3) It broadcasts messages when items are modified to a RabbitMQ topic.
RabbitMQ 3.9.4 is used running it locally. To see the messages go to http://localhost:15672/

*NOTE:* 
In Intellij, if errors show up when trying to compile the project , please make this change.This 
project uses new Java 14 record, so you need to select Project Language level
in Intellij Project Settings as `14 (Preview) - Records, patterns and text blocks.`