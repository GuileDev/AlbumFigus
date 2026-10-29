-- ============================================================
--  AlbumFigus - Esquema de base de datos MySQL
--  Ejecutar como root:
--    mysql -u root -p --default-character-set=utf8mb4 < db/albumfigus.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS albumfigus
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE albumfigus;

-- ------------------------------------------------------------
-- Tablas
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50) NOT NULL UNIQUE,
    monedas    INT NOT NULL DEFAULT 100
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS seleccion (
    id_seleccion INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(60) NOT NULL,
    grupo        CHAR(1) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS figurita (
    id_figurita  INT AUTO_INCREMENT PRIMARY KEY,
    numero       INT NOT NULL UNIQUE,
    jugador      VARCHAR(80) NOT NULL,
    posicion     VARCHAR(40) NOT NULL,
    rareza       ENUM('Comun', 'Rara', 'Legendaria') NOT NULL DEFAULT 'Comun',
    id_seleccion INT NOT NULL,
    CONSTRAINT fk_figurita_seleccion
        FOREIGN KEY (id_seleccion) REFERENCES seleccion (id_seleccion)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS almacenamiento (
    id_usuario  INT NOT NULL,
    id_figurita INT NOT NULL,
    cantidad    INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id_usuario, id_figurita),
    CONSTRAINT fk_almacenamiento_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_almacenamiento_figurita
        FOREIGN KEY (id_figurita) REFERENCES figurita (id_figurita)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

-- ------------------------------------------------------------
-- Datos iniciales (36 figuritas = 3 paginas de 12)
-- ------------------------------------------------------------

INSERT INTO seleccion (id_seleccion, nombre, grupo) VALUES
    (1, 'Argentina', 'A'),
    (2, 'Brasil',    'B'),
    (3, 'Francia',   'C'),
    (4, 'España',    'D'),
    (5, 'Alemania',  'E'),
    (6, 'Portugal',  'F') AS nuevo
ON DUPLICATE KEY UPDATE nombre = nuevo.nombre, grupo = nuevo.grupo;

INSERT INTO figurita (numero, jugador, posicion, rareza, id_seleccion) VALUES
    ( 1, 'Emiliano Martinez', 'Portero',        'Comun',      1),
    ( 2, 'Cristian Romero',   'Defensa',        'Comun',      1),
    ( 3, 'Rodrigo De Paul',   'Centrocampista', 'Rara',       1),
    ( 4, 'Enzo Fernandez',    'Centrocampista', 'Comun',      1),
    ( 5, 'Lionel Messi',      'Delantero',      'Legendaria', 1),
    ( 6, 'Julian Alvarez',    'Delantero',      'Rara',       1),

    ( 7, 'Alisson Becker',    'Portero',        'Rara',       2),
    ( 8, 'Marquinhos',        'Defensa',        'Comun',      2),
    ( 9, 'Casemiro',          'Centrocampista', 'Comun',      2),
    (10, 'Lucas Paqueta',     'Centrocampista', 'Comun',      2),
    (11, 'Vinicius Junior',   'Delantero',      'Legendaria', 2),
    (12, 'Rodrygo',           'Delantero',      'Rara',       2),

    (13, 'Mike Maignan',      'Portero',        'Comun',      3),
    (14, 'William Saliba',    'Defensa',        'Rara',       3),
    (15, 'Aurelien Tchouameni', 'Centrocampista', 'Comun',    3),
    (16, 'Antoine Griezmann', 'Centrocampista', 'Rara',       3),
    (17, 'Kylian Mbappe',     'Delantero',      'Legendaria', 3),
    (18, 'Ousmane Dembele',   'Delantero',      'Comun',      3),

    (19, 'Unai Simon',        'Portero',        'Comun',      4),
    (20, 'Dani Carvajal',     'Defensa',        'Comun',      4),
    (21, 'Rodri',             'Centrocampista', 'Rara',       4),
    (22, 'Pedri',             'Centrocampista', 'Rara',       4),
    (23, 'Lamine Yamal',      'Delantero',      'Legendaria', 4),
    (24, 'Alvaro Morata',     'Delantero',      'Comun',      4),

    (25, 'Manuel Neuer',      'Portero',        'Rara',       5),
    (26, 'Antonio Rudiger',   'Defensa',        'Comun',      5),
    (27, 'Joshua Kimmich',    'Centrocampista', 'Comun',      5),
    (28, 'Jamal Musiala',     'Centrocampista', 'Rara',       5),
    (29, 'Florian Wirtz',     'Delantero',      'Legendaria', 5),
    (30, 'Kai Havertz',       'Delantero',      'Comun',      5),

    (31, 'Diogo Costa',       'Portero',        'Comun',      6),
    (32, 'Ruben Dias',        'Defensa',        'Rara',       6),
    (33, 'Bruno Fernandes',   'Centrocampista', 'Rara',       6),
    (34, 'Bernardo Silva',    'Centrocampista', 'Comun',      6),
    (35, 'Cristiano Ronaldo', 'Delantero',      'Legendaria', 6),
    (36, 'Rafael Leao',       'Delantero',      'Comun',      6) AS nuevo
ON DUPLICATE KEY UPDATE
    jugador = nuevo.jugador,
    posicion = nuevo.posicion,
    rareza = nuevo.rareza,
    id_seleccion = nuevo.id_seleccion;

-- Usuario de prueba (el album necesita un id_usuario existente)
INSERT INTO usuario (username, monedas) VALUES ('demo', 100) AS nuevo
ON DUPLICATE KEY UPDATE username = nuevo.username;

-- ------------------------------------------------------------
-- Usuario MySQL dedicado para la aplicacion
-- ------------------------------------------------------------

CREATE USER IF NOT EXISTS 'albumfigus'@'localhost'
    IDENTIFIED BY 'albumfigus2026';

ALTER USER 'albumfigus'@'localhost'
    IDENTIFIED BY 'albumfigus2026';

GRANT SELECT, INSERT, UPDATE, DELETE ON albumfigus.* TO 'albumfigus'@'localhost';
