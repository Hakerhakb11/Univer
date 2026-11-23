import matplotlib

matplotlib.use("Agg")
import matplotlib.pyplot as plt
import numpy as np
from qiskit import QuantumCircuit, transpile
from qiskit_aer import AerSimulator
from scipy.stats import chisquare


def build_qrng_circuit(n_qubits: int = 9) -> QuantumCircuit:
    """Создает квантовую схему QRNG на n кубитах с вентилями Адамара."""
    qc = QuantumCircuit(n_qubits, n_qubits)
    qc.h(range(n_qubits))  # Наложение гейтов Адамара
    qc.barrier()
    qc.measure(range(n_qubits), range(n_qubits))  # Измерение в Z-базисе
    return qc


def generate_quantum_numbers(
    circuit: QuantumCircuit,
    simulator: AerSimulator,
    count: int = 10,
    max_val: int = 256,
) -> list[int]:
    """Генерирует count случайных чисел в отрезке [0, max_val] методом отсечения."""
    results = []
    batch_size = int(count * 2.5) + 16  # Запас shots с учетом acceptance ~50%
    while len(results) < count:
        job = simulator.run(circuit, shots=batch_size, memory=True)
        samples = job.result().get_memory()
        for bitstring in samples:
            val = int(bitstring, 2)
            if val <= max_val:  # Отсечение значений > max_val
                results.append(val)
                if len(results) == count:
                    break
    return results


def run_statistical_analysis(
    circuit: QuantumCircuit,
    simulator: AerSimulator,
    target_samples: int = 25700,
    max_val: int = 256,
):
    """Сбор выборки N, проверка критерием Хи-квадрат и экспорт гистограммы."""
    collected = []
    batch_size = 50000
    while len(collected) < target_samples:
        job = simulator.run(circuit, shots=batch_size, memory=True)
        for bits in job.result().get_memory():
            val = int(bits, 2)
            if val <= max_val:
                collected.append(val)
                if len(collected) == target_samples:
                    break

    collected = np.array(collected)
    frequencies = np.bincount(collected, minlength=max_val + 1)
    expected_freq = target_samples / (max_val + 1)

    # Проверка равномерности критерием согласия Пирсона (Хи-квадрат)
    chi2_stat, p_value = chisquare(frequencies)

    plt.figure(figsize=(10, 5), dpi=300)
    plt.bar(
        range(max_val + 1),
        frequencies,
        width=0.85,
        color="#2563EB",
        label="Фактическая частота",
    )
    plt.axhline(
        expected_freq,
        color="#DC2626",
        linestyle="--",
        linewidth=1.8,
        label=f"Теоретическая частота ({expected_freq:.1f})",
    )
    plt.title(
        f"Гистограмма QRNG (N = {target_samples})\n"
        f"Диапазон [0, 256], Хи-квадрат p = {p_value:.4f}",
        fontsize=11,
        fontweight="bold",
    )
    plt.xlabel("Сгенерированное число (0 - 256)")
    plt.ylabel("Количество выпадений")
    plt.xlim(-2, max_val + 2)
    plt.legend(loc="upper right")
    plt.grid(axis="y", linestyle=":", alpha=0.6)
    plt.tight_layout()
    plt.savefig("quantum_rng_histogram.png", dpi=300)
    plt.close()

    return collected, frequencies, chi2_stat, p_value


if __name__ == "__main__":
    sim = AerSimulator()
    qc = build_qrng_circuit(n_qubits=9)
    compiled_qc = transpile(qc, sim)

    sample_numbers = generate_quantum_numbers(compiled_qc, sim, count=10, max_val=256)
    print("10 квантовых чисел:", sample_numbers)

    run_statistical_analysis(compiled_qc, sim, target_samples=25700, max_val=256)
