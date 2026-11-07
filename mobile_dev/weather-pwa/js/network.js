async function fetchWithRetry(url, options = {}, retries = 3, delay = 1000) {
    try {
        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response;
    } catch (error) {
        if (error.name === 'AbortError') {
            throw error;
        }

        if (retries > 0) {
            console.warn(`⚠️ Ошибка сети. Повторяем запрос... Осталось попыток: ${retries}. Задержка: ${delay}ms`);
            await new Promise(resolve => setTimeout(resolve, delay));
            return fetchWithRetry(url, options, retries - 1, delay * 5);
        }
        throw error;
    }
}

const statusEl = document.getElementById('network-status');

function updateOnlineStatus() {
    if (navigator.onLine) {
        statusEl.textContent = 'Онлайн';
        statusEl.classList.remove('offline');

        const lastCity = cityInput.value.trim();
        if (lastCity) {
            loadWeather(lastCity);
        }
    } else {
        statusEl.textContent = 'Офлайн';
        statusEl.classList.add('offline');
    }
}

window.addEventListener('online', updateOnlineStatus);
window.addEventListener('offline', updateOnlineStatus);

updateOnlineStatus();