# Manual de usuario backend

Bienvenid@ al manual de usuario de backend Retromatic. En este espacio encontrarás las entidades responsables de que el back funcione, sus relaciones y los métodos escenciales para la lógica de negocio.

---
### Las tecnologías utilizadas son las siguientes:
* SpringBoot 
    * JPA
    * Spring Web
    * Lombok
    * SpringDoc
* Supabase (BBDD)
* Render (API) - Uptime Robot

### Las principales entidades son:
* Juego
    * Categoria
    * Clasificación
    * Compannia
    * Modalidad
    * Plataforma
* Venta 
    * Estado
    * MetodoPago
    * VentaJuego
* Usuario
    * Rol (admin/cliente)
    * Comuna
    * Region

---
## Links principales
#### Link API Render
Tal como se nombró anteriormente, la API está en render en https://backend-retromatic.onrender.com/

#### Link Swagger

El swagger se encuentra en la siguiente dirección: [Ir al swagger](https://backend-retromatic.onrender.com/doc/swagger-ui/index.html#/)

---
# Funcionamiento Login - Register

**Nota:** En un futuro se integrará la feature de JWT para la seguridad

## Register

El registro contempla las siguientes entradas:
- nombre (String)
- apellido (String)
- correo (String)
- contrasenna (String)
- comunaId (Long) --hereda regionId
- direccion (String)

#### Endpoint
```
POST /auth/register
https://backend-retromatic.onrender.com/auth/register
```

#### Request Body (JSON)
```
{
  "nombre": "Joaquin",
  "apellido": "Melendez",
  "correo": "joaquin@melendez.com",
  "contrasenna": "123456"
  "comunaId": 1,
  "direccion": "Calle falsa 123"
}
```

**NOTA:** No se especifica Rol debido a que default es 1 (Cliente). Para agregar un admin solo se puede desde la misma BBDD por lógica de negocio.

## Login

El login se divide en **LoginRequest** y **Login Response**

## Login Request

Recibe:
* correo String
* contrasenna String

#### Endpoint
```
POST /auth/login
https://backend-retromatic.onrender.com/auth/login
```

#### Request Body (JSON)
```
{
  "correo": "joaquin@melendez.com"
  "contrasenna": "123456"
}
```

## Login Response

**RESPONSE 200**
```
{
  "id": 5,
  "nombre": "Joaquín",
  "apellido": "Meléndez",
  "correo": "usuario@example.com",
  "rol": "CLIENTE"
}
```

**RESPONSE 401 UNAUTHORIZED**
```
"Correo o contraseña incorrectos"
```

**RESPONSE 500**
```
Problema con BBDD o error inesperado.
```


# Funcionamiento Gestión de Juegos (Catálogo)

Los juegos poseen relaciones N–N con:

- Categorías  
- Plataformas  
- Modalidades  
- Compañías  
- Clasificación (1–N)

Este módulo permite que un administrador pueda:

- Crear juegos  
- Editar juegos  
- Eliminar juegos  
- Listar todo el catálogo  
- Obtener un juego por ID  

En un futuro se integrará seguridad con JWT y roles para restringir operaciones administrativas.

---

# 1. Modelo de entrada: JuegoRequest

Para crear o editar un juego, el backend recibe un objeto `JuegoRequest` con los siguientes campos:


titulo           String
descripcion      String 
precio           Integer
urlPortada       String
clasificacionId  Long 
categoriaIds     List<Long>
plataformaIds    List<Long>
modalidadIds     List<Long> 
companiaIds      List<Long> 

---

# 2. Crear Juego

## Endpoint
```
POST /juegos
https://backend-retromatic.onrender.com/juegos
```

## Request Body (JSON)
```json
{
  "titulo": "Hollow Knight",
  "descripcion": "Un metroidvania desafiante.",
  "precio": 9990,
  "urlPortada": "https://i.ibb.co/imagen.webp",
  "clasificacionId": 1,
  "categoriaIds": [1, 2],
  "plataformaIds": [1, 3],
  "modalidadIds": [1],
  "companiaIds": [2]
}
```

## Response 201 CREATED
```json
{
  "id": 10,
  "titulo": "Hollow Knight",
  "descripcion": "Un metroidvania desafiante.",
  "precio": 9990,
  "urlPortada": "https://i.ibb.co/imagen.webp",
  "clasificacion": { "id": 1, "nombre": "T" }
}
```

---

# 3. Editar Juego

## Endpoint
```
PUT /juegos/{id}
```

## Ejemplo
```
PUT /juegos/10
```

## Request Body (JSON)
```json
{
  "titulo": "Hollow Knight (Actualizado)",
  "descripcion": "Metroidvania de culto.",
  "precio": 10990,
  "urlPortada": "https://i.ibb.co/nueva.webp",
  "clasificacionId": 2,
  "categoriaIds": [1],
  "plataformaIds": [2, 3],
  "modalidadIds": [1],
  "companiaIds": [2]
}
```

## Response 200 OK
```json
{
  "id": 10,
  "titulo": "Hollow Knight (Actualizado)",
  "precio": 10990
}
```

---

# 4. Eliminar Juego

## Endpoint
```
DELETE /juegos/{id}
```

## Ejemplo
```
DELETE /juegos/10
```

## Response 204 NO CONTENT
Sin contenido en la respuesta.

---

# 5. Listar Todos los Juegos (Catálogo)

## Endpoint
```
GET /juegos
```

## Response 200 OK
```json
[
  {
    "id": 1,
    "titulo": "God of War",
    "precio": 29990,
    "clasificacion": { "id": 2, "nombre": "M" }
  },
  {
    "id": 2,
    "titulo": "Hollow Knight",
    "precio": 9990
  }
]
```

---

# 6. Obtener un Juego por ID

## Endpoint
```
GET /juegos/{id}
```

## Ejemplo
```
GET /juegos/2
```

## Response 200 OK
```json
{
  "id": 2,
  "titulo": "Hollow Knight",
  "precio": 9990,
  "clasificacion": {
    "id": 1,
    "nombre": "T"
  }
}
```

---

# Notas sobre relaciones

- Antes de editar un juego, el backend elimina todas las relaciones existentes en:
  - juego_categoria
  - juego_plataforma
  - juego_modalidad
  - juego_compannia

- Luego recrea las nuevas relaciones enviadas en el `JuegoRequest`.

- Esto asegura consistencia y evita duplicados.

# Funcionamiento Ventas y Carrito

El carrito de un usuario se representa mediante una entidad `Venta` con:

- `estado = PENDIENTE` (carrito activo)  
- `usuario` (dueño del carrito)  
- `juegos` (lista de `VentaJuego`)  
- `total` (suma de `precio * cantidad`)  
- `metodoPago` (solo en ventas confirmadas)  
- `fechaHora` (solo en ventas confirmadas)

Cuando se confirma la compra:

- El estado pasa de `PENDIENTE` a `PAGADO`.  
- Se registra la fecha y el método de pago.  
- Para un nuevo proceso de compra se podrá crear una nueva venta `PENDIENTE`.

---

## 1. Cargar el carrito de un usuario

### Endpoint
```
GET /ventas/carrito/{usuarioId}
```

### Ejemplo de respuesta
```json
{
  "id": 15,
  "estado": "PENDIENTE",
  "usuario": { "id": 5, "nombre": "Joaquín" },
  "juegos": [
    {
      "id": 1,
      "juego": { "id": 10, "titulo": "Hollow Knight" },
      "precio": 9990,
      "cantidad": 2
    }
  ],
  "total": 19980
}
```
---

## 2. Añadir un juego al carrito (sumar una unidad)

### Endpoint
```
POST /ventas/carrito/{usuarioId}/agregar/{juegoId}
```

---

## 3. Decrementar una unidad de un ítem del carrito

### Endpoint
```
POST /ventas/carrito/{usuarioId}/item/{ventaJuegoId}/decrementar
```
---

## 4. Eliminar completamente un ítem del carrito

### Endpoint
```
DELETE /ventas/carrito/{usuarioId}/item/{ventaJuegoId}
```

---

## 5. Vaciar el carrito

### Endpoint
```
DELETE /ventas/carrito/{usuarioId}
```

---

## 6. Confirmar la compra (pagar el carrito)

### Endpoint
```
POST /ventas/carrito/{usuarioId}/confirmar/{metodoPagoId}
```

### Ejemplo de respuesta
```json
{
  "id": 22,
  "estado": "PAGADO",
  "usuario": { "id": 5, "nombre": "Joaquín" },
  "juegos": [
    {
      "id": 1,
      "juego": { "id": 10, "titulo": "Hollow Knight" },
      "precio": 9990,
      "cantidad": 2
    }
  ],
  "total": 19980,
  "fechaHora": "2025-11-22T16:10:00",
  "metodoPago": { "id": 1, "nombre": "Tarjeta de crédito" }
}
```

---

## 7. Registro de ventas para el administrador

```
GET /ventas/pagadas
```

Devuelve
```
[
  {
    "id": 51,
    "estado": "PAGADO",
    "usuario": {
      "id": 5,
      "nombre": "Joaquín",
      "apellido": "Meléndez",
      "correo": "usuario@example.com"
    },
    "juegos": [
      {
        "id": 101,
        "precio": 9990,
        "cantidad": 2,
        "juego": {
          "id": 10,
          "titulo": "Hollow Knight"
        }
      }
    ],
    "total": 19980,
    "metodoPago": {
      "id": 1,
      "nombre": "Tarjeta de crédito"
    },
    "fechaHora": "2025-02-10T14:22:33"
  }
]
```


---

## 8. Cálculo del total de la venta
```
GET /ventas/carrito/{usuarioId}
```

Devuelve:

```
{
  "id": 15,
  "estado": "PENDIENTE",
  "juegos": [
    {
      "id": 1,
      "juego": { "id": 10, "titulo": "Hollow Knight" },
      "precio": 9990,
      "cantidad": 2
    }
  ],
  "total": 19980
}
```

Del cual se puede consumir el total
---

# Filtros del Catálogo

Los filtros permiten obtener juegos según distintos atributos del catálogo:

* Compañía  
* Plataforma  
* Modalidad  
* Categoría  
* Clasificación  

Todo mediante **un solo endpoint flexible**, lo que facilita la integración desde el frontend.

---

## Filtro Unificado

El filtro acepta distintos **RequestParam opcionales**.  
Cada parámetro corresponde a un ID de la tabla asociada.

### Parámetros disponibles:

| Parámetro | Tipo 
|----------|------
| companniaId | Long 
| plataformaId | Long 
| modalidadId | Long 
| categoriaId| Long 
| clasificacionId | Long 

---

## Endpoint

```
GET /juegos/filtrar
https://backend-retromatic.onrender.com/juegos/filtrar
```

---

## Ejemplos de uso

### Sin filtros (trae todos los juegos)
```
GET /juegos/filtrar
```

---

### Filtrar por Compañía
```
GET /juegos/filtrar?companniaId=1
```

---

### Filtrar por Plataforma
```
GET /juegos/filtrar?plataformaId=2
```

---

### Filtrar por Compañía + Plataforma
(Devuelve SOLO juegos que cumplan ambos filtros)

```
GET /juegos/filtrar?companniaId=1&plataformaId=2
```

---

### Filtro más complejo (3 parámetros)
```
GET /juegos/filtrar?modalidadId=3&categoriaId=4&clasificacionId=1
```

Todos los parámetros son opcionales → se combinan con **AND** (intersección).

---

## Response

### **RESPONSE 200 OK**
Devuelve una lista de juegos filtrados.  
Ejemplo:

```json
[
  {
    "id": 12,
    "nombre": "The Witcher 3",
    "precio": 19990,
    "descripcion": "RPG de mundo abierto",
    "imagenUrl": "https://i.ibb.co/.../witcher3.webp",
    "clasificacion": { ... },
    "plataformas": [ ... ],
    "companias": [ ... ],
    "modalidades": [ ... ],
    "categorias": [ ... ]
  }
]
```

---

### **RESPONSE 200 con lista vacía**
Si ningún juego cumple las condiciones:

```json
[]
```

---

### **RESPONSE 500**
```
Problema con BBDD o error inesperado.
```

---

## Notas Importantes

* **Puedes combinar los filtros en cualquier orden**, todos son opcionales.  
* **Si envías más de un parámetro**, el backend hace una **intersección**, es decir:  
  → “Dame juegos que cumplan TODOS los filtros seleccionados.”  
* Si no se envía ningún filtro → se devuelven **todos los juegos**.  
* El endpoint fue diseñado para que el frontend pueda activar/desactivar filtros fácilmente.


#Datos base
## Usuario

### Region
```
ID | Nombre
------------------------
1  | Region Metropolitana
```

### Comuna
```
ID | Nombre  | Region
---|---------|---------
1  | Maipu   |   1
2  |Cerrillos|   1
3  |Pudahuel |   1
```

### Rol
```
ID | Nombre
------------------------
1  | CLIENTE
1  | ADMIN
```


## Juegos

### compannia
```
ID | Nombre  
---|---------
1  | Team Cherry
2  | Santa Monica
```

### clasificacion
```
ID | codigo  | edad_minima
---|---------|---------
1  |   E     |   0
2  |   T     |   10
```

### categoria
```
ID | Nombre
------------------------
1  | Metroidvania
2  | Aventura
3  | Accion
```

### modalidad
```
ID | Nombre
------------------------
1  | online
2  | offline
```

### modalidad
```
ID | Nombre
------------------------
1  | PS5
2  | Nintendo Switch
```


## Ventas

### metodoPago
```
ID | Nombre  
---|---------
1  | Credito
2  | Debito