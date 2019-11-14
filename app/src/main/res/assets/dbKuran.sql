CREATE TABLE `kuran_sq` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `surja_id` INTEGER,
  `surja` TEXT,
  `ajeti_id` INTEGER,
  `ajeti` TEXT
);

CREATE TABLE `kuran_tr` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `surja_id` INTEGER,
  `surja` TEXT,
  `ajeti_id` INTEGER,
  `ajeti` TEXT
);

CREATE TABLE `kuran_en` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `surja_id` INTEGER,
  `surja` TEXT,
  `ajeti_id` INTEGER,
  `ajeti` TEXT
);

CREATE TABLE `kuran_de` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `surja_id` INTEGER,
  `surja` TEXT,
  `ajeti_id` INTEGER,
  `ajeti` TEXT
);

CREATE TABLE `kategorite_sq` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `kategoria_sq` TEXT,
  `kategoria_tr` TEXT,
  `kategoria_en` TEXT,
  `kategoria_de` TEXT
);

CREATE TABLE `kategori_ajet` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `kategori_id` INTEGER,
  `ajeti_id` INTEGER
);
