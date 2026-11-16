-- ============================================================
--  AlbumFigus - Esquema de base de datos MySQL (unica BD)
-- ============================================================

CREATE DATABASE IF NOT EXISTS album;

USE album;

CREATE TABLE IF NOT EXISTS usuario (
    id            INT PRIMARY KEY,
    username      VARCHAR(50) NOT NULL UNIQUE,
    monedas       INT NOT NULL,
    sobres_gratis INT NOT NULL DEFAULT 0
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

CREATE TABLE IF NOT EXISTS codigo (
    codigo            VARCHAR(50) PRIMARY KEY,
    recompensamonedas INT NOT NULL,
    recompensasobres  INT NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS canje (
    id_usuario INT NOT NULL,
    codigo     VARCHAR(20) NOT NULL,
    fecha      DATETIME NOT NULL,
    PRIMARY KEY (id_usuario, codigo),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id),
    FOREIGN KEY (codigo)     REFERENCES codigo (codigo)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS configuracion (
    id_usuario     INT PRIMARY KEY,
    visionnocturna BOOLEAN NOT NULL,
    sonido         BOOLEAN NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS revivir (
    id          INT PRIMARY KEY,
    id_usuario  INT NOT NULL,
    consigna    VARCHAR(100) NOT NULL,
    multiplicador INT NOT NULL,
    completado  BOOLEAN NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS sesion (
    id                 INT PRIMARY KEY,
    id_usuario         INT NOT NULL,
    fecha_entrada      DATETIME NOT NULL,
    fecha_salida       DATETIME,
    ultima_recompensa  DATETIME NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id)
) ENGINE = InnoDB;

INSERT IGNORE INTO seleccion (id, nombre, grupo) VALUES
    (1, 'Uruguay', 'A'),
    (2, 'Brasil',  'B'),
    (3, 'Argentina', 'C'),
    (4, 'Francia', 'D'),
    (5, 'Belgica', 'E'),
    (6, 'Croacia', 'F'),
    (7, 'Inglaterra', 'G'),
    (8, 'Canada', 'H'),
    (9, 'Portugal', 'I'),
    (10, 'Espana', 'J'),
    (11, 'Paises Bajos', 'K');

INSERT IGNORE INTO codigo (codigo, recompensamonedas, recompensasobres) VALUES
    ('ITI2026', 500, 1),
    ('MUNDIAL', 1000, 2),
    ('FIGUS', 200, 0);