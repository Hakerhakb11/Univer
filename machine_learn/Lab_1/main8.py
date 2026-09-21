def print_pyramid(n: int) -> None:
    for i in range(1, n + 1):
        up = ''.join(str(x) for x in range(1, i + 1))
        down = ''.join(str(x) for x in range(i - 1, 0, -1))

        print(' ' * (n - i) + up + down)


n = 5
print_pyramid(n)
