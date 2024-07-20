drop table "SETTING";
drop table "TEAM_INTERACT";
drop table "TEAM_INTERACT_TYPE";
drop table "SANCTION";
drop table "SANCTION_TYPE";
drop table "ROLE_ASSIGMENT";
drop table "ROLE";
drop table "ROLE_GROUP";
drop table "MESSAGE_ATTACHING";
drop table "DELETED_MESSAGE";
drop table "MESSAGE";
drop table "TEAM";
drop table "USER";

-- Создание таблицы с пользователями
CREATE TABLE "USER" (
"user_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"login" nvarchar2(30) UNIQUE not NULL,
"password" nvarchar2(50) not NULL,
"user_name" NVARCHAR2(50),
"email" NVARCHAR2(50),
"is_authorized" CHAR(1) DEFAULT '0' CHECK ("is_authorized" IN ('0','1')) not NULL 
);

-- Создание таблицы с информацией о группах ролей
CREATE TABLE "ROLE_GROUP" (
"role_group_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"role_group_name" nvarchar2(30) UNIQUE not NULL
);

-- Создание таблицы с перечнем всех команд
CREATE TABLE "TEAM" (
"team_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"team_name" nvarchar2(50) UNIQUE not NULL
);

-- Создание таблицы с информацией о ролях пользователей
CREATE TABLE "ROLE" (
"role_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"role_name" nvarchar2(30) UNIQUE not NULL,
"group" int not NULL
);

-- Создание таблицы с историей назначения пользователей на роли
CREATE TABLE "ROLE_ASSIGMENT" (
"role_assigment_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"role" int not NULL,
"sender" int NULL,
"receiver" int not NULL,
"time" date
);

-- Создание таблицы, хранящей настройки СИС
CREATE TABLE "SETTING" (
"id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"name" nvarchar2(30) not NULL UNIQUE,
"value" int
);

-- Создание таблицы с историей действий пользователя по отношению к команде
CREATE TABLE "TEAM_INTERACT" (
"team_interact_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"user" int not NULL,
"type" int not NULL,
"team" int not NULL,
"time" date not NULL
);

-- Создание таблицы с сообщениями пользователей
CREATE TABLE "MESSAGE" (
"message_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"data" nvarchar2(250) not NULL,
"author" int not NULL,
"message_time" date not NULL
);

-- Создание таблицы, хранящей информацию о удаленных сообщениях
CREATE TABLE "DELETED_MESSAGE" (
"del_message_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"sender" int NULL,
"message" int not NULL,
"del_message_time" date not NULL
);

-- Создание таблицы с информацией о санкциях, наложенных на пользователя
CREATE TABLE "SANCTION" (
"sanction_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"type" int not NULL,
"sender" int NULL,
"receiver" int not NULL,
"reason" nvarchar2(100),
"time" date not NULL
);

-- Создание таблицы с информацией о типах санкций
CREATE TABLE "SANCTION_TYPE" (
"sanction_t_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"sanction_t_name" nvarchar2(30) UNIQUE not NULL
);

-- Создание таблицы с информацией о прикреплении сообщений к конктретным командам
CREATE TABLE "MESSAGE_ATTACHING" (
"message_attach_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"team" int not NULL,
"message" int not NULL
);

-- Создание таблицы с информацией о типах действий пользователя по отношению к команде
CREATE TABLE "TEAM_INTERACT_TYPE" (
"type_id" int generated always as identity(start with 1 increment by 1) PRIMARY KEY not NULL,
"type_name" nvarchar2(30) UNIQUE not NULL
);

ALTER TABLE "TEAM_INTERACT" ADD FOREIGN KEY ("user") REFERENCES "USER" ("user_id") ON DELETE CASCADE;

ALTER TABLE "MESSAGE" ADD FOREIGN KEY ("author") REFERENCES "USER" ("user_id") ON DELETE CASCADE;

ALTER TABLE "TEAM_INTERACT" ADD FOREIGN KEY ("team") REFERENCES "TEAM" ("team_id") ON DELETE CASCADE;

ALTER TABLE "ROLE_ASSIGMENT" ADD FOREIGN KEY ("role") REFERENCES "ROLE" ("role_id");

ALTER TABLE "ROLE_ASSIGMENT" ADD FOREIGN KEY ("sender") REFERENCES "USER" ("user_id") ON DELETE SET NULL;

ALTER TABLE "ROLE_ASSIGMENT" ADD FOREIGN KEY ("receiver") REFERENCES "USER" ("user_id") ON DELETE CASCADE;

ALTER TABLE "SANCTION" ADD FOREIGN KEY ("sender") REFERENCES "USER" ("user_id") ON DELETE SET NULL;

ALTER TABLE "SANCTION" ADD FOREIGN KEY ("receiver") REFERENCES "USER" ("user_id") ON DELETE CASCADE;

ALTER TABLE "ROLE" ADD FOREIGN KEY ("group") REFERENCES "ROLE_GROUP" ("role_group_id");

ALTER TABLE "DELETED_MESSAGE" ADD FOREIGN KEY ("message") REFERENCES "MESSAGE" ("message_id") ON DELETE CASCADE;

ALTER TABLE "DELETED_MESSAGE" ADD FOREIGN KEY ("sender") REFERENCES "USER" ("user_id") ON DELETE SET NULL;

ALTER TABLE "SANCTION" ADD FOREIGN KEY ("type") REFERENCES "SANCTION_TYPE" ("sanction_t_id");

ALTER TABLE "MESSAGE_ATTACHING" ADD FOREIGN KEY ("message") REFERENCES "MESSAGE" ("message_id") ON DELETE CASCADE;

ALTER TABLE "MESSAGE_ATTACHING" ADD FOREIGN KEY ("team") REFERENCES "TEAM" ("team_id") ON DELETE CASCADE;

ALTER TABLE "TEAM_INTERACT" ADD FOREIGN KEY ("type") REFERENCES "TEAM_INTERACT_TYPE" ("type_id");


-- Добавление групп ролей
INSERT INTO "ROLE_GROUP" ("role_group_name") VALUES ('Administrator');
INSERT INTO "ROLE_GROUP" ("role_group_name") VALUES ('Moderator');
INSERT INTO "ROLE_GROUP" ("role_group_name") VALUES ('User');


-- Добавление ролей
INSERT INTO "ROLE" ("role_name", "group") VALUES ('Administrator', 1);
INSERT INTO "ROLE" ("role_name", "group") VALUES ('Moderator', 2);
INSERT INTO "ROLE" ("role_name", "group") VALUES ('Expert', 3);
INSERT INTO "ROLE" ("role_name", "group") VALUES ('Common user', 3);


-- Добавление типов санкций
INSERT INTO "SANCTION_TYPE" ("sanction_t_name") VALUES ('Block');
INSERT INTO "SANCTION_TYPE" ("sanction_t_name") VALUES ('Unblock');
INSERT INTO "SANCTION_TYPE" ("sanction_t_name") VALUES ('Mute');
INSERT INTO "SANCTION_TYPE" ("sanction_t_name") VALUES ('Unmute');


-- Добавление типов взаимодействий с командой
INSERT INTO "TEAM_INTERACT_TYPE" ("type_name") VALUES ('Join');
INSERT INTO "TEAM_INTERACT_TYPE" ("type_name") VALUES ('Exit');
INSERT INTO "TEAM_INTERACT_TYPE" ("type_name") VALUES ('Expert_ejected');


-- Добавление настроек
INSERT INTO "SETTING" ("name", "value") VALUES ('max_team_capacity', 7);
INSERT INTO "SETTING" ("name", "value") VALUES ('max_team_consulted_expert', 3);


-- Добавление пользователей
INSERT INTO "USER" ("login", "password") VALUES ('root', '1');
INSERT INTO "USER" ("login", "password") VALUES ('admin', '1');
INSERT INTO "USER" ("login", "password") VALUES ('moderator_1', '1');
INSERT INTO "USER" ("login", "password") VALUES ('moderator_2', '1');
INSERT INTO "USER" ("login", "password") VALUES ('expert_1', '1');
INSERT INTO "USER" ("login", "password") VALUES ('expert_2', '1');
INSERT INTO "USER" ("login", "password", "user_name", "email") VALUES ('dmitry', '1', 'Кузнецов Дмитрий', 'cuznetsov.dmitri2003@yandex.ru');
INSERT INTO "USER" ("login", "password", "user_name", "email") VALUES ('egor', '1', 'Соколов Егор', 'faositb@yandex.ru');
INSERT INTO "USER" ("login", "password") VALUES ('olya', '1');
INSERT INTO "USER" ("login", "password") VALUES ('tiger', '1');
INSERT INTO "USER" ("login", "password") VALUES ('mitter', '1');
INSERT INTO "USER" ("login", "password") VALUES ('cat', '1');
INSERT INTO "USER" ("login", "password") VALUES ('admin_hater', '1');
INSERT INTO "USER" ("login", "password") VALUES ('killer', '1');


-- Назначения ролей пользователям
INSERT INTO "ROLE_ASSIGMENT" ("role", "receiver") VALUES (1, 1);
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (1, 1, 2, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (2, 2, 3, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (2, 2, 4, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (3, 2, 5, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (3, 2, 6, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 7, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 8, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 9, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 10, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 11, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 12, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 13, TO_DATE('23.09.2023', 'dd.mm.yyyy'));
INSERT INTO "ROLE_ASSIGMENT" ("role", "sender", "receiver", "time") VALUES (4, 2, 14, TO_DATE('23.09.2023', 'dd.mm.yyyy'));

-- Создание команд
INSERT INTO "TEAM" ("team_name") VALUES ('JAVA-кодеры');
INSERT INTO "TEAM" ("team_name") VALUES ('Python-исты');


-- Создание взаимодействий с командой
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (5, 1, 1, TO_DATE('23.09.2023 10:01:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (5, 3, 1, TO_DATE('23.09.2023 10:02:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (5, 1, 1, TO_DATE('23.09.2023 10:03:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (5, 2, 1, TO_DATE('23.09.2023 10:04:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (5, 1, 1, TO_DATE('23.09.2023 10:05:52', 'dd.mm.yyyy hh24:mi:ss'));

INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (6, 1, 1, TO_DATE('23.09.2023 11:00:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (6, 2, 1, TO_DATE('23.09.2023 11:01:52', 'dd.mm.yyyy hh24:mi:ss'));

INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (6, 1, 2, TO_DATE('23.09.2023 11:02:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (7, 1, 1, TO_DATE('23.09.2023 12:30:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (8, 1, 2, TO_DATE('23.09.2023 13:30:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (9, 1, 1, TO_DATE('23.09.2023 14:30:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (10, 1, 2, TO_DATE('23.09.2023 15:30:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (11, 1, 1, TO_DATE('23.09.2023 18:00:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (12, 1, 2, TO_DATE('23.09.2023 18:10:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (12, 2, 2, TO_DATE('23.09.2023 18:20:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (12, 1, 1, TO_DATE('23.09.2023 18:21:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (13, 1, 1, TO_DATE('23.09.2023 19:30:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "TEAM_INTERACT" ("user", "type", "team", "time") VALUES (14, 1, 2, TO_DATE('23.09.2023 20:30:52', 'dd.mm.yyyy hh24:mi:ss'));


-- Выдача санкций
INSERT INTO "SANCTION" ("type", "sender", "receiver", "reason", "time") VALUES (1, 3, 13, 'Неадекватное поведение', TO_DATE('23.09.2023 07:09:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "SANCTION" ("type", "sender", "receiver", "reason", "time") VALUES (1, 4, 14, 'Реклама стороних ресурсов', TO_DATE('23.09.2023 14:27:11', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "SANCTION" ("type", "sender", "receiver", "reason", "time") VALUES (1, 3, 11, 'Спам', TO_DATE('23.09.2023 10:30:01', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "SANCTION" ("type", "sender", "receiver", "time") VALUES (2, 3, 11, TO_DATE('23.09.2023 12:30:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "SANCTION" ("type", "sender", "receiver", "reason", "time") VALUES (1, 4, 12, 'Флуд', TO_DATE('23.09.2023 7:10:58', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "SANCTION" ("type", "sender", "receiver", "reason", "time") VALUES (2, 4, 12, 'Пользователь извинился', TO_DATE('23.09.2023 11:32:42', 'dd.mm.yyyy hh24:mi:ss'));


-- Создание сообщений
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Всем привет', 7, TO_DATE('23.09.2023 07:00:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Приветствую всех в нашей команде', 8, TO_DATE('23.09.2023 07:01:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Модер, ты в своём уме?!', 13, TO_DATE('23.09.2023 07:08:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Заходите на сайт geekbrains.ru. Бесплатные курсы каждому 1-ому', 14, TO_DATE('23.09.2023 07:08:55', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Голосуем за 3!', 11, TO_DATE('23.09.2023 07:09:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Голосуем за 3!', 11, TO_DATE('23.09.2023 07:09:57', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Голосуем за 3!', 11, TO_DATE('23.09.2023 07:10:01', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('А я сегодня покушал', 12, TO_DATE('23.09.2023 07:12:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Предлагаю первый вариант', 9, TO_DATE('23.09.2023 07:13:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Давай-те выгоним капитана из команды', 10, TO_DATE('23.09.2023 07:14:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('@expert помоги нам', 9, TO_DATE('23.09.2023 07:15:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Да как это могло быть правильным???', 7, TO_DATE('23.09.2023 07:16:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('@expert Ты понял о чем был вопрос?', 8, TO_DATE('23.09.2023 07:17:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Я лучше поищу другую команду!', 12, TO_DATE('24.09.2023 07:18:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Я expert_1!', 5, TO_DATE('24.09.2023 07:18:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('Я expert_2', 6, TO_DATE('24.09.2023 07:18:53', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "MESSAGE" ("data", "author", "message_time") VALUES ('И снова здравствуйте!', 6, TO_DATE('24.09.2023 07:18:54', 'dd.mm.yyyy hh24:mi:ss'));


--  Пометка сообщений удаленными
INSERT INTO "DELETED_MESSAGE" ("sender", "message", "del_message_time") VALUES (4, 3, TO_DATE('23.09.2023 07:08:59', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "DELETED_MESSAGE" ("sender", "message", "del_message_time") VALUES (4, 4, TO_DATE('23.09.2023 07:09:08', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "DELETED_MESSAGE" ("sender", "message", "del_message_time") VALUES (3, 5, TO_DATE('23.09.2023 07:10:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "DELETED_MESSAGE" ("sender", "message", "del_message_time") VALUES (3, 6, TO_DATE('23.09.2023 07:10:52', 'dd.mm.yyyy hh24:mi:ss'));
INSERT INTO "DELETED_MESSAGE" ("sender", "message", "del_message_time") VALUES (3, 7, TO_DATE('23.09.2023 07:10:52', 'dd.mm.yyyy hh24:mi:ss'));


--  Прикрепление сообщений к команде
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 1);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 2);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 3);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 4);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 5);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 6);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 7);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 8);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 9);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 10);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 11);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 12);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 13);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 14);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (1, 15);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 16);
INSERT INTO "MESSAGE_ATTACHING" ("team", "message") VALUES (2, 17);
