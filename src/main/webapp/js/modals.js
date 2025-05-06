// Modal UI

document.addEventListener('DOMContentLoaded', function () {
    // Create modal container
    let modalContainer = document.querySelector('.modal-container') || document.createElement('div');
    modalContainer.className = 'modal-container';
    document.body.appendChild(modalContainer);

    // Add CSS for modals
    document.head.appendChild(Object.assign(document.createElement('style'), {
        textContent: `
        .modal-container{position:fixed;top:0;left:0;width:100%;height:100%;background-color:rgba(0,0,0,.5);display:flex;align-items:center;justify-content:center;z-index:9999;opacity:0;visibility:hidden;transition:opacity .3s,visibility .3s}
        .modal-container.active{opacity:1;visibility:visible}
        .modal{background-color:#fff;border-radius:5px;box-shadow:0 5px 15px rgba(0,0,0,.3);width:90%;max-width:500px;max-height:90vh;overflow-y:auto;transform:translateY(-20px);transition:transform .3s}
        .modal-container.active .modal{transform:translateY(0)}
        .modal-header{display:flex;justify-content:space-between;align-items:center;padding:15px 20px;border-bottom:1px solid #ddd}
        .modal-title{font-size:1.2rem;font-weight:600;margin:0}
        .modal-close{background:none;border:none;font-size:1.5rem;cursor:pointer;color:#777}
        .modal-body{padding:20px}
        .modal-footer{padding:15px 20px;border-top:1px solid #ddd;display:flex;justify-content:flex-end;gap:10px}
        `
    }));

    // Modal Functions
    window.Modal = {
        show: function (options) {
            const { title = 'Modal', content = '', footer = null } = options || {};

            // Create modal structure
            const modal = document.createElement('div');
            modal.className = 'modal';
            modal.innerHTML = `
                <div class="modal-header">
                    <h3 class="modal-title">${title}</h3>
                    <button class="modal-close">&times;</button>
                </div>
                <div class="modal-body"></div>
                <div class="modal-footer"></div>
            `;

            // Add content
            const modalBody = modal.querySelector('.modal-body');
            if (typeof content === 'string') {
                modalBody.innerHTML = content;
            } else if (content instanceof HTMLElement) {
                modalBody.appendChild(content);
            }

            // Add footer
            const modalFooter = modal.querySelector('.modal-footer');
            if (footer) {
                if (typeof footer === 'string') {
                    modalFooter.innerHTML = footer;
                } else if (footer instanceof HTMLElement) {
                    modalFooter.appendChild(footer);
                }
            } else {
                modalFooter.innerHTML = '<button class="btn btn-outline">Close</button>';
            }

            // Add event listeners
            modal.querySelector('.modal-close').addEventListener('click', Modal.hide);
            if (!footer) modal.querySelector('.btn-outline').addEventListener('click', Modal.hide);

            // Show modal
            modalContainer.innerHTML = '';
            modalContainer.appendChild(modal);
            setTimeout(() => modalContainer.classList.add('active'), 10);

            // Close on outside click or Escape key
            modalContainer.addEventListener('click', e => {
                if (e.target === modalContainer) Modal.hide();
            });
            document.addEventListener('keydown', e => {
                if (e.key === 'Escape') Modal.hide();
            });
        },

        hide: function () {
            modalContainer.classList.remove('active');
        },

        confirm: function (options) {
            const { title = 'Confirmation', message = 'Are you sure?' } = options || {};
            const content = `<p>${message}</p>`;
            const footer = document.createElement('div');
            footer.innerHTML = `
                <button class="btn btn-outline">Cancel</button>
                <button class="btn btn-primary">Confirm</button>
            `;

            footer.querySelector('.btn-outline').addEventListener('click', Modal.hide);
            footer.querySelector('.btn-primary').addEventListener('click', Modal.hide);

            Modal.show({ title, content, footer });
        },

        alert: function (options) {
            const { title = 'Alert', message = 'Alert message' } = options || {};
            const content = `<p>${message}</p>`;
            const footer = document.createElement('div');
            footer.innerHTML = '<button class="btn btn-primary">OK</button>';
            footer.querySelector('.btn-primary').addEventListener('click', Modal.hide);

            Modal.show({ title, content, footer });
        }
    };
});
