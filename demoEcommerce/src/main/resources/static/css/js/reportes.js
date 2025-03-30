document.addEventListener("DOMContentLoaded", () => {
    generarDatos();
    actualizarTabla();
});

let reportes = [
    { id: 1001, cliente: "Juan Pérez", fecha: "2024-03-25", monto: 500 },
    { id: 1002, cliente: "María López", fecha: "2024-03-26", monto: 300 },
    { id: 1003, cliente: "Carlos Sánchez", fecha: "2024-03-27", monto: 700 }
];

function generarDatos() {
    let totalVentas = reportes.reduce((sum, reporte) => sum + reporte.monto, 0);
    let pedidosCompletados = reportes.length;

    document.getElementById("total-ventas").textContent = `$${totalVentas}`;
    document.getElementById("pedidos-completados").textContent = pedidosCompletados;

    generarGrafico();
}

function generarGrafico() {
    const ctx = document.getElementById("graficoVentas").getContext("2d");
    let meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio"];
    let ventas = [1000, 1800, 1500, 1800, 2200, 2500];

    new Chart(ctx, {
        type: "bar",
        data: {
            labels: meses,
            datasets: [{
                label: "Ventas en USD",
                data: ventas,
                backgroundColor: "#5CD89F"
            }]
        },
        options: {
            responsive: true
        }
    });
}

function actualizarTabla() {
    const tabla = document.getElementById("tabla-reportes");
    tabla.innerHTML = "";
    reportes.forEach((reporte) => {
        tabla.innerHTML += `
            <tr>
                <td>${reporte.id}</td>
                <td>${reporte.cliente}</td>
                <td>${reporte.fecha}</td>
                <td>$${reporte.monto}</td>
            </tr>
        `;
    });
}
