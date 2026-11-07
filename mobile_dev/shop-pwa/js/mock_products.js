// === Мок-данные ===
const products = Array.from({ length: 40 }, (_, i) => ({
    id: i + 1,
    title: `Товар №${i + 1}`,
    price: (i + 1) * 100,
    image: `https://picsum.photos/seed/${i + 1}/200`, // найти свое хранилище картинок
    description: `Подробное описание товара №${i + 1}. Отличный выбор для тех, кто ценит качество и стиль.`
}));

const getProduct = id => products.find(p => p.id === Number(id));

// === Экраны ===
const screens = {
    home: () => `
    <div class="product-list">
      ${products.map(p => `
        <a href="/product/${p.id}" class="product-card" data-link>
          <img src="${p.image}" alt="">
          <div class="info">
            <div class="title">${p.title}</div>
            <div class="price">${p.price} ₽</div>
          </div>
        </a>
      `).join('')}
    </div>
  `,

    product: (params) => {
        const p = getProduct(params.id);
        if (!p) return `<div class="screen"><h2>Товар не найден</h2></div>`;
        return `
      <div class="screen">
        <div class="product-detail">
          <img src="${p.image}" alt="">
          <h3>${p.title}</h3>
          <div class="price">${p.price} ₽</div>
          <div class="description">${p.description}</div>
          <button class="buy-btn">Купить</button>
        </div>
      </div>
    `;
    },

    profile: () => `
    <div class="screen">
      <div class="profile-card">
        <div class="avatar">👤</div>
        <h2>Иван Иванов</h2>
        <p>📧 ivan@example.com</p>
        <p>📱 +7 (999) 123-45-67</p>
        <p>📍 Москва, Россия</p>
      </div>
    </div>
  `,

    settings: () => `
    <div class="screen">
      <div class="settings-card">
        <h2>Настройки</h2>
        <p>🎨 Тема: светлая</p>
        <p>🔔 Уведомления: включены</p>
        <p>🌍 Язык: русский</p>
        <p>💳 Способ оплаты: карта</p>
      </div>
    </div>
  `
};

// === Роутер ===
const root = document.getElementById('app-root');
const titleEl = document.getElementById('app-title');
const backBtn = document.getElementById('back-btn');
const tabbar = document.getElementById('tabbar');

const routes = [
    {
        pattern: /^\/product\/([^/]+)$/,
        name: 'product',
        title: 'Страница товара'
    },
    {
        pattern: /^\/profile$/,
        name: 'profile',
        title: 'Профиль пользователя'
    },
    {
        pattern: /^\/settings$/,
        name: 'settings',
        title: 'Настройки'
    },
    {
        pattern: /^\/$/,
        name: 'home',
        title: 'Главная'
    }
];


function match(path) {
    for (const route of routes) {
        const m = path.match(route.pattern);
        if (m) return { route, params: { id: m[1] } };
    }
    return null;
}

function render(path) {
    const found = match(path);
    if (!found) {
        root.innerHTML = '<div class="screen"><h2>404 - Страница не найдена</h2></div>';
        titleEl.textContent = 'Ошибка 404';
        history.replaceState(null, '', '/404');
        return;
    }

    const { route, params } = found;

    const doRender = () => {
        root.innerHTML = screens[route.name](params);
        titleEl.textContent = route.title;
        backBtn.classList.toggle('hidden', route.name === 'home');

        // Обновляем активную вкладку в таббаре
        tabbar.querySelectorAll('.tab-btn').forEach(btn => {
            const isHome = route.name === 'home';
            const isProfile = route.name === 'profile';
            const isSettings = route.name === 'settings';
            btn.classList.toggle('active',
                (btn.dataset.href === '/' && isHome) ||
                (btn.dataset.href === '/profile' && isProfile) ||
                (btn.dataset.href === '/settings' && isSettings)
            );
        });
    };

    if (document.startViewTransition) {
        const transition = document.startViewTransition(doRender);
        transition.finished.then(() => {
            restoreScroll(path);
        });
    } else {
        doRender();
        restoreScroll(path);
    }
}

function restoreScroll(path) {
    const saved = sessionStorage.getItem('scroll:' + path);
    if (saved) {
        window.scrollTo(0, Number(saved));
    } else {
        window.scrollTo(0, 0);
    }
}

function saveScroll() {
    const path = window.location.pathname;
    sessionStorage.setItem('scroll:' + path, String(window.scrollY));
}

// Клик по «Назад» в хедере
backBtn.addEventListener('click', () => {
    saveScroll();
    if (window.history.length > 1) {
        history.back();
    } else {
        // Если зашли по прямой ссылке — возвращаем на главную
        history.pushState(null, '', '/');
        render('/');
    }
});

// Клик по таббару
tabbar.addEventListener('click', (e) => {
    const btn = e.target.closest('.tab-btn');
    if (!btn) return;
    saveScroll();
    const href = btn.dataset.href;
    history.pushState(null, '', href);
    render(href);
});

document.addEventListener('click', (e) => {
    const link = e.target.closest('a[data-link]');
    if (!link) return;
    e.preventDefault();
    saveScroll();
    const href = link.getAttribute('href');
    history.pushState(null, '', href);
    render(href);
});

window.addEventListener('popstate', () => {
    render(window.location.pathname);
});

render(window.location.pathname);

