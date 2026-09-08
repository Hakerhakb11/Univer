string = 'Pyth1abch2hon'

new_string: str = ""

for index, char in enumerate(string):
    if char == "h":
        break

first_index = index

string2 = string[::-1]


for index, char in enumerate(string2):
    if char == "h":
        break


print(first_index)
print(index)

print(string)

index = len(string) - 1 - index
result = string[first_index + 1 : index]
result = result[::-1]
print(result)