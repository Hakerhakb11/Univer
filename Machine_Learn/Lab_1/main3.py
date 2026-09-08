n:int = input("Введите размер массива: ")

n = int(n)

mas:list = []

for i in range(n):
    inp = input(f"Введите число номер {i + 1}: ")
    mas.append(inp)

print("Printed mas:", mas)

first_num = mas.pop(0)

second_num = mas.pop()

mas.insert(0, second_num)
mas.insert(-1, first_num)

print("Output mas:", mas)