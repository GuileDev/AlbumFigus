# Base de datos de AlbumFigus

## Requisitos

- MySQL Server 8.0 o superior (probado con MySQL 8.0 en `localhost:3306`).
- Driver JDBC: `mysql-connector-j-9.4.0.jar` (en `lib/`, referenciado en
  `nbproject/project.properties`).

## Crear la base y cargar los datos

Con el servidor MySQL corriendo, desde la raíz del proyecto:

```bash
mysql -u root -p --default-character-set=utf8mb4 < db/albumfigus.sql
```

El script es idempotente: se puede ejecutar varias veces sin romper nada.
Crea:

| Elemento | Valor |
|---|---|
| Base de datos | `album` (utf8mb4) |

Tablas: `usuario`, `seleccion`, `figurita`, `almacenamiento`.
Además carga las 11 selecciones y las 32 figuritas (11 Común, 15 Estrella y 6 Especial).

Desde Java, `Persistencia.Usuario` también crea la base y las tablas con
`IF NOT EXISTS` al inicializarse, y seed de selecciones con `INSERT IGNORE`.

## Configuración desde Java

`Persistencia.Conexion` usa por defecto:

| Elemento | Valor |
|---|---|
| Base de datos | `album` |
| Puerto | `3306` |
| Usuario | `root` |
| Contraseña | `1234` |

## Probar la conexión

Ejecutar `Persistencia.ConnSimple` (Run File en NetBeans). Debe mostrar:

```
Conexión exitosa a la base de datos.
```
