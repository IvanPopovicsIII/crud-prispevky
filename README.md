# CRUD prispevky

Web application that supports CRUD operations on Post objects.
Post object is defined as 
* **id** : Integer 
* **userId** : Integer  
* **title** : String (100)
* **body** : String (250)                            

## Requested endpoints  
     Create a Post
          userId verified by external API [https://jsonplaceholder.typicode.com/] 
     Read a Post
          Via either Post id or userId 
          If looked up by Post id and not found, it's then looked up on external API [https://jsonplaceholder.typicode.com/] and saved to db
     Update a Post
          Interchangable parameters are only title and body
     Delete a Post
          Delets a Post using Post id

API is documented using Swagger and can be visited on (http://localhost:8080/swagger-ui/index.html) after running the application


## First run
Web application uses postgres 15 for db and docker for containerisation. You can configure docker parameters in compose.yaml file. To start the application, run CrudPrispevkyApplication.java. SecurityFilterChain is set up, so it disables csrf and allows all requests. 


### Notes
If error of this type occurs :  > Cannot invoke "org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(java.sql.SQLException, String)
you need to turn off postgres service. You can do so, using task manager -> services -> postgresql-64-15 -> stop
