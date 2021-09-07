
truncate table cuenta

drop table cuenta

create table cuenta
(
	cuenta_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	codigo text,
	name text
);

insert into cuenta(codigo, name) select '1', 'Activos';
insert into cuenta(codigo, name) select '2', 'Pasivos';
insert into cuenta(codigo, name) select '3', 'Patrimonio';
insert into cuenta(codigo, name) select '4', 'Ingresos';
insert into cuenta(codigo, name) select '5', 'Gastos';

select c.* from cuenta c;

create table subcuenta
(
	subcuenta_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	codigo text,
	name text,
	cuenta_id int,
	FOREIGN KEY (cuenta_id) REFERENCES cuenta(cuenta_id)
);

insert into subcuenta(codigo, name, cuenta_id) select '11', 'Disponible', 1;
insert into subcuenta(codigo, name, cuenta_id) select '1105', 'Caja', 1;
insert into subcuenta(codigo, name, cuenta_id) select '110505', 'Caja General', 1;
insert into subcuenta(codigo, name, cuenta_id) select '110510', 'Cajas Menores', 1;
insert into subcuenta(codigo, name, cuenta_id) select '121595', 'Otros', 1;

select * from subcuenta s;

insert into subcuenta(codigo, name, cuenta_id) select '21', 'Obligaciones Financieras', 2;
insert into subcuenta(codigo, name, cuenta_id) select '2105', 'Bancos Nacionales', 2;
insert into subcuenta(codigo, name, cuenta_id) select '210505', 'Sobregiros', 2;
insert into subcuenta(codigo, name, cuenta_id) select '210510', 'Pagares', 2;
insert into subcuenta(codigo, name, cuenta_id) select '210515', 'Cartas de Credito', 2;
insert into subcuenta(codigo, name, cuenta_id) select '210520', 'Aceptaciones Bancarias', 2;
insert into subcuenta(codigo, name, cuenta_id) select '2110', 'Bancos del Exterior', 2;
insert into subcuenta(codigo, name, cuenta_id) select '211005', 'Sobregiros', 2;
insert into subcuenta(codigo, name, cuenta_id) select '210510', 'Cartas de Credito', 2;

/*

210520. Aceptaciones Bancarias
2110 Bancos del Exterior
211005. Sobregiros
211015. Cartas de Credito
*/


create table caja_menor
(
	cuenta varchar,
	subcuenta varchar,
	talonario varchar,
	fecha datetime,
	persona varchar,
	detalle varchar,
	cantidad int,
	valor numeric(10,2),
	total numeric(10,2)
)
