document.addEventListener('DOMContentLoaded', function () {
    // Initialize mobile menu toggle functionality
    function initializeMobileMenu() {
        const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
        const navMenu = document.querySelector('.nav-menu');

        if (mobileMenuToggle && navMenu) {
            mobileMenuToggle.addEventListener('click', function () {
                mobileMenuToggle.classList.toggle('active');
                navMenu.classList.toggle('active');
            });
        }
    }

    // Set active navigation link based on current page
    function setActiveNavLink() {
        const currentPath = window.location.pathname;

        // Remove all active classes first
        const navLinks = document.querySelectorAll('.nav-menu a');
        navLinks.forEach(link => link.classList.remove('active'));

        // Set active class based on current path
        if (currentPath.endsWith('/') || currentPath.includes('/index.jsp')) {
            document.getElementById('nav-home')?.classList.add('active');
        } else if (currentPath.includes('/properties')) {
            document.getElementById('nav-properties')?.classList.add('active');
        } else if (currentPath.includes('/about')) {
            document.getElementById('nav-about')?.classList.add('active');
        } else if (currentPath.includes('/contact')) {
            document.getElementById('nav-contact')?.classList.add('active');
        } else if (currentPath.includes('/profile')) {
            document.querySelector('.nav-menu a[href*="profile"]')?.classList.add('active');
        }
    }

    // Initialize mobile menu
    initializeMobileMenu();

    // Set active navigation link
    setActiveNavLink();

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

    // Modal functionality
    window.Modal = {
        alert: function ({ title, message }) {
            // Simple alert for now, can be enhanced later
            alert(`${title}\n\n${message}`);
        }
    };
});
