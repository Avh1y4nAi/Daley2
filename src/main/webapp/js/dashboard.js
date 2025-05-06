// Dashboard UI - Simplified for UI interactions only

document.addEventListener('DOMContentLoaded', function () {
    // Dashboard Tab Navigation
    const navLinks = document.querySelectorAll('.dashboard-nav a');
    const dashboardPanels = document.querySelectorAll('.dashboard-panel');

    navLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            const targetId = this.getAttribute('href').substring(1);

            navLinks.forEach(l => l.classList.remove('active'));
            dashboardPanels.forEach(p => p.classList.remove('active'));

            this.classList.add('active');
            document.getElementById(targetId)?.classList.add('active');
        });
    });

    // UI-only button actions with modal feedback
    const showModal = (type, title, message) => {
        if (window.Modal) {
            window.Modal[type]({ title, message });
        } else {
            alert(message);
        }
    };

    // Property actions - UI feedback only
    document.querySelectorAll('.saved-property-item .btn-danger').forEach(btn => {
        btn.addEventListener('click', () =>
            showModal('confirm', 'Remove Property', 'This is a UI demonstration only.'));
    });

    // Review actions - UI feedback only
    document.querySelectorAll('.review-actions .btn').forEach(btn => {
        btn.addEventListener('click', () =>
            showModal('alert', 'UI Demonstration', 'This is a UI demonstration only.'));
    });
});
