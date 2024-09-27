function checkDataForWarnings() {
    // APDEX (Application Performance Index) table
    const tbody = document.querySelectorAll('table#apdexTable tbody')[1];
    for (const item of tbody.children) { 
        const td = item.children.item(0);
        if (parseFloat(td.innerText) > 1.5) {
            item.classList.add('critical');
        } else if (parseFloat(td.innerText) > 1) {
            item.classList.add('warn');
        }
    }

    //Statistics table
    const tbody2 = document.querySelectorAll('table#statisticsTable tbody')[1];
    for (const item of tbody2.children) { 
        const td = item.children.item(4);
        if (parseFloat(td.innerText) > 1500) {
            const value = parseFloat(td.innerText);
            if (value > 2000) {
                item.classList.add('critical');
            } else if (value > 1500) {
                item.classList.add('warn');
            }
        }
    }
    if (!tbody && !tbody2) {
        setTimeout(() => checkDataForWarnings(), 1000);
    }
}
