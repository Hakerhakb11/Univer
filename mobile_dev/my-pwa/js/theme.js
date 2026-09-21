let isDark = false;

function toggleTheme() {
    isDark = !isDark;
    document.body.className = isDark ? 'dark' : 'light';
}