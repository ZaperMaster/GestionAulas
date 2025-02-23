document.addEventListener('DOMContentLoaded', function() {
    var edificioSelect = document.getElementById('edificioSelect');
    if (!edificioSelect) return;

    var plantasPorEdificioUrl = edificioSelect.getAttribute('data-plantas-url');

    edificioSelect.addEventListener('change', function() {
        var edificioId = this.value;
        if (edificioId && plantasPorEdificioUrl) {
            fetch(plantasPorEdificioUrl + '?edificioId=' + encodeURIComponent(edificioId))
                .then(function(response) {
                    return response.json();
                })
                .then(function(data) {
                    var plantaSelect = document.getElementById('plantaSelect');
                    plantaSelect.innerHTML = '<option value="" disabled selected>Seleccione una planta</option>';
                    data.forEach(function(planta) {
                        var option = document.createElement('option');
                        option.value = planta.id;
                        option.text = planta.descripcion;
                        plantaSelect.appendChild(option);
                    });
                })
                .catch(function(error) {
                    console.error('Error al obtener las plantas:', error);
                });
        }
    });
});
