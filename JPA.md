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


### JPA Persistable Types

The term persistable types refers to data types that can be used in storing data in the database.

 - User defined classes - Entity classes, Mapped superclasses, Embeddable classes.
 - Simple Java data types: Primitive types, Wrappers, String, Date and Math types.
 - Multi value types - Collections, Maps and Arrays.
 - Miscellaneous types: Enum types and Serializable types (user or system defined).

### An Entity Class must:
  - be annotated with the javax.persistence.Entity annotation
  - have a public or protected, no-argument constructor. The class may have other constructors.
  - not be declared final. No methods or persistent instance variables must be declared final.
  - implement the Serializable interface, If an entity instance is passed by value as a detached object, such as through a session bean’s remote business interface
  - Entities may extend both entity and non-entity classes, and non-entity classes may extend entity classes.
  - Persistent instance variables must be declared private, protected, or package-private and can be accessed directly only by the entity class’s methods. Clients must access the entity’s state through accessor or business methods.

### Entity Class Name

 By default, the entity name is the unqualified name of the entity class. A different entity name can be set explicitly by using the name attribute of the Entity annotation:

 ```java
 @Entity(name="MyName")
 public class MyEntity {

 }
 ```

### Embeddable Classes

Embeddable classes are user defined persistable classes that function as value types. As with other non entity types, instances of an embeddable class can only be stored in the database as embedded objects

### JPA Entity Fields

Fields of persistable user defined classes (entity classes, embeddable classes and mapped superclasses) can be classified into the following five groups:

 - Transient fields

    fields that do not participate in persistence and their values are never stored in the database

    the following fields are transient:
    + Static field
    + final field
    + fields declared with Java `transient` modifier
    + fields declared with JPA `@Transient` annotation

 - Persistent fields

    By default, any field that is not declared as static or transient is a persistent field.
 **Fields are stored in the database when an entity object is persisted.**

    Every persistent field can be marked with one of the following annotations:
    + OneToOne, ManyToOne - for references of entity types.
    + OneToMany, ManyToMany - for collections and maps of entity types.
    + Basic - for any other persistable type.

 - Inverse (Mapped By) fields

    Inverse (or mapped by) fields contain data that is not stored as part of the entity in the database, but is still available after retrieval by a special automatic query.

    ```java
    @Entity
    public class Employee {
        String name;
        @ManyToOne Department department;
    }

    @Entity
    public class Department {
        @OneToMany(mappedBy="department") Set<Employee> employees;
    }
    ```

    The` mappedBy` element (above) specifies that the `employees` field is an `inverse field` rather than a `persistent field`. The content of the employees set is not stored as part of a Department entity. Instead, employees is automatically populated when a Department entity is retrieved from the database.

    ObjectDB accomplishes this by effectively running the following query (where :d represents the Department entity):

    ```java
    SELECT e FROM Employee e WHERE e.department = :d
    ```

    if the employees field is used often, a persistent field rather than inverse field is expected to be more efficient. In this case, two unidirectional and unrelated relationships are managed by the Employee and the Department classes and the application is responsible to keep them synchronized.

    For an inverse map field, the keys can be extracted from the inverse query results by specifying a selected key field using the MapKey annotation:

    ```java
    @Entity
    public class Department {
        @OneToMany(mappedBy="department") @MapKey(name="name")
        Map<String,Employee> employees;
    }
    ```

 - Primary key (ID) fields

   ObjectDB supports implicit object IDs, so an explicitly defined primary key is not required. But ObjectDB also supports explicit standard JPA primary keys.

   + Automatic Primary Key

      By default the primary key is a sequential 64 bit number (long) that is set automatically by ObjectDB for every new entity object that is stored in the database. The primary key of the first entity object in the database is 1, the primary key of the second entity object is 2, etc. Primary key values are not recycled when entity objects are deleted from the database.

      ```java
      @Entity
      public class Project {
          @Id @GeneratedValue long id; // still set automatically
           :
      }
      ```

   + Application Set Primary Key

     If an entity has a primary key field that is not marked with `@GeneratedValue`, automatic primary key value is not generated and the application is responsible to set a primary key by initializing the primary key field.

   + Composite Primary Key

      When an entity has multiple primary key fields, JPA requires defining a special ID class that is attached to the entity class using the `@IdClass` annotation.

      ```java
      Class ProjectId {
          int departmentId;
          long projectId;
      }

      @Entity @IdClass(ProjectId.class)
      public class Project {
          @Id int departmentId;
          @Id long projectId;
           :
      }
      ```
   +  Embedded Primary Key

      An alternate way to represent a composite primary key is to use an embeddable class:

      ```java
      @Entity
      public class Project {
          @EmbeddedId ProjectId id;
           :
      }

      @Embeddable
      Class ProjectId {
          int departmentId;
          long projectId;
      }
      ```

   + Obtaining the Primary Key

     ```java
     PersistenceUnitUtil util = emf.getPersistenceUnitUtil();
     Object projectId = util.getIdentifier(project);
     ```

   +

### Property Access

   By default, ObjectDB accesses the fields directly, but accessing fields indirectly as properties using get and set methods is also supported. To use property access mode, every non-transient field must have get and set methods based on the Java bean property convention.

   Property access is enabled by moving all the JPA annotations from the fields to their respective get methods and specifying the Access annotation on the class itself:

   ```java
   @Entity @Access(AccessType.PROPERTY)
    public static class PropertyAccess {
        private int _id;
        @Id int getId() { return _id; }
        void setId(int id) { _id = id; }

        private String str;
        String getStr() { return str; }
        void setStr(String str) { this.str = str; }
    }
   ```

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

## Doing CRUD Database Operations with JPA

With a given `EntityManger`, we can use it to store, retrieve, update and delete database objects.

### Store new `Entity` Objects

```java
em.getTransaction().begin();
  for (int i = 0; i < 1000; i++) {
      Point p = new Point(i, i);
      em.persist(p);
  }
  em.getTransaction().commit();
```

### do some queries

```java
Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
System.out.println("Total Points: " + q1.getSingleResult());
```

### Retrieving Existing Entities

```java
TypedQuery<Point> query =
    em.createQuery("SELECT p FROM Point p", Point.class);
List<Point> results = query.getResultList();
```

The `getResultList` method executes the query and returns the result objects.

### update and delete Entities

**delete entities**

```java
em.getTransaction().begin();
em.remove(p); // delete entity
em.getTransaction().commit();
```

p must be a managed entity object of the `EntityManager` em

**update Entities**

```java
em.getTransaction().begin();
p.setX(p.getX() + 100); // update entity
em.getTransaction().commit();
```

[Tutorial](http://www.objectdb.com/java/jpa/getting/started)
