document.addEventListener('DOMContentLoaded', function () {
    // Determine the correct path prefix based on current location
    const isInnerPage = window.location.pathname.includes('/pages/');
    const pathPrefix = isInnerPage ? '../' : '';

    // Load header
    const headerPlaceholder = document.getElementById('header-placeholder');
    if (headerPlaceholder) {
        // Try to load using fetch API first
        fetch(pathPrefix + 'components/header.html')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load header: ' + response.status);
                }
                return response.text();
            })
            .then(data => {
                headerPlaceholder.innerHTML = data;
                setActiveNavLink();
            })
            .catch(error => {
                console.error('Error loading header:', error);
                // Fallback: Insert header HTML directly
                insertHeaderDirectly(headerPlaceholder, isInnerPage);
            });
    }

    // Load footer
    const footerPlaceholder = document.getElementById('footer-placeholder');
    if (footerPlaceholder) {
        // Try to load using fetch API first
        fetch(pathPrefix + 'components/footer.html')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load footer: ' + response.status);
                }
                return response.text();
            })
            .then(data => {
                footerPlaceholder.innerHTML = data;
            })
            .catch(error => {
                console.error('Error loading footer:', error);
                // Fallback: Insert footer HTML directly
                insertFooterDirectly(footerPlaceholder, isInnerPage);
            });
    }

    // Fallback function to insert header directly
    function insertHeaderDirectly(placeholder, isInnerPage) {
        const homeLink = isInnerPage ? '../index.html' : 'index.html';
        const pagesPrefix = isInnerPage ? '' : 'pages/';

        placeholder.innerHTML = `
        <!-- Navigation Bar -->
        <header class="navbar">
            <div class="container">
                <div class="logo">
                    <a href="${homeLink}">
                        <h1>Ghar Dalali</h1>
                    </a>
                </div>
                <nav class="nav-menu">
                    <ul>
                        <li><a href="${homeLink}" id="nav-home">Home</a></li>
                        <li><a href="${pagesPrefix}properties.html" id="nav-properties">Properties</a></li>
                        <li><a href="${pagesPrefix}blog.html" id="nav-blog">Blog</a></li>
                        <li><a href="${pagesPrefix}about.html" id="nav-about">About</a></li>
                        <li><a href="${pagesPrefix}contact.html" id="nav-contact">Contact</a></li>
                    </ul>
                </nav>
                <div class="auth-buttons">
                    <a href="${pagesPrefix}login.html" class="btn btn-outline">Login</a>
                    <a href="${pagesPrefix}register.html" class="btn btn-primary">Register</a>
                </div>
                <div class="mobile-menu-toggle">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        </header>
        `;

        setActiveNavLink();
    }

    // Fallback function to insert footer directly
    function insertFooterDirectly(placeholder, isInnerPage) {
        const homeLink = isInnerPage ? '../index.html' : 'index.html';
        const pagesPrefix = isInnerPage ? '' : 'pages/';

        placeholder.innerHTML = `
        <!-- Footer -->
        <footer class="footer">
            <div class="container">
                <div class="footer-content">
                    <div class="footer-logo">
                        <h2>Ghar Dalali</h2>
                        <p>Your trusted partner in finding the perfect home.</p>
                        <div class="social-links">
                            <a href="#" class="social-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path>
                                </svg>
                            </a>
                            <a href="#" class="social-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <path
                                        d="M22 4s-.7 2.1-2 3.4c1.6 10-9.4 17.3-18 11.6 2.2.1 4.4-.6 6-2C3 15.5.5 9.6 3 5c2.2 2.6 5.6 4.1 9 4-.9-4.2 4-6.6 7-3.8 1.1 0 3-1.2 3-1.2z">
                                    </path>
                                </svg>
                            </a>
                            <a href="#" class="social-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <rect x="2" y="2" width="20" height="20" rx="5" ry="5"></rect>
                                    <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"></path>
                                    <line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line>
                                </svg>
                            </a>
                            <a href="#" class="social-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <path
                                        d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z">
                                    </path>
                                    <rect x="2" y="9" width="4" height="12"></rect>
                                    <circle cx="4" cy="4" r="2"></circle>
                                </svg>
                            </a>
                        </div>
                    </div>
                    <div class="footer-links">
                        <h3>Quick Links</h3>
                        <ul>
                            <li><a href="${homeLink}">Home</a></li>
                            <li><a href="${pagesPrefix}properties.html">Properties</a></li>
                            <li><a href="${pagesPrefix}blog.html">Blog</a></li>
                            <li><a href="${pagesPrefix}about.html">About</a></li>
                            <li><a href="${pagesPrefix}contact.html">Contact</a></li>
                            <li><a href="${pagesPrefix}terms.html">Terms of Service</a></li>
                            <li><a href="${pagesPrefix}privacy.html">Privacy Policy</a></li>
                        </ul>
                    </div>
                    <div class="footer-contact">
                        <h3>Contact Us</h3>
                        <p>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                                stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z">
                                </path>
                                <polyline points="22,6 12,13 2,6"></polyline>
                            </svg>
                            info@ghardalali.com
                        </p>
                        <p>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                                stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path
                                    d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z">
                                </path>
                            </svg>
                            +977 1234567890
                        </p>
                    </div>
                </div>
                <div class="footer-bottom">
                    <p>&copy; 2023 Ghar Dalali. All rights reserved.</p>
                </div>
            </div>
        </footer>
        `;
    }

    // Set active navigation link based on current page
    function setActiveNavLink() {
        const currentPath = window.location.pathname;

        // Remove all active classes first
        const navLinks = document.querySelectorAll('.nav-menu a');
        navLinks.forEach(link => link.classList.remove('active'));

        // Set active class based on current path
        if (currentPath.includes('/index.html') || currentPath === '/' || currentPath === '') {
            document.getElementById('nav-home')?.classList.add('active');
        } else if (currentPath.includes('/properties.html')) {
            document.getElementById('nav-properties')?.classList.add('active');
        } else if (currentPath.includes('/blog.html')) {
            document.getElementById('nav-blog')?.classList.add('active');
        } else if (currentPath.includes('/about.html')) {
            document.getElementById('nav-about')?.classList.add('active');
        } else if (currentPath.includes('/contact.html')) {
            document.getElementById('nav-contact')?.classList.add('active');
        }
    }

    // Initialize mobile menu toggle functionality
    document.addEventListener('click', function (e) {
        if (e.target.closest('.mobile-menu-toggle')) {
            document.querySelector('.mobile-menu-toggle').classList.toggle('active');
            document.querySelector('.nav-menu').classList.toggle('active');
        }
    });

    // Call setActiveNavLink after a short delay to ensure all elements are loaded
    setTimeout(setActiveNavLink, 100);
});
