def sum_1(n) -> int:
    result = 0
    for i in range(n):
        result += 1/(i+1)
    return result

def sum_2(n, m):
    result = 0
    for i in range(m, n):
        result += m ** n
    return result

def select_operation(operation):
    if operation == 1:
        return sum_1
    if operation == 2:
        return sum_2

print(sum_1(3))
print(sum_2(3, 3))
print(select_operation(1)(3))
