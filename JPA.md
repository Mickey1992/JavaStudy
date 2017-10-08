# Java Persistence API (JPA)

The Java Persistence API provides Java developers with an object/relational mapping facility for managing relational data in Java applications.

What is persistence?
 - Persistence is the ability of an object to survive the lifetime of the OS process in which it resides.
 - Data that survive over several runs of your program are called "persistent".

Java Persistence consists of four areas:
 - The Java Persistence API
 - The query language
 - The Java Persistence Criteria API
 - Object/relational mapping metadata

## Entities

To be able to store objects in the database using JPA we need to define an entity class.

An entity represents a table in a relational database, and each entity instance corresponds to a row in that table.

```java
@Entity
public class Point {
    // Persistent Fields:
    private int x;
    private int y;

    // Constructor:
    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Accessor Methods:
    public int getX() { return this.x; }
    public int getY() { return this.y; }

    // String Representation:
    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }
}
```

### An Entity Class must:
  - be annotated with the javax.persistence.Entity annotation
  - have a public or protected, no-argument constructor. The class may have other constructors.
  - not be declared final. No methods or persistent instance variables must be declared final.
  - implement the Serializable interface, If an entity instance is passed by value as a detached object, such as through a session bean’s remote business interface
  - Entities may extend both entity and non-entity classes, and non-entity classes may extend entity classes.
  - Persistent instance variables must be declared private, protected, or package-private and can be accessed directly only by the entity class’s methods. Clients must access the entity’s state through accessor or business methods.

### Persistent Fields

By default, any field that is not declared as static or transient is a persistent field.
Fields are stored in the database when an entity object is persisted.

## Obtaining a JPA Database Connection

In JPA a database connection is represented by the `EntityManager` interface. Therefore, in order to manipulate an ObjectDB database we need an `EntityManager` instance. Operations that modify database content also require an `EntityTransaction` instance.

### Obtaining `EntityManager` instance

**Two Steps:**
 1. obtain an instance of `EntityManagerFactory` that represents the relevant database

   ```java
   EntityManagerFactory emf =
    Persistence.createEntityManagerFactory("$objectdb/db/points.odb");
   ```

   The EntityManagerFactory is also used to close the database once we are finished using it:

   ```java
   emf.close();
   ```

 2. use the `EntityManagerFactory` instance to obtain an instance of `EntityManager`

    ```java
    EntityManager em = emf.createEntityManager();
    ```

    The EntityManager instance represents a connection to the database. When using JPA, every operation on a database is associated with an EntityManager.

    When the connection to the database is no longer needed the EntityManager can be closed:

    ```java
    em.close();
    ```

### Using an EntityTransaction

Operations that modify database content, such as store, update, and delete **should only be performed within an active transaction.**

- Use the `EntityManger` instance to begin a transaction

   ```java
   em.getTransaction().begin();
   ```

- When a transaction is active you can invoke EntityManager methods that modify the database content, such as persist and remove.
- Database updates are collected and managed in memory and applied to the database when the transaction is committed:

   ```java
   em.getTransaction().commit();
   ```
