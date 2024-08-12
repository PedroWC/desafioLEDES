CREATE DATABASE IF NOT EXISTS institute_manager;
USE institute_manager;
CREATE TABLE instituicao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(32) NOT NULL,
    sigla VARCHAR(8) NOT NULL,
    status TINYINT(1) DEFAULT 1
);
CREATE TABLE instituicao_brasileira (
    id INT AUTO_INCREMENT PRIMARY KEY,
    instituicao_id INT NOT NULL,
    pais VARCHAR(100) NOT NULL DEFAULT 'Brasil',
    cnpj VARCHAR(14) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(32) NOT NULL,
    bairro VARCHAR(32) NOT NULL,
    estado VARCHAR(32) NOT NULL,
    municipio VARCHAR(32) NOT NULL,
    numero VARCHAR(8) NOT NULL,
    complemento VARCHAR(16),
    FOREIGN KEY (instituicao_id) REFERENCES instituicao(id) ON DELETE CASCADE
);
CREATE TABLE instituicao_estrangeira (
    id INT AUTO_INCREMENT PRIMARY KEY,
    instituicao_id INT NOT NULL,
    pais VARCHAR(100) NOT NULL,
    cep VARCHAR(9),
    logradouro VARCHAR(32) NOT NULL,
    estado_regiao VARCHAR(32) NOT NULL,
    municipio VARCHAR(32) NOT NULL,
    complemento VARCHAR(32),
    FOREIGN KEY (instituicao_id) REFERENCES instituicao(id) ON DELETE CASCADE
);
