const weatherStore = localforage.createInstance({
    name: 'weather-pwa',
    storeName: 'weather_cache'
});

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