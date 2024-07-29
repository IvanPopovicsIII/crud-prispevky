# CRUD prispevky

Web application that supports CRUD operations on Post objects.
Post object is defined as 
* id : Integer 
* userId : Integer  
* title : String (50)
* body : String (250)                            

## Requested endpoints  
     Create a Post
          userId verified by external API [https://jsonplaceholder.typicode.com/] 
     Read a Post
          Via either Post id or userId 
          If looked up by Post id and not found, it's then looked up on external API [https://jsonplaceholder.typicode.com/]
     Update a Post
          Interchangable parameters are only title and body
     Delete a Post
          Delets a Post using Post id


## First run
Web application uses postgres 15 for db. You can configure postgres parameters in compose.yaml file. To start the application, run CrudPrispevkyApplication.java. SecurityFilterChain is set up, that it disables csrf and allows all requests. 


### Notes
If error of this type occurs :  > Cannot invoke "org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(java.sql.SQLException, String)
you need to turn off postgres service. You can do so, using task manager -> services -> postgresql-64-15 -> stop
