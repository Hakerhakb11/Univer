import numpy as np
import pandas as pd

seq = np.linspace(0, 25, 20)
s = pd.Series(seq)

ratios = s / s.shift(-1)

filtered_ratios = ratios[ratios <= 0.85]

mean_ratio = filtered_ratios.mean()
result_1 = round(mean_ratio, 3)

print(f'Задание 1 Среднее значение: {result_1}\n')


try:
    transactions = pd.read_csv('data/transactions.csv', nrows=1000000, sep=',')
    tr_types = pd.read_csv('data/tr_types.csv', sep=';')

    print('Задание 2 transactions:')
    print(transactions.head(3))
    print('\n tr_types:')
    print(tr_types.head(3))
    print('\n')

    sample_df = tr_types.sample(n=100, random_state=242)

    has_plata_mask = (
        sample_df['tr_description'].str.lower().str.contains('плата', na=False)
    )

    proportion = has_plata_mask.mean()

    result_3 = f'{proportion:.2f}'

    print(f"Задание 3 со словом 'плата': {result_3}")

except FileNotFoundError:
    print("Ошибка нету data folder")
