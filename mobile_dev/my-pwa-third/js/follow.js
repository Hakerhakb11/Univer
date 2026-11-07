let isFollowing = false;

function toggleFollow() {
    isFollowing = !isFollowing;
    // const isFollowing = localStorage.getItem('isFollowing');
    const followBtnText = document.querySelector('.profile-button');


    // if (followBtnText.textContent === 'Подписаться') {
    //     followBtnText.textContent = 'Отписаться';
    // } else {
    //     followBtnText.textContent = 'Подписаться';
    // }

    // followBtnText.textContent = isFollowing ? 'Отписаться' : 'Подписаться';

    if (isFollowing) {
        followBtnText.textContent = 'Отписаться';
        followBtnText.style

        // localStorage.setItem('isFollowing', 'false');
    } else {
        followBtnText.textContent = 'Подписаться';
        // localStorage.setItem('isFollowing', 'true');
    }
}
