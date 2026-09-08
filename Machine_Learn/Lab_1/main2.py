mas = [10, -7, 8, -100, -50, 32, 8, 117, -210]

res = min(mas, key=abs)
print(res)

res = max(mas, key=abs)
print(res)

res = sorted(mas, key=abs)
print(res)