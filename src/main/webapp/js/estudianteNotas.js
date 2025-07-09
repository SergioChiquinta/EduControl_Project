
function togglePanel(id) {
    const panel = document.getElementById("panel-" + id);
    const arrow = document.getElementById("arrow-" + id);

    if (panel.classList.contains("hidden")) {
        panel.classList.remove("hidden");
        arrow.classList.add("rotate-down");
    } else {
        panel.classList.add("hidden");
        arrow.classList.remove("rotate-down");
    }
}
