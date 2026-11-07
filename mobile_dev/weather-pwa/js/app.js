const form = document.getElementById('weather-form');
const cityInput = document.getElementById('city-input');
const temperatureEl = document.getElementById('temperature');
const cityNameEl = document.getElementById('city-name');

let currentAbortController = null;

async function loadWeather(city) {
    if (currentAbortController) {
        currentAbortController.abort();
    }

    currentAbortController = new AbortController();
    const { signal } = currentAbortController;

    try {
        temperatureEl.textContent = 'Загрузка...';
        cityNameEl.textContent = '';

        const encodedCity = encodeURIComponent(city);
        const geoUrl = `https://nominatim.openstreetmap.org/search?q=${encodedCity}&format=json&limit=1&accept-language=ru`;

        const geoResponse = await fetchWithRetry(geoUrl, { signal });
        const geoData = await geoResponse.json();

        if (!geoData || geoData.length === 0) {
            temperatureEl.textContent = 'Город не найден';
            return;
        }

        const { lat, lon, display_name } = geoData[0];
        const latitude = parseFloat(lat);
        const longitude = parseFloat(lon);
        const name = display_name.split(',')[0];

        const weatherUrl = `https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=${latitude}&lon=${longitude}`;

        const weatherResponse = await fetchWithRetry(weatherUrl, { signal });
        const weatherData = await weatherResponse.json();

        const temp = weatherData.properties.timeseries[0].data.instant.details.air_temperature;

        cityNameEl.textContent = name;
        temperatureEl.textContent = `Температура: ${temp} °C`;

        await saveWeatherToCache(city, { name, temp });

    } catch (error) {
        if (error.name === 'AbortError') {
            console.log('Предыдущий запрос был отменен.');
            return;
        }

        console.error('Ошибка сети, пробуем достать из IndexedDB кэша...');

        const cached = await getWeatherFromCache(city);

        if (cached) {
            const { name, temp } = cached.data;
            cityNameEl.textContent = `${name} (из IndexedDB кеша)`;
            temperatureEl.textContent = `Температура: ${temp} °C ⚠️ (офлайн-режим)`;
        } else {
            temperatureEl.textContent = 'Ошибка загрузки';
            cityNameEl.textContent = '';
        }
    }
}

form.addEventListener('submit', (e) => {
    e.preventDefault();
    const city = cityInput.value.trim();
    if (city) {
        loadWeather(city);
    }
});

// const weatherStore = localforage.createInstance({
//     name: 'weather-pwa',
//     storeName: 'weather_cache'
// });

async function saveWeatherToCache(city, data) {
    try {
        await weatherStore.setItem(city.toLowerCase(), {
            data: data,
            timestamp: Date.now()
        });
    } catch (err) {
        console.error('Ошибка сохранения:', err);
    }
}

async function getWeatherFromCache(city) {
    try {
        const cached = await weatherStore.getItem(city.toLowerCase());
        if (!cached) return null;

        const age = Date.now() - cached.timestamp;
        const maxAge = 24 * 60 * 60 * 1000;

        return {
            data: cached.data,
            isStale: age > maxAge
        };
    } catch (err) {
        return null;
    }
}