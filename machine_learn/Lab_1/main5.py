import random


def printMatrix(matrix: list[list[int]]) -> None:
    for row in matrix:
        formatted_row = "".join(f"{num:>4}" for num in row)
        print(formatted_row)


if __name__ == "__main__":
    N, M = 5, 6
    matrix = [[random.randint(20, 80) for _ in range(M)] for _ in range(N)]

    printMatrix(matrix)
