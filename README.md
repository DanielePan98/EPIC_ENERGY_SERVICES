# EPIC_ENERGY_SERVICES

## Tabella dei contenuti

- [Introduzione e Tecnologie](#Introduzione-e-Tecnologie)
- [Versione](#Versione)
- [Requisiti](#Requisiti)
- [Principali features](#Principali-features)
- [Testing](#Testing)
- [External Resources](#External-resources)
- [License](#License)

## Introduzione
Questa è un'applicazione per gestire i clienti bussiness di un'agenzia nell'ambito del fornimento d'energia. 

### Tecnologie: 
- Java + Spring Boot
- JUnit
- Visual Studio Code
- Advanced REST Client
- Maven
- Git and Gihub, of course : ) 

## Introduzione e Tecnologie

## Versione
Latest stable version: 3.0  

## Requisiti
Creare nuovi dati attraverso i metodi POST(es.nuove cliente,nuova fattura,nuovo indirizzo)
Associare all'elemento creato le varie relazioni di chiave esterna(es.Creo una nuova fattura,aggiorna fattura setCliente)


## Principali features

- Servizi Rest con le principali CRUD per tutte le entity
- Collection su Postman di tutte le richieste URL
- Implementazione di documentazione OpenAPI e Swagger
- Servizi Rest di ordinamento e di ricerca per filtro delle classi Cliente.java e Fattura.java
- Deployement su Heroku
- Front-end con Thymeleaf

## Testing

Per i test ho usato Junit 5,raggiungendo una soglia di coverage dell'intera applicazione del 66%.



## External Resources

Heroku service:  
- https://www.heroku.com/

Testing: 
- http://wiki.c2.com/?ArrangeActAssert 
- https://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
- https://github.com/advanced-rest-client

Java:  
- https://spring.io/

## License
MIT ©