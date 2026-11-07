n: int = input("Введите размер массива: ")

n = int(n)

mas: list = []

for i in range(n):
    inp = input(f"Введите число номер {i + 1}: ")
    mas.append(inp)

print("Printed mas:", mas)

if len(mas) > 1:
    mas[0], mas[-1] = mas[-1], mas[0]

print("Output mas:", mas)
