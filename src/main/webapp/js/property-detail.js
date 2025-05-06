// Property Detail UI

document.addEventListener('DOMContentLoaded', function () {
    // Image Gallery - Thumbnail click updates main image
    const mainImage = document.querySelector('.main-image img');
    const thumbnails = document.querySelectorAll('.thumbnail-images img');

    if (mainImage && thumbnails.length > 0) {
        thumbnails.forEach(thumbnail => {
            thumbnail.addEventListener('click', function () {
                mainImage.src = this.src;
                thumbnails.forEach(t => t.classList.remove('active'));
                this.classList.add('active');
            });
        });
    }

    // Rating System - Star hover and click effects
    const ratingStars = document.querySelectorAll('.rating-input .star');
    if (ratingStars.length > 0) {
        let selectedRating = 0;

        const updateStars = (activeIndex) => {
            ratingStars.forEach((s, i) => s.classList.toggle('filled', i < activeIndex));
        };

        ratingStars.forEach((star, index) => {
            star.addEventListener('mouseenter', () => updateStars(index + 1));
            star.addEventListener('mouseleave', () => updateStars(selectedRating));
            star.addEventListener('click', () => {
                selectedRating = index + 1;
                updateStars(selectedRating);
            });
        });
    }
});
