# albumfigus

aplicacion de escritorio en java (swing) sobre netbeans.
album de figuritas de futbol con sobres, mazo de repetidas y configuracion.

## estructura

- `src/logic/`: logica del juego (album, figurita, seleccion, fachada, sobres, packs, principal).
- `src/graphic/`: interfaces graficas (login, lobby, index, configuracion, mazo repetidas, preferencias).
- `src/Persistencia/`: acceso a base de datos (conexion, usuario, conn simple).
- `db/`: script sql y readme de la base.
- `lib/`: conector mysql.
- `src/ImagenesParaDiseño/`: imagenes del juego.

## requisitos

- java 8 o superior.
- mysql server 8.0+ en localhost:3306.
- netbeans (opcional, para editar los forms).

## ejecucion

1. crear la base de datos: `mysql -u root -p --default-character-set=utf8mb4 < db/albumfigus.sql`
2. compilar y ejecutar `logic.Principal`.

## notas

- codigo y comentarios en minusculas y simples.
- funciones documentadas con javadoc.
- en el lobby, `monedastext` muestra "monedas: #" con la cantidad actual.