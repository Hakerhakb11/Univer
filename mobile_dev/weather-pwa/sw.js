const CACHE_NAME = 'v1';

const ASSETS = [
  '/',
  '/index.html',
  '/styles.css',
  '/manifest.json',
  '/js/storage.js',
  '/js/network.js',
  '/js/app.js',
  '/js/navigator-sw.js',
  'https://cdn.jsdelivr.net/npm/localforage@1.10.0/dist/localforage.min.js'
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
  const url = new URL(event.request.url);

  if (url.hostname.includes('nominatim.openstreetmap.org') || url.hostname.includes('api.met.no')) {
    return;
  }

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
