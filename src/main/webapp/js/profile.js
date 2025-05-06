/**
 * Ghar Dalali - User Profile Page JavaScript
 * Handles UI interactions for the user profile page
 */

document.addEventListener('DOMContentLoaded', function () {
    // Get DOM elements for profile sections
    const sections = {
        view: document.getElementById('view-profile'),
        edit: document.getElementById('edit-profile'),
        password: document.getElementById('change-password')
    };

    // Helper function to show a section and hide others
    const showSection = (sectionName) => {
        Object.entries(sections).forEach(([name, section]) => {
            if (section) section.classList.toggle('active', name === sectionName);
        });
    };

    // Helper function for modal alerts
    const showModal = (title, message) => {
        if (window.Modal) {
            window.Modal.alert({ title, message });
        } else {
            alert(message);
        }
    };

    // Section toggle buttons
    document.getElementById('edit-profile-btn')?.addEventListener('click', () => showSection('edit'));
    document.getElementById('cancel-edit-btn')?.addEventListener('click', () => showSection('view'));
    document.getElementById('change-password-btn')?.addEventListener('click', () => showSection('password'));
    document.getElementById('cancel-password-btn')?.addEventListener('click', () => showSection('view'));
    document.getElementById('enable-2fa-btn')?.addEventListener('click', () =>
        showModal('Feature Coming Soon', 'Two-factor authentication will be available soon.'));

    // Password strength checker
    document.getElementById('new-password')?.addEventListener('input', function () {
        const password = this.value;
        const checks = {
            length: password.length >= 8,
            uppercase: /[A-Z]/.test(password),
            number: /[0-9]/.test(password),
            special: /[^A-Za-z0-9]/.test(password)
        };

        // Update requirement list
        const requirements = document.querySelectorAll('.password-requirements li');
        if (requirements.length >= 4) {
            requirements[0].className = checks.length ? 'valid' : '';
            requirements[1].className = checks.uppercase ? 'valid' : '';
            requirements[2].className = checks.number ? 'valid' : '';
            requirements[3].className = checks.special ? 'valid' : '';
        }

        // Calculate strength (0-4)
        const strength = Object.values(checks).filter(Boolean).length;

        // Update strength meter
        const strengthSegments = document.querySelectorAll('.strength-segment');
        const strengthText = document.querySelector('.strength-text span');

        strengthSegments.forEach((segment, i) => {
            segment.className = 'strength-segment';
            if (i < strength) {
                segment.classList.add(strength <= 2 ? 'weak' : strength === 3 ? 'medium' : 'strong');
            }
        });

        if (strengthText) {
            strengthText.textContent = ['Very Weak', 'Weak', 'Fair', 'Good', 'Strong'][strength];
        }
    });

    // Confirm password validation
    document.getElementById('confirm-password')?.addEventListener('input', function () {
        const newPassword = document.getElementById('new-password')?.value;
        if (this.value !== newPassword) {
            this.setCustomValidity('Passwords do not match');
        } else {
            this.setCustomValidity('');
        }
    });

    // Avatar change button
    document.querySelector('.change-avatar-btn')?.addEventListener('click', () =>
        showModal('Change Profile Picture', 'This feature will be available soon.'));

    // Toggle switches for preferences
    document.querySelectorAll('.toggle-switch input').forEach(toggle => {
        toggle.addEventListener('change', function () {
            const preferenceName = this.closest('.preference-item').querySelector('h3').textContent;
            const status = this.checked ? 'enabled' : 'disabled';

            // In a real implementation, this would send an AJAX request to update preferences
            showModal('Preference Updated', `${preferenceName} has been ${status}.`);
        });
    });
});
