number = input("Введите число от 0 до 10")

if number.isdigit():
    number = int(number)
    for _ in range(number):

        print("* " * number)
