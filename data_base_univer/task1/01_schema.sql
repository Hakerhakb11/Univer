CREATE TABLE Жанры (
    ID_Жанра SERIAL PRIMARY KEY,
    Название VARCHAR(100) NOT NULL
);

CREATE TABLE Авторы (
    ID_Автора SERIAL PRIMARY KEY,
    ФИО VARCHAR(150) NOT NULL
);

CREATE TABLE Издательства (
    ID_Издательства SERIAL PRIMARY KEY,
    Название VARCHAR(150) NOT NULL
);

CREATE TABLE Библиотекари (
    ID_Библиотекаря SERIAL PRIMARY KEY,
    ФИО VARCHAR(150) NOT NULL
);

CREATE TABLE Читатели (
    ID_Читателя SERIAL PRIMARY KEY,
    ФИО VARCHAR(150) NOT NULL,
    Номер_билета VARCHAR(50) UNIQUE NOT NULL,
    Дата_рождения DATE NOT NULL,
    Дата_регистрации DATE DEFAULT CURRENT_DATE,
    Телефон VARCHAR(20),
    Адрес TEXT
);

CREATE TABLE Типы_штрафов (
    ID_Штрафа SERIAL PRIMARY KEY,
    Название_штрафа VARCHAR(150) NOT NULL,
    Цена NUMERIC(10, 2) NOT NULL
);

CREATE TABLE Архив_читателей (
    ID_Архива SERIAL PRIMARY KEY,
    Старый_ID_читателя INTEGER NOT NULL,
    ФИО VARCHAR(150) NOT NULL,
    Номер_билета VARCHAR(50) NOT NULL,
    Дата_удаления TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Пользователь_БД VARCHAR(50) DEFAULT current_user
);

CREATE TABLE Серии (
    ID_Серии SERIAL PRIMARY KEY,
    ID_Издательства INTEGER NOT NULL REFERENCES Издательства(ID_Издательства),
    Название VARCHAR(150) NOT NULL
);

CREATE TABLE Книги (
    ID_Книги SERIAL PRIMARY KEY,
    Название VARCHAR(200) NOT NULL,
    ID_Жанра INTEGER REFERENCES Жанры(ID_Жанра) 
);

CREATE TABLE Авторы_и_Книги (
    ID_Автора_и_Книги SERIAL PRIMARY KEY,
    ID_Автора INTEGER NOT NULL REFERENCES Авторы(ID_Автора),
    ID_Книги INTEGER NOT NULL REFERENCES Книги(ID_Книги)
);

CREATE TABLE Издания (
    ID_Издания SERIAL PRIMARY KEY,
    ID_Автора_и_Книги INTEGER NOT NULL REFERENCES Авторы_и_Книги(ID_Автора_и_Книги),
    ID_Серии INTEGER REFERENCES Серии(ID_Серии) ,
    Год_издания INTEGER,
    Цена NUMERIC(10, 2)
);

CREATE TABLE Журнал_выдачи (
    ID_Выдачи SERIAL PRIMARY KEY,
    ID_Читателя INTEGER NOT NULL REFERENCES Читатели(ID_Читателя),
    ID_Издания INTEGER NOT NULL REFERENCES Издания(ID_Издания),
    ID_Библиотекаря INTEGER REFERENCES Библиотекари(ID_Библиотекаря),
    Дата_выдачи DATE DEFAULT CURRENT_DATE,
    Ожидаемая_дата_возврата DATE
);

CREATE TABLE Журнал_сдачи (
    ID_Сдачи SERIAL PRIMARY KEY,
    ID_Выдачи INTEGER NOT NULL REFERENCES Журнал_выдачи(ID_Выдачи),
    Фактическая_дата_возврата DATE DEFAULT CURRENT_DATE
);

CREATE TABLE Штрафы (
    ID_Сдачи INTEGER NOT NULL REFERENCES Журнал_сдачи(ID_Сдачи),
    ID_Штрафа INTEGER NOT NULL REFERENCES Типы_штрафов(ID_Штрафа),
    Сумма_штрафа NUMERIC(10, 2),
    PRIMARY KEY(ID_Сдачи, ID_Штрафа)
);
