document.addEventListener('DOMContentLoaded', function() {
    // Simula la obtención del saldo actual desde el servidor
    const saldoActual = 1000.00; // Este valor debería ser obtenido del backend
    document.getElementById('saldo-actual').textContent = `$${saldoActual.toFixed(2)}`;
});
