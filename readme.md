# Hypermedia-Driven RESTful Pokemon API

---
Java with Spring HATEOAS app providing operations on Pokemons of Nintendo games. Supports search/create/read/delete
operations interacting with external [poke service](https://pokeapi.co/). Results contain links to navigate resources.

## Implemented functionalities:

---

- read list of available Pokemons (from external service and custom tailored by the clients)
- read Pokemon's details
- search Pokemons by name
- create custom Pokemon
- delete any Pokemon
- pagination of collection results (default if not specified - first page with 20 results)
- caching of API/DB results

## API Specification

The created API consists of the following endpoints:

| URL                    | Method | Description                               | Query parameters                               |
|------------------------|--------|-------------------------------------------|------------------------------------------------|
| /api/v1/pokemon        | GET    | Get a list of Pokemons (paginated)        | page, size (optional)                          |
| /api/v1/pokemon/{id}   | GET    | Get a single Pokemon details              | -                                              |
| /api/v1/pokemon        | POST   | Add a new custom Pokemon                  | -                                              |
| /api/v1/pokemon/{id}   | DELETE | Delete an existing Pokemon                | -                                              |
| /api/v1/pokemon/search | GET    | Search pokemons by name containing phrase | name (min. 3 chars) <br/>page, size (optional) |

### Examples:

---
```GET: /api/v1/pokemon?size=2&page=0```
```yaml
{
    "_embedded": {
        "pokemons": [
            {
                "name": "bulbasaur",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/v1/pokemon/1"
                    },
                    "pokemon list": {
                        "href": "http://localhost:8080/api/v1/pokemon"
                    }
                }
            },
            {
                "name": "ivysaur",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/v1/pokemon/2"
                    },
                    "pokemon list": {
                        "href": "http://localhost:8080/api/v1/pokemon"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/pokemon"
        }
    }
}
```

```GET: /api/v1/pokemon/1```
```yaml
{
    "name": "bulbasaur",
    "height": 7,
    "weight": 69,
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    "abilities": [
        {
            "name": "overgrow",
            "is_hidden": false
        },
        {
            "name": "chlorophyll",
            "is_hidden": true
        }
    ],
    "stats": [
        {
            "name": "hp",
            "base_stat": 45
        },
        {
            "name": "special-defense",
            "base_stat": 65
        },
        {
            "name": "defense",
            "base_stat": 49
        },
        {
            "name": "attack",
            "base_stat": 49
        },
        {
            "name": "speed",
            "base_stat": 45
        },
        {
            "name": "special-attack",
            "base_stat": 65
        }
    ],
    "types": [
        {
            "name": "poison"
        },
        {
            "name": "grass"
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/pokemon/1"
        },
        "pokemon list": {
            "href": "http://localhost:8080/api/v1/pokemon"
        }
    }
}
```

```GET: /api/v1/pokemon/search?name=pika&page=0&size=2```
```yaml
{
    "_embedded": {
        "pokemons": [
            {
                "name": "pikachu",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/v1/pokemon/25"
                    },
                    "pokemon list": {
                        "href": "http://localhost:8080/api/v1/pokemon"
                    }
                }
            },
            {
                "name": "pikachu-rock-star",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/v1/pokemon/10080"
                    },
                    "pokemon list": {
                        "href": "http://localhost:8080/api/v1/pokemon"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/pokemon"
        }
    }
}
```

### Running the app locally:

---

**1) Requirements: Docker Compose (multi-container setup)**

- run the app in IDE or CLI from root of the project:  
  ```docker compose up```

**2) Requirements: Java17, instance of PostgreSQL database**

- start PostgreSQL database, e.g. in docker container:  
  ```docker run -e POSTGRES_USER=root -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=postgres -p 5432:5432 -d postgres```

- run the app in IDE or CLI from root of the project:  
  ```./mvnw clean package```  
  ```java -jar target/*.jar```

---

### Technologies used:

- Java 17
- Spring HATEOAS
- Spring Boot
- Spring Data JPA
- Caffeine cache
- Hibernate
- PostgreSQL
- Docker
- Lombok
- Maven
- JUnit