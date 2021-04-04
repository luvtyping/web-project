CREATE TABLE IF NOT EXISTS users (
  user_name VARCHAR(255) NULL,
  birthDate DATE NULL,
  login VARCHAR(255) NOT NULL PRIMARY KEY,
  role VARCHAR(45) NULL DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS books (
  book_name VARCHAR(255) NOT NULL PRIMARY KEY ,
  publishYear INT NULL,
  description VARCHAR(100) NULL,
  price DECIMAL NULL,
  author VARCHAR(255) NULL
  );

CREATE TABLE IF NOT EXISTS cart (
  id SERIAL PRIMARY KEY ,
  user_login VARCHAR(255) NOT NULL REFERENCES users(login),
  book_name VARCHAR(255) NOT NULL REFERENCES books(book_name)
);

CREATE TABLE IF NOT EXISTS authorized_data (
  login VARCHAR(255) PRIMARY KEY references users(login) ,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS books_genres (
  book_name VARCHAR(255) REFERENCES books(book_name),
  genre VARCHAR(255) not null
);