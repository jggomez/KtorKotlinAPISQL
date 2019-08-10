# KotlinAPISQL

Api con Ktor que accede a una base de datos MYSQL

Consideraciones:
- Se usa Clean Architecture, separando las capas de endpoints, casos de uso, repositorios.
- Se usa el patron comando en los casos de uso para dividir mejor las tareas.
- Se usa Coroutines.
- Se usa patron DTO para regresar los datos requeridos.
- Se usa la lib Exposed para acceder a los datos.

Como probar:
- Ejecuta el programa.
- Invoca a las APIs:
  - localhost:3939/accounts/1
  - localhost:3939/accounts

Listo!! pronto actualizaciones....
