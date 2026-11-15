-- ============================================================
--  AlbumFigus - Esquema de base de datos MySQL
--  Ejecutar como root:
--    mysql -u root -p --default-character-set=utf8mb4 < db/albumfigus.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS album
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE album;

-- ------------------------------------------------------------
-- Tablas
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS usuario (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    monedas  INT NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS seleccion (
    id     INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    grupo  CHAR(1)     NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS figurita (
    id           INT PRIMARY KEY,
    numero       INT          NOT NULL,
    jugador      VARCHAR(100) NOT NULL,
    posicion     VARCHAR(30)  NOT NULL,
    rareza       VARCHAR(20)  NOT NULL,
    id_seleccion INT          NOT NULL,
    FOREIGN KEY (id_seleccion) REFERENCES seleccion (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS almacenamiento (
    id_usuario  INT NOT NULL,
    id_figurita INT NOT NULL,
    cantidad    INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id_usuario, id_figurita),
    FOREIGN KEY (id_usuario)  REFERENCES usuario (id),
    FOREIGN KEY (id_figurita) REFERENCES figurita (id)
) ENGINE = InnoDB;

-- ------------------------------------------------------------
-- Selecciones iniciales
-- ------------------------------------------------------------

INSERT IGNORE INTO seleccion (id, nombre, grupo) VALUES
    (1, 'Uruguay', 'A'),
    (2, 'Brasil',  'B'),
    (3, 'Argentina', 'C'),
    (4, 'Francia', 'D'),
    (5, 'Bélgica', 'E'),
    (6, 'Croacia', 'F'),
    (7, 'Inglaterra', 'G'),
    (8, 'Canadá', 'H'),
    (9, 'Portugal', 'I'),
    (10, 'España', 'J'),
    (11, 'Países Bajos', 'K');

-- ------------------------------------------------------------
-- Figuritas (32 = 11 Común / 15 Estrella / 6 Especial)
-- ------------------------------------------------------------

INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (1, 1, 'Sergio Rochet', 'Portero', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (2, 2, 'Sebastián Cáceres', 'Defensa', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (3, 3, 'Ronald Araujo', 'Defensa', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (4, 4, 'José María Giménez', 'Defensa', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (5, 5, 'Manuel Ugarte', 'Mediocampista', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (6, 6, 'Federico Valverde', 'Mediocampista', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (7, 7, 'Rodrigo Bentancur', 'Mediocampista', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (8, 8, 'Nahitan Nández', 'Mediocampista', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (9, 9, 'Maximiliano Araújo', 'Delantero', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (10, 10, 'Luis Suárez', 'Delantero', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (11, 11, 'Darwin Núñez', 'Delantero', 'Común', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (12, 12, 'Vinícius Júnior', 'Delantero', 'Estrella', 2);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (13, 13, 'Lionel Messi', 'Delantero', 'Estrella', 3);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (14, 14, 'Kylian Mbappé', 'Delantero', 'Estrella', 4);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (15, 15, 'Romelu Lukaku', 'Delantero', 'Estrella', 5);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (16, 16, 'Neymar Jr.', 'Delantero', 'Estrella', 2);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (17, 17, 'Luka Modrić', 'Mediocampista', 'Estrella', 6);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (18, 18, 'Ángel Di María', 'Delantero', 'Estrella', 3);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (19, 19, 'Harry Kane', 'Delantero', 'Estrella', 7);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (20, 20, 'Jude Bellingham', 'Mediocampista', 'Estrella', 7);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (21, 21, 'Alphonso Davies', 'Defensa', 'Estrella', 8);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (22, 22, 'Cristiano Ronaldo', 'Delantero', 'Estrella', 9);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (23, 23, 'Bruno Fernandes', 'Mediocampista', 'Estrella', 9);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (24, 24, 'Federico Valverde', 'Mediocampista', 'Estrella', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (25, 25, 'Paul Pogba', 'Mediocampista', 'Estrella', 4);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (26, 26, 'Enzo Fernández', 'Mediocampista', 'Estrella', 3);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (27, 27, 'Lamine Yamal', 'Delantero', 'Especial', 10);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (28, 28, 'Antony Robinson', 'Defensa', 'Especial', 7);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (29, 29, 'Darwin Núñez', 'Delantero', 'Especial', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (30, 30, 'Virgil van Dijk', 'Defensa', 'Especial', 11);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (31, 31, 'Federico Valverde', 'Mediocampista', 'Especial', 1);
INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (32, 32, 'Lautaro Martínez', 'Delantero', 'Especial', 3);
