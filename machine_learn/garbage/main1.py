def manage_dictionary(letters: list[str], numbers: list[int]):
    my_dict = dict(zip(letters, numbers))

    print('Текущий словарь:')
    print(my_dict)
    print('-' * 30)

    key = input('Введите ключ для поиска: ').strip()

    if key in my_dict:
        print(f"Значение для ключа '{key}': {my_dict[key]}")
    else:
        print(f"Ключ '{key}' не найден.")

        while True:
            new_value = input(f"Введите целое число для нового ключа '{key}': ")
            if new_value.isdigit():
                int(new_value)
                break
            else:
                print('Введите целое число')

        my_dict[key] = new_value
        print(f"Пара '{key}': {new_value} успешно добавлена в словарь.")
        print('-' * 30)

    with open('file-1.txt', 'w', encoding='utf-8') as file:
        file.writelines(f'{k}: {v}\n' for k, v in my_dict.items())


english_letters = ['a', 'b', 'c', 'd', 'e']
integer_values = [10, 20, 30, 40, 50]

manage_dictionary(english_letters, integer_values)
