from datetime import datetime


def print_datetime_info() -> None:
    now = datetime.now()

    target_date = datetime(2026, 7, 1)

    days_left = (target_date - now).days

    print("Сегодняшняя дата: {}".format(now.strftime("%d.%m.%Y")))
    print("Текущее время: {}".format(now.strftime("%H:%M:%S")))

    print(f"Номер дня недели: {now.weekday()}")

    if days_left >= 0:
        print(f"Дней до 1 июля 2026 года: {days_left}")
    else:
        print(f"С 1 июля 2026 года уже прошло дней: {abs(days_left)}")


if __name__ == "__main__":
    print_datetime_info()
