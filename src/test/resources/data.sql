INSERT INTO books VALUES('book 1',1999,'Short description.',1299,'Author R.T.');
INSERT INTO books_genres VALUES('book 1','Роман');

INSERT INTO books VALUES('book 2',2000,'Short description.',1499,'Second Author');
INSERT INTO books_genres VALUES('book 2','Драма');

INSERT INTO books VALUES('book 3',1999,'Short description.',1600,'Author R.T.');
INSERT INTO books_genres VALUES('book 3','Детектив');

INSERT INTO books VALUES('book 4',1999,'Short description.',1100,'Author');
INSERT INTO books_genres VALUES('book 4','Роман');

INSERT INTO users VALUES('name1','2021-01-01','user1login','USER');
INSERT INTO authorized_data VALUES ('user1login','password1');

INSERT INTO cart VALUES (1,'user1login','book 3');
INSERT INTO cart VALUES (2,'user1login','book 3');