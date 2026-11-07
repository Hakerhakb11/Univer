const CACHE_NAME = 'v1';

const ASSETS = [
  './',
  './index.html',
  './manifest.json'
];

self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => {
      console.log('Кэшируем файлы:', ASSETS);
      return cache.addAll(ASSETS);
    }).then(() => self.skipWaiting())
  );
});

self.addEventListener('activate', event => {
  event.waitUntil(clients.claim());
});

self.addEventListener('fetch', event => {
  event.respondWith(
    fetch(event.request)
      .then(response => {
        if (response && response.status === 200) {
          const responseToCache = response.clone();
          caches.open(CACHE_NAME).then(cache => {
            cache.put(event.request, responseToCache);
          });
        }
        return response;
      })
      .catch(() => {
        console.log('Офлайн. Достаем из кэша:', event.request.url);
        return caches.match(event.request);
      })
  );
});

// СТАРЫЙ ЗАГРУЗЧИК КЕША, КОТОРЫЙ НЕ ПОЗВОЛЯЕТ ЗАГРУЖАЕТ НОВУЮ ВЕРСИЮ HTML, изза загрузки кеша старой версии.
// Которая не обновляется сама.
// self.addEventListener('fetch', event => {
//   event.respondWith(caches.match(event.request).then(cached => {
//     if (cached) {
//       console.log('Отдаём из кэша:', event.request.url);
//       return cached;
//     }
//     console.log('Загружаем из сети:', event.request.url);
//     return fetch(event.request);
//   })
//   );
// });
