/**
 * Updated components.js for JSP-based application
 * This file handles common UI components and interactions
 */
document.addEventListener('DOMContentLoaded', function () {
    // Initialize mobile menu toggle functionality
    initializeMobileMenu();
    
    // Set active navigation link based on current page
    setActiveNavLink();
    
    // Initialize any modals
    initializeModals();
    
    // Initialize property image gallery if on property detail page
    if (document.querySelector('.property-gallery')) {
        initializePropertyGallery();
    }
});

/**
 * Initialize mobile menu toggle functionality
 */
function initializeMobileMenu() {
    const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
    if (mobileMenuToggle) {
        mobileMenuToggle.addEventListener('click', function() {
            this.classList.toggle('active');
            document.querySelector('.nav-menu').classList.toggle('active');
        });
    }
}

/**
 * Set active navigation link based on current page
 */
function setActiveNavLink() {
    const currentPath = window.location.pathname;
    
    // Remove all active classes first
    const navLinks = document.querySelectorAll('.nav-menu a');
    navLinks.forEach(link => link.classList.remove('active'));
    
    // Set active class based on current path
    if (currentPath.endsWith('/') || currentPath.includes('/index.jsp')) {
        document.querySelector('.nav-menu a[href$="/"]')?.classList.add('active');
    } else if (currentPath.includes('/properties')) {
        document.querySelector('.nav-menu a[href*="/properties"]')?.classList.add('active');
    } else if (currentPath.includes('/about')) {
        document.querySelector('.nav-menu a[href*="/about"]')?.classList.add('active');
    } else if (currentPath.includes('/contact')) {
        document.querySelector('.nav-menu a[href*="/contact"]')?.classList.add('active');
    } else if (currentPath.includes('/dashboard')) {
        document.querySelector('.nav-menu a[href*="/dashboard"]')?.classList.add('active');
    }
}

/**
 * Initialize modals
 */
function initializeModals() {
    // Modal functionality
    window.Modal = {
        show: function(modalId) {
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.classList.add('active');
                document.body.classList.add('modal-open');
            }
        },
        
        hide: function(modalId) {
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.classList.remove('active');
                document.body.classList.remove('modal-open');
            }
        },
        
        alert: function(options) {
            const { title = 'Alert', message = '' } = options;
            
            // Create modal if it doesn't exist
            let modal = document.getElementById('alert-modal');
            if (!modal) {
                modal = document.createElement('div');
                modal.id = 'alert-modal';
                modal.className = 'modal';
                modal.innerHTML = `
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 id="alert-title">${title}</h3>
                            <button class="modal-close">&times;</button>
                        </div>
                        <div class="modal-body">
                            <p id="alert-message">${message}</p>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary modal-ok">OK</button>
                        </div>
                    </div>
                `;
                document.body.appendChild(modal);
                
                // Add event listeners
                modal.querySelector('.modal-close').addEventListener('click', () => this.hide('alert-modal'));
                modal.querySelector('.modal-ok').addEventListener('click', () => this.hide('alert-modal'));
            } else {
                // Update content
                modal.querySelector('#alert-title').textContent = title;
                modal.querySelector('#alert-message').textContent = message;
            }
            
            this.show('alert-modal');
        },
        
        confirm: function(options) {
            const { title = 'Confirm', message = '', onConfirm = () => {}, onCancel = () => {} } = options;
            
            // Create modal if it doesn't exist
            let modal = document.getElementById('confirm-modal');
            if (!modal) {
                modal = document.createElement('div');
                modal.id = 'confirm-modal';
                modal.className = 'modal';
                modal.innerHTML = `
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 id="confirm-title">${title}</h3>
                            <button class="modal-close">&times;</button>
                        </div>
                        <div class="modal-body">
                            <p id="confirm-message">${message}</p>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-outline modal-cancel">Cancel</button>
                            <button class="btn btn-primary modal-confirm">Confirm</button>
                        </div>
                    </div>
                `;
                document.body.appendChild(modal);
                
                // Add event listeners
                modal.querySelector('.modal-close').addEventListener('click', () => {
                    this.hide('confirm-modal');
                    onCancel();
                });
                
                modal.querySelector('.modal-cancel').addEventListener('click', () => {
                    this.hide('confirm-modal');
                    onCancel();
                });
                
                modal.querySelector('.modal-confirm').addEventListener('click', () => {
                    this.hide('confirm-modal');
                    onConfirm();
                });
            } else {
                // Update content
                modal.querySelector('#confirm-title').textContent = title;
                modal.querySelector('#confirm-message').textContent = message;
                
                // Update event listeners
                const confirmBtn = modal.querySelector('.modal-confirm');
                const cancelBtn = modal.querySelector('.modal-cancel');
                const closeBtn = modal.querySelector('.modal-close');
                
                // Remove old event listeners
                const newConfirmBtn = confirmBtn.cloneNode(true);
                const newCancelBtn = cancelBtn.cloneNode(true);
                const newCloseBtn = closeBtn.cloneNode(true);
                
                confirmBtn.parentNode.replaceChild(newConfirmBtn, confirmBtn);
                cancelBtn.parentNode.replaceChild(newCancelBtn, cancelBtn);
                closeBtn.parentNode.replaceChild(newCloseBtn, closeBtn);
                
                // Add new event listeners
                newConfirmBtn.addEventListener('click', () => {
                    this.hide('confirm-modal');
                    onConfirm();
                });
                
                newCancelBtn.addEventListener('click', () => {
                    this.hide('confirm-modal');
                    onCancel();
                });
                
                newCloseBtn.addEventListener('click', () => {
                    this.hide('confirm-modal');
                    onCancel();
                });
            }
            
            this.show('confirm-modal');
        }
    };
}

/**
 * Initialize property gallery
 */
function initializePropertyGallery() {
    const mainImage = document.querySelector('.property-gallery .main-image img');
    const thumbnails = document.querySelectorAll('.property-gallery .thumbnail-images img');
    
    thumbnails.forEach(thumbnail => {
        thumbnail.addEventListener('click', function() {
            // Update main image
            mainImage.src = this.src;
            
            // Update active class
            thumbnails.forEach(thumb => thumb.classList.remove('active'));
            this.classList.add('active');
        });
    });
}
