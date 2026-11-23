string = "Pyth1abch2hon"

first_index = string.find("h")

last_index = string.rfind("h")

start = string[: first_index + 1]
middle = string[first_index + 1 : last_index]
end = string[last_index:]

middle = middle[::-1]
result = start + middle + end

print(result)
