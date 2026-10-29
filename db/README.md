# Base de datos de AlbumFigus

## Requisitos

- MySQL Server 8.0 o superior (probado con MySQL 26.10 en `localhost:3306`).
- Driver JDBC: `mysql-connector-j-26.10.0.jar` (ya referenciado en
  `nbproject/project.properties`).

## Crear la base y el usuario

Con el servidor MySQL corriendo, desde la raíz del proyecto:

```bash
mysql -u root -p --default-character-set=utf8mb4 < db/albumfigus.sql
```

El script es idempotente: se puede ejecutar varias veces sin romper nada.
Crea:

| Elemento | Valor |
|---|---|
| Base de datos | `albumfigus` (utf8mb4) |
| Usuario MySQL | `albumfigus`@`localhost` |
| Contraseña | `albumfigus2026` |
| Permisos | `SELECT, INSERT, UPDATE, DELETE` sobre `albumfigus.*` |

Tablas: `usuario`, `seleccion`, `figurita`, `almacenamiento`.
Además carga 6 selecciones y 36 figuritas de ejemplo (3 páginas de 12),
y un usuario de prueba `demo` con id `1`.

## Configuración desde Java

`logic.Conexion` usa esos valores por defecto. Se pueden sobrescribir sin
recompilar con propiedades del sistema:

```bash
java -Dalbumfigus.db.url="jdbc:mysql://localhost:3306/albumfigus" \
     -Dalbumfigus.db.usuario=albumfigus \
     -Dalbumfigus.db.password=albumfigus2026 \
     -cp ... logic.Principal
```

o con las variables de entorno `ALBUMFIGUS_DB_URL`,
`ALBUMFIGUS_DB_USUARIO` y `ALBUMFIGUS_DB_PASSWORD`.

## Probar la conexión

Ejecutar `logic.ConnSimple` (Run File en NetBeans). Debe mostrar:

```
Conexión exitosa a la base de datos.
Motor: MySQL 26.10.0
Base de datos: albumfigus
Figuritas cargadas: 36
```
