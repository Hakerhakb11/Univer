CREATE OR REPLACE PROCEDURE удалить_неактивных_читателей()
AS $$
BEGIN
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

    DELETE FROM Читатели
    WHERE Читатели.Дата_регистрации < CURRENT_DATE - INTERVAL '2 years'
      AND Читатели.ID_Читателя NOT IN (
          SELECT DISTINCT ID_Читателя
          FROM Журнал_выдачи
          WHERE Дата_выдачи >= CURRENT_DATE - INTERVAL '2 years'
      );

END;

$$ LANGUAGE plpgsql;
