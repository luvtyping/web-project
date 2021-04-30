insert into books values('Book one',1999,'Short description of book one.',1299,'Author A.');
insert into books_genres values('Book one','Роман');

insert into books values('Book two',2021,'Short description of book two.',1499,'Author B.');
insert into books_genres values('Book two','Драма');

insert into books values('Book three',1999,'Short description of book three.',1600,'Author A.');
insert into books_genres values('Book three','Детектив');

insert into books values('Book four',2021,'Short description of book four.',1100,'Author C.');
insert into books_genres values('Book four','Роман');

insert into  users values('name1','2021-01-01','user1login','USER');
insert into  authorized_data values ('user1login','password1');

insert into  cart values  (1,'user1login','Book three');
insert into  cart values  (2,'user1login','Book three');
insert into  cart values  (3,'user1login','Book one');
insert into  cart values  (4,'user1login','Book two');
insert into  cart values  (5,'user1login','Book four');