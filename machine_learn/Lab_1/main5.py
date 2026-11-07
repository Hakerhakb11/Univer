import random


def printMatrix(matrix: list[list[int]]) -> None:
    for row in matrix:
        template = "{:>4}" * len(row)
        print(template.format(*row))


N, M = 5, 6

matrix = []
for i in range(N):
    row = []
    for j in range(M):
        num = random.randint(20, 80)
        row.append(num)
    matrix.append(row)

printMatrix(matrix)
