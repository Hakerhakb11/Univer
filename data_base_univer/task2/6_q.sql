CREATE OR REPLACE PROCEDURE удалить_неактивных_читателей()
LANGUAGE plpgsql
AS $$
BEGIN
    -- 1. Удаляем сдачи книг, привязанные к выдачам удаляемых читателей
    DELETE FROM Журнал_сдачи
    WHERE ID_Выдачи IN (
        SELECT Журнал_выдачи.ID_Выдачи
        FROM Журнал_выдачи
        WHERE Журнал_выдачи.ID_Читателя IN (
            SELECT Читатели.ID_Читателя
            FROM Читатели
            WHERE Читатели.Дата_регистрации < CURRENT_DATE - INTERVAL '2 years'
              AND Читатели.ID_Читателя NOT IN (
                  SELECT DISTINCT ID_Читателя
                  FROM Журнал_выдачи
                  WHERE Дата_выдачи >= CURRENT_DATE - INTERVAL '2 years'
              )
        )
    );

    -- 2. Удаляем записи о выдачах книг этим читателям
    DELETE FROM Журнал_выдачи
    WHERE ID_Читателя IN (
        SELECT Читатели.ID_Читателя
        FROM Читатели
        WHERE Читатели.Дата_регистрации < CURRENT_DATE - INTERVAL '2 years'
          AND Читатели.ID_Читателя NOT IN (
              SELECT DISTINCT ID_Читателя
              FROM Журнал_выдачи
              WHERE Дата_выдачи >= CURRENT_DATE - INTERVAL '2 years'
          )
    );

    -- 3. Удаляем самих читателей из базы
    DELETE FROM Читатели
    WHERE Читатели.Дата_регистрации < CURRENT_DATE - INTERVAL '2 years'
      AND Читатели.ID_Читателя NOT IN (
          SELECT DISTINCT ID_Читателя
          FROM Журнал_выдачи
          WHERE Дата_выдачи >= CURRENT_DATE - INTERVAL '2 years'
      );

    RAISE NOTICE 'Неактивные читатели и вся история их операций успешно удалены.';
END;
$$;