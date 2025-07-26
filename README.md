
📦 MicroLibrary - Aplicación basada en microservicios

🧑‍💼 CustomerRestController - Gestión de Clientes
Controlador para manejar todas las operaciones relacionadas con la entidad Customer.

Endpoints
📄 Obtener todos los clientes
GET /customers/list
Devuelve una lista con todos los clientes registrados.

🔍 Obtener cliente por ID
GET /customers/{id}
Busca un cliente por su ID.
Respuestas:

200 OK: Cliente encontrado

404 Not Found: Cliente no encontrado

➕ Crear nuevo cliente
POST /customers/alta
Crea un nuevo cliente.
Cuerpo: JSON con los datos del cliente
Respuesta:

200 OK: Cliente creado exitosamente

♻️ Actualizar cliente existente
PUT /customers/update/{id}
Actualiza los datos de un cliente existente.
Cuerpo: JSON con los nuevos datos del cliente
Respuestas:

200 OK: Cliente actualizado

404 Not Found: Cliente no encontrado

❌ Eliminar cliente
DELETE /customers/delete/{id}
Elimina un cliente por su ID.
Respuestas:

200 OK: Cliente eliminado

404 Not Found: Cliente no encontrado

📦 ProductRestController - Gestión de Productos
Controlador para gestionar operaciones sobre productos.

Endpoints
📄 Obtener todos los productos
GET /products/list
Devuelve una lista de todos los productos.

🔍 Obtener producto por ID
GET /products/{id}
Busca un producto por su ID.
Respuestas:

200 OK: Producto encontrado

404 Not Found: Producto no encontrado

➕ Crear nuevo producto
POST /products/alta
Crea un nuevo producto.
Cuerpo: JSON con los datos del producto
Respuesta:

200 OK: Producto creado exitosamente

♻️ Actualizar producto existente
PUT /products/update/{id}
Actualiza un producto existente.
Cuerpo: JSON con los nuevos datos del producto
Respuestas:

200 OK: Producto actualizado

404 Not Found: Producto no encontrado

❌ Eliminar producto
DELETE /products/delete/{id}
Elimina un producto por su ID.
Respuestas:

200 OK: Producto eliminado

404 Not Found: Producto no encontrado

🔗 CustomerProductRestController - Gestión de Relaciones Cliente-Producto
Controlador encargado de manejar las relaciones entre clientes y productos.

Endpoints
📄 Obtener todas las relaciones
GET /customerproducts/list
Devuelve una lista de todas las relaciones entre clientes y productos.

🔍 Obtener relación por ID
GET /customerproducts/{id}
Busca una relación cliente-producto por su ID.
Respuestas:

200 OK: Relación encontrada

404 Not Found: Relación no encontrada

➕ Crear nueva relación
POST /customerproducts/alta
Crea una nueva relación cliente-producto.
Cuerpo: JSON con los IDs de cliente y producto
Respuesta:

200 OK: Relación creada exitosamente

♻️ Actualizar relación existente
PUT /customerproducts/update/{id}
Actualiza una relación existente.
Cuerpo: JSON con los nuevos valores de la relación
Respuestas:

200 OK: Relación actualizada

404 Not Found: Relación no encontrada

❌ Eliminar relación
DELETE /customerproducts/delete/{id}
Elimina una relación cliente-producto por su ID.
Respuestas:

200 OK: Relación eliminada

404 Not Found: Relación no encontrada

🧱 Tecnologías y Requisitos
☕ Java 17+

🌱 Spring Boot 3.3+

🐬 MYSQL

🐳 Docker (opcional para despliegue)

📦 Maven

ℹ️ Notas
Los controladores utilizan anotaciones REST de Spring como @RestController, @RequestMapping, @GetMapping, @PostMapping, etc.

Las respuestas HTTP están adaptadas para reflejar correctamente los estados de éxito y error (200, 404, etc).

El sistema puede integrarse fácilmente con herramientas como Swagger/OpenAPI para documentar automáticamente los endpoints.
