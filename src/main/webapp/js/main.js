document.addEventListener('DOMContentLoaded', function () {
    // Load components.js functionality
    if (typeof initializeMobileMenu === 'function') {
        initializeMobileMenu();
    }

    // Set active navigation link
    if (typeof setActiveNavLink === 'function') {
        setActiveNavLink();
    }

    // Property Carousel
    const carousel = document.querySelector('.property-carousel');
    if (carousel) {
        const prevButton = document.querySelector('.carousel-prev');
        const nextButton = document.querySelector('.carousel-next');
        if (prevButton && nextButton) {
            const cardWidth = carousel.querySelector('.property-card')?.offsetWidth || 300;
            const gap = 16;
            const scrollAmount = cardWidth + gap;

            prevButton.addEventListener('click', () => carousel.scrollBy({ left: -scrollAmount, behavior: 'smooth' }));
            nextButton.addEventListener('click', () => carousel.scrollBy({ left: scrollAmount, behavior: 'smooth' }));
        }
    }

    // Navbar Glass Effect
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        const handleScroll = () => requestAnimationFrame(() => {
            navbar.classList.toggle('glass', window.scrollY > 30);
        });

        window.addEventListener('scroll', handleScroll, { passive: true });
        handleScroll();
    }
});
