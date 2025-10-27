// src/main/resources/static/spa/app.js

// Small helper for JSON REST calls
async function api(path, opts = {}) {
    const res = await fetch(`/api${path}`, {
        headers: { 'Content-Type': 'application/json' },
        ...opts,
    });
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    return res.status !== 204 ? res.json() : null;
}

/* ---------------- SPA helpers ---------------- */

async function loadList() {
    const list = document.getElementById('spa-ab-list');
    if (!list) return; // not on SPA page
    list.innerHTML = '';
    const abs = await api('/addressbooks');
    for (const ab of abs) {
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = '#';
        a.textContent = `${ab.id} — ${ab.buddies?.length ?? 0} buddy(ies)`;
        a.addEventListener('click', () => showAB(ab.id));
        li.appendChild(a);
        list.appendChild(li);
    }
}

async function showAB(id) {
    const detail = document.getElementById('spa-ab-detail');
    if (!detail) return; // not on SPA page
    const data = await api(`/addressbooks/${id}`);

    detail.hidden = false;
    document.getElementById('ab-title').textContent = `AddressBook #${data.id}`;

    const ul = document.getElementById('buddy-list');
    ul.innerHTML = '';
    (data.buddies || []).forEach(b => {
        const li = document.createElement('li');
        li.textContent = `${b.name} — ${b.phone}${b.address ? ' (' + b.address + ')' : ''}`;
        ul.appendChild(li);
    });

    // keep current AB id on the add-buddy form
    const addForm = document.getElementById('spa-add-buddy');
    if (addForm) addForm.dataset.abId = id;
}

/* -------- Progressive enhancement for server-rendered pages --------
   If you add forms/ids to your Thymeleaf pages later, these hooks will
   enhance them automatically. Safe to load even if elements aren’t present.
-------------------------------------------------------------------- */

function enhanceServerRenderedIndex() {
    const form = document.getElementById('create-ab-form');
    const list = document.getElementById('ab-list');
    if (!form || !list) return;

    form.addEventListener('submit', async (e) => {
        if (!window.fetch) return;
        e.preventDefault();
        const nameInput =
            form.querySelector('input[name="name"]') ||
            form.querySelector('input'); // fallback
        const name = (nameInput?.value || '').trim();
        // REST create endpoint creates an empty AddressBook (no name),
        const ab = await api('/addressbooks', { method: 'POST', body: '{}' });

        // Append to list without a full page reload
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = `/addressbooks/${ab.id}`;
        a.textContent = `AddressBook #${ab.id}`;
        li.appendChild(a);
        list.appendChild(li);
        form.reset();
    });
}

/* ---------------- Boot ---------------- */

window.addEventListener('DOMContentLoaded', () => {
    // SPA page wiring
    const spaCreate = document.getElementById('spa-create-ab');
    if (spaCreate) {
        spaCreate.addEventListener('submit', async (e) => {
            e.preventDefault();
            await api('/addressbooks', { method: 'POST', body: '{}' });
            await loadList();
            e.target.reset();
        });
    }

    const addBuddyForm = document.getElementById('spa-add-buddy');
    if (addBuddyForm) {
        addBuddyForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const id = e.target.dataset.abId;
            const payload = {
                name: e.target.elements.name.value.trim(),
                phone: e.target.elements.phone.value.trim(),
                address: e.target.elements.address.value.trim() || null,
            };
            await api(`/addressbooks/${id}/buddies`, {
                method: 'POST',
                body: JSON.stringify(payload),
            });
            e.target.reset();
            await showAB(id);
        });
    }

    // If we’re on the SPA page, populate the list
    if (document.getElementById('spa-ab-list')) {
        loadList();
    }

    // Progressive enhancement for server-rendered pages (safe if elements missing)
    enhanceServerRenderedIndex();
});
