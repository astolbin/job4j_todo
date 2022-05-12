create table if not exists items (
   id serial primary key not null,
   description varchar(200),
   created timestamp,
   done boolean
);