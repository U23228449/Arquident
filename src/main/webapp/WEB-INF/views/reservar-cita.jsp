<%@ include file="layout/header.jsp" %>

<!-- Header -->
<section class="gradient-bg text-white py-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center" data-aos="fade-up">
            <h1 class="text-3xl md:text-4xl font-bold mb-4">
                <i class="fas fa-calendar-plus mr-3"></i>
                Reservar Cita
            </h1>
            <p class="text-blue-100 text-lg">
                Agenda tu cita de manera rápida y sencilla
            </p>
        </div>
    </div>
</section>

<!-- Alertas -->
<c:if test="${not empty error}">
    <section class="bg-red-50 border-l-4 border-red-500 py-4">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center">
                <i class="fas fa-exclamation-triangle text-red-500 text-xl mr-3"></i>
                <p class="text-red-700">${error}</p>
            </div>
        </div>
    </section>
</c:if>

<!-- Formulario de Reserva -->
<section class="py-12 bg-gray-50">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="bg-white rounded-2xl shadow-xl p-8" data-aos="fade-up">
            
            <!-- Progreso -->
            <div class="mb-8">
                <div class="flex items-center justify-between mb-4">
                    <div class="flex items-center">
                        <div class="w-8 h-8 bg-blue-500 text-white rounded-full flex items-center justify-center text-sm font-bold mr-3">1</div>
                        <span class="text-blue-500 font-medium">Servicio</span>
                    </div>
                    <div class="flex-1 h-1 bg-gray-200 mx-4">
                        <div class="h-1 bg-blue-500 transition-all duration-300" id="progress-bar" style="width: 25%"></div>
                    </div>
                    <div class="flex items-center">
                        <div class="w-8 h-8 bg-gray-300 text-gray-500 rounded-full flex items-center justify-center text-sm font-bold mr-3" id="step-2">2</div>
                        <span class="text-gray-500" id="step-2-text">Doctor</span>
                    </div>
                    <div class="flex-1 h-1 bg-gray-200 mx-4"></div>
                    <div class="flex items-center">
                        <div class="w-8 h-8 bg-gray-300 text-gray-500 rounded-full flex items-center justify-center text-sm font-bold mr-3" id="step-3">3</div>
                        <span class="text-gray-500" id="step-3-text">Fecha</span>
                    </div>
                    <div class="flex-1 h-1 bg-gray-200 mx-4"></div>
                    <div class="flex items-center">
                        <div class="w-8 h-8 bg-gray-300 text-gray-500 rounded-full flex items-center justify-center text-sm font-bold" id="step-4">4</div>
                        <span class="text-gray-500" id="step-4-text">Confirmar</span>
                    </div>
                </div>
            </div>
            
            <form id="reservaForm" method="post" action="${pageContext.request.contextPath}/reservar-cita">
                
                <!-- Paso 1: Seleccionar Servicio -->
                <div id="paso-1" class="step-content">
                    <h3 class="text-2xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-stethoscope text-blue-500 mr-2"></i>
                        Selecciona el Servicio
                    </h3>
                    
                    <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <c:forEach var="servicio" items="${serviciosDisponibles}">
                            <div class="servicio-card border-2 border-gray-200 rounded-xl p-6 cursor-pointer hover:border-blue-500 hover:shadow-lg transition-all duration-300" 
                                 data-servicio="${servicio.idServicio}" 
                                 data-precio="${servicio.precio}"
                                 data-nombre="${servicio.nombre}">
                                <div class="text-center">
                                    <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
                                        <i class="fas fa-tooth text-blue-500 text-2xl"></i>
                                    </div>
                                    <h4 class="font-bold text-gray-800 mb-2">${servicio.nombre}</h4>
                                    <p class="text-gray-600 text-sm mb-4">${servicio.descripcion}</p>
                                    <div class="text-2xl font-bold text-green-600">S/ ${servicio.precio}</div>
                                    <div class="text-sm text-gray-500 mt-2">
                                        <i class="fas fa-clock mr-1"></i>
                                        45 min
                                    </div>
                                    <div class="text-xs text-green-600 mt-1">
                                        <i class="fas fa-check mr-1"></i>
                                        Reserva directa
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div class="flex justify-end mt-8">
                        <button type="button" id="btn-siguiente-1" class="bg-blue-500 text-white px-8 py-3 rounded-xl hover:bg-blue-600 transition-colors duration-300 disabled:bg-gray-300 disabled:cursor-not-allowed" disabled>
                            Siguiente <i class="fas fa-arrow-right ml-2"></i>
                        </button>
                    </div>
                </div>
                
                <!-- Paso 2: Seleccionar Doctor -->
                <div id="paso-2" class="step-content hidden">
                    <h3 class="text-2xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-user-md text-blue-500 mr-2"></i>
                        Selecciona el Odontólogo
                    </h3>
                    
                    <p class="text-gray-600 mb-6">
                        <i class="fas fa-info-circle text-blue-500 mr-2"></i>
                        Elige el odontólogo de tu preferencia para realizar tu tratamiento:
                    </p>
                    
                    <div id="doctores-container" class="grid md:grid-cols-2 gap-6">
                        <c:choose>
                            <c:when test="${empty doctoresDisponibles}">
                                <div class="col-span-2 text-center text-gray-500 py-8">
                                    <i class="fas fa-user-times text-3xl mb-2"></i>
                                    <p>No hay doctores disponibles para este servicio</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="doctor" items="${doctoresDisponibles}">
                                    <div class="doctor-card border-2 border-gray-200 rounded-xl p-6 cursor-pointer hover:border-blue-500 hover:shadow-lg transition-all duration-300" 
                                         data-doctor="${doctor.idUsuario}" 
                                         data-nombre="${doctor.nombre}">
                                        <div class="flex items-center">
                                            <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mr-4">
                                                <i class="fas fa-user-md text-blue-500 text-2xl"></i>
                                            </div>
                                            <div class="flex-1">
                                                <h4 class="font-bold text-gray-800 text-lg">${doctor.nombre}</h4>
                                                <p class="text-blue-600 text-sm font-medium">Odontólogo General</p>
                                                <p class="text-gray-500 text-sm mt-1">
                                                    <i class="fas fa-phone mr-1"></i>
                                                    <c:choose>
                                                        <c:when test="${not empty doctor.telefono}">
                                                            ${doctor.telefono}
                                                        </c:when>
                                                        <c:otherwise>
                                                            Sin teléfono
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <div class="mt-2 flex items-center text-green-600 text-xs">
                                                    <i class="fas fa-check-circle mr-1"></i>
                                                    Disponible
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <div class="flex justify-between mt-8">
                        <button type="button" id="btn-anterior-2" class="bg-gray-300 text-gray-700 px-8 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            <i class="fas fa-arrow-left mr-2"></i> Anterior
                        </button>
                        <button type="button" id="btn-siguiente-2" class="bg-blue-500 text-white px-8 py-3 rounded-xl hover:bg-blue-600 transition-colors duration-300 disabled:bg-gray-300 disabled:cursor-not-allowed" disabled>
                            Siguiente <i class="fas fa-arrow-right ml-2"></i>
                        </button>
                    </div>
                </div>
                
                <!-- Paso 3: Seleccionar Fecha y Hora -->
                <div id="paso-3" class="step-content hidden">
                    <h3 class="text-2xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-calendar-alt text-blue-500 mr-2"></i>
                        Selecciona Fecha y Hora
                    </h3>
                    
                    <div class="grid lg:grid-cols-2 gap-8">
                        <!-- Calendario -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Fecha</label>
                            <input type="date" id="fecha" name="fecha" 
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none"
                                   min="<%= java.time.LocalDate.now() %>"
                                   max="<%= java.time.LocalDate.now().plusMonths(2) %>">
                        </div>
                        
                        <!-- Horarios -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Hora Disponible</label>
                            <div id="horarios-container" class="grid grid-cols-2 gap-2 max-h-64 overflow-y-auto">
                                <div class="text-center text-gray-500 py-8 col-span-2">
                                    Selecciona una fecha para ver horarios disponibles
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Observaciones -->
                    <div class="mt-6">
                        <label class="block text-sm font-medium text-gray-700 mb-2">Observaciones (Opcional)</label>
                        <textarea name="observaciones" rows="3" 
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none"
                                  placeholder="Describe cualquier síntoma o información relevante..."></textarea>
                    </div>
                    
                    <div class="flex justify-between mt-8">
                        <button type="button" id="btn-anterior-3" class="bg-gray-300 text-gray-700 px-8 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            <i class="fas fa-arrow-left mr-2"></i> Anterior
                        </button>
                        <button type="button" id="btn-siguiente-3" class="bg-blue-500 text-white px-8 py-3 rounded-xl hover:bg-blue-600 transition-colors duration-300 disabled:bg-gray-300 disabled:cursor-not-allowed" disabled>
                            Siguiente <i class="fas fa-arrow-right ml-2"></i>
                        </button>
                    </div>
                </div>
                
                <!-- Paso 4: Confirmar Reserva -->
                <div id="paso-4" class="step-content hidden">
                    <h3 class="text-2xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-check-circle text-blue-500 mr-2"></i>
                        Confirmar Reserva
                    </h3>
                    
                    <div class="bg-gray-50 rounded-xl p-6 mb-6">
                        <h4 class="font-bold text-gray-800 mb-4">Resumen de tu Cita</h4>
                        
                        <div class="space-y-3">
                            <div class="flex justify-between">
                                <span class="text-gray-600">Servicio:</span>
                                <span class="font-medium" id="resumen-servicio">-</span>
                            </div>
                            <div class="flex justify-between">
                                <span class="text-gray-600">Odontólogo:</span>
                                <span class="font-medium" id="resumen-doctor">-</span>
                            </div>
                            <div class="flex justify-between">
                                <span class="text-gray-600">Fecha:</span>
                                <span class="font-medium" id="resumen-fecha">-</span>
                            </div>
                            <div class="flex justify-between">
                                <span class="text-gray-600">Hora:</span>
                                <span class="font-medium" id="resumen-hora">-</span>
                            </div>
                            <div class="flex justify-between">
                                <span class="text-gray-600">Duración:</span>
                                <span class="font-medium">45 min</span>
                            </div>
                            <hr class="my-4">
                            <div class="flex justify-between text-lg font-bold">
                                <span>Total a Pagar:</span>
                                <span class="text-green-600" id="resumen-precio">S/ 0.00</span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="bg-yellow-50 border border-yellow-200 rounded-xl p-4 mb-6">
                        <div class="flex items-start">
                            <i class="fas fa-exclamation-triangle text-yellow-500 mt-1 mr-3"></i>
                            <div>
                                <h5 class="font-bold text-yellow-800 mb-1">Importante</h5>
                                <p class="text-yellow-700 text-sm">
                                    Tienes <strong>5 minutos</strong> para completar el pago una vez confirmada la reserva. 
                                    Si no pagas en este tiempo, la cita será cancelada automáticamente.
                                </p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="flex justify-between">
                        <button type="button" id="btn-anterior-4" class="bg-gray-300 text-gray-700 px-8 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            <i class="fas fa-arrow-left mr-2"></i> Anterior
                        </button>
                        <button type="submit" id="btn-confirmar" class="bg-green-500 text-white px-8 py-3 rounded-xl hover:bg-green-600 transition-colors duration-300">
                            <i class="fas fa-check mr-2"></i> Confirmar Reserva
                        </button>
                    </div>
                </div>
                
                <!-- Campos ocultos -->
                <input type="hidden" id="servicio-hidden" name="servicio">
                <input type="hidden" id="odontologo-hidden" name="odontologo">
                <input type="hidden" id="hora-hidden" name="hora">
                
            </form>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>

<script>
let pasoActual = 1;
let servicioSeleccionado = null;
let doctorSeleccionado = null;
let fechaSeleccionada = null;
let horaSeleccionada = null;

// Inicializar
document.addEventListener('DOMContentLoaded', function() {
    console.log('Inicializando reserva de cita...');
    
    // Si hay servicio preseleccionado
    <c:if test="${not empty servicioPreseleccionado}">
        console.log('Servicio preseleccionado: ${servicioPreseleccionado.nombre}');
        seleccionarServicio('${servicioPreseleccionado.idServicio}', '${servicioPreseleccionado.nombre}', '${servicioPreseleccionado.precio}');
        siguientePaso();
    </c:if>
    
    configurarEventos();
});

function configurarEventos() {
    console.log('Configurando eventos...');
    
    // Eventos de navegación
    document.getElementById('btn-siguiente-1').addEventListener('click', () => siguientePaso());
    document.getElementById('btn-anterior-2').addEventListener('click', () => anteriorPaso());
    document.getElementById('btn-siguiente-2').addEventListener('click', () => siguientePaso());
    document.getElementById('btn-anterior-3').addEventListener('click', () => anteriorPaso());
    document.getElementById('btn-siguiente-3').addEventListener('click', () => siguientePaso());
    document.getElementById('btn-anterior-4').addEventListener('click', () => anteriorPaso());
    
    // Evento de selección de servicio
    document.querySelectorAll('.servicio-card').forEach(card => {
        card.addEventListener('click', function() {
            const idServicio = this.dataset.servicio;
            const nombre = this.dataset.nombre;
            const precio = this.dataset.precio;
            console.log('Servicio seleccionado:', nombre);
            seleccionarServicio(idServicio, nombre, precio);
        });
    });
    
    // Configurar eventos de doctores (ya están cargados en el DOM)
    configurarEventosDoctores();
    
    // Evento de cambio de fecha
    document.getElementById('fecha').addEventListener('change', function() {
        fechaSeleccionada = this.value;
        console.log('Fecha seleccionada:', fechaSeleccionada);
        
        // Verificar que la fecha no sea anterior a hoy
        const fechaHoy = new Date().toISOString().split('T')[0];
        if (fechaSeleccionada < fechaHoy) {
            alert('No puedes seleccionar una fecha anterior a hoy');
            this.value = '';
            fechaSeleccionada = null;
            return;
        }
        
        cargarHorarios();
    });
}

function configurarEventosDoctores() {
    console.log('Configurando eventos de doctores...');
    
    // Agregar eventos de click a todas las tarjetas de doctores
    document.querySelectorAll('.doctor-card').forEach(card => {
        card.addEventListener('click', function() {
            const doctorId = this.dataset.doctor;
            const doctorNombre = this.dataset.nombre;
            console.log('Doctor seleccionado:', doctorNombre);
            seleccionarDoctor(doctorId, doctorNombre);
        });
    });
}

function seleccionarServicio(id, nombre, precio) {
    console.log('Seleccionando servicio:', id, nombre, precio);
    
    // Remover selección anterior
    document.querySelectorAll('.servicio-card').forEach(card => {
        card.classList.remove('border-blue-500', 'bg-blue-50');
        card.classList.add('border-gray-200');
    });
    
    // Seleccionar nuevo servicio
    const card = document.querySelector('[data-servicio="' + id + '"]');
    if (card) {
        card.classList.remove('border-gray-200');
        card.classList.add('border-blue-500', 'bg-blue-50');
        
        servicioSeleccionado = { id, nombre, precio };
        document.getElementById('servicio-hidden').value = id;
        document.getElementById('btn-siguiente-1').disabled = false;
        
        console.log('Servicio seleccionado correctamente. Los doctores ya están disponibles.');
    }
}

function seleccionarDoctor(id, nombre) {
    console.log('Seleccionando doctor:', id, nombre);
    
    // Remover selección anterior
    document.querySelectorAll('.doctor-card').forEach(card => {
        card.classList.remove('border-blue-500', 'bg-blue-50');
        card.classList.add('border-gray-200');
    });
    
    // Seleccionar nuevo doctor
    const card = document.querySelector('[data-doctor="' + id + '"]');
    if (card) {
        card.classList.remove('border-gray-200');
        card.classList.add('border-blue-500', 'bg-blue-50');
        
        doctorSeleccionado = { id, nombre };
        document.getElementById('odontologo-hidden').value = id;
        document.getElementById('btn-siguiente-2').disabled = false;
        
        console.log('Doctor seleccionado correctamente:', nombre);
    }
}

function cargarHorarios() {
    if (!doctorSeleccionado || !fechaSeleccionada) {
        console.log('Faltan datos para cargar horarios');
        return;
    }
    
    console.log('Cargando horarios para doctor:', doctorSeleccionado.nombre, 'fecha:', fechaSeleccionada);
    
    const container = document.getElementById('horarios-container');
    container.innerHTML = '<div class="col-span-2 text-center text-gray-500 py-4"><i class="fas fa-spinner fa-spin text-xl mb-2"></i><p>Cargando horarios...</p></div>';
    
    const url = '${pageContext.request.contextPath}/reservar-cita?action=getHorarios&doctor=' + doctorSeleccionado.id + '&fecha=' + fechaSeleccionada;
    console.log('URL horarios:', url);
    
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('HTTP ' + response.status);
            }
            return response.json();
        })
        .then(horarios => {
            console.log('Horarios recibidos:', horarios);
            
            container.innerHTML = '';
            
            if (!horarios || horarios.length === 0) {
                container.innerHTML = '<div class="col-span-2 text-center text-gray-500 py-8"><i class="fas fa-clock text-2xl mb-2"></i><p>No hay horarios disponibles para esta fecha</p></div>';
                return;
            }
            
            horarios.forEach(hora => {
                const horaButton = document.createElement('button');
                horaButton.type = 'button';
                horaButton.className = 'hora-btn px-3 py-2 border border-gray-300 rounded-lg hover:border-blue-500 hover:bg-blue-50 transition-all duration-300 text-sm';
                horaButton.textContent = hora;
                horaButton.dataset.hora = hora;
                
                horaButton.addEventListener('click', function() {
                    seleccionarHora(hora);
                });
                
                container.appendChild(horaButton);
            });
        })
        .catch(error => {
            console.error('Error cargando horarios:', error);
            container.innerHTML = '<div class="col-span-2 text-center text-red-500 py-8"><i class="fas fa-exclamation-triangle text-2xl mb-2"></i><p>Error cargando horarios</p></div>';
        });
}

function seleccionarHora(hora) {
    console.log('Seleccionando hora:', hora);
    
    // Remover selección anterior
    document.querySelectorAll('.hora-btn').forEach(btn => {
        btn.classList.remove('border-blue-500', 'bg-blue-500', 'text-white');
        btn.classList.add('border-gray-300');
    });
    
    // Seleccionar nueva hora
    const btn = document.querySelector('[data-hora="' + hora + '"]');
    if (btn) {
        btn.classList.remove('border-gray-300');
        btn.classList.add('border-blue-500', 'bg-blue-500', 'text-white');
        
        horaSeleccionada = hora;
        document.getElementById('hora-hidden').value = hora;
        document.getElementById('btn-siguiente-3').disabled = false;
    }
}

function siguientePaso() {
    if (pasoActual < 4) {
        document.getElementById('paso-' + pasoActual).classList.add('hidden');
        pasoActual++;
        document.getElementById('paso-' + pasoActual).classList.remove('hidden');
        actualizarProgreso();
        
        if (pasoActual === 4) {
            actualizarResumen();
        }
    }
}

function anteriorPaso() {
    if (pasoActual > 1) {
        document.getElementById('paso-' + pasoActual).classList.add('hidden');
        pasoActual--;
        document.getElementById('paso-' + pasoActual).classList.remove('hidden');
        actualizarProgreso();
    }
}

function actualizarProgreso() {
    const porcentaje = (pasoActual / 4) * 100;
    document.getElementById('progress-bar').style.width = porcentaje + '%';
    
    // Actualizar pasos
    for (let i = 1; i <= 4; i++) {
        const step = document.getElementById('step-' + i);
        const stepText = document.getElementById('step-' + i + '-text');
        
        if (step) {
            if (i <= pasoActual) {
                step.classList.remove('bg-gray-300', 'text-gray-500');
                step.classList.add('bg-blue-500', 'text-white');
                if (stepText) {
                    stepText.classList.remove('text-gray-500');
                    stepText.classList.add('text-blue-500');
                }
            } else {
                step.classList.remove('bg-blue-500', 'text-white');
                step.classList.add('bg-gray-300', 'text-gray-500');
                if (stepText) {
                    stepText.classList.remove('text-blue-500');
                    stepText.classList.add('text-gray-500');
                }
            }
        }
    }
}

function actualizarResumen() {
    if (servicioSeleccionado) {
        document.getElementById('resumen-servicio').textContent = servicioSeleccionado.nombre;
        document.getElementById('resumen-precio').textContent = 'S/ ' + servicioSeleccionado.precio;
    }
    
    if (doctorSeleccionado) {
        document.getElementById('resumen-doctor').textContent = doctorSeleccionado.nombre;
    }
    
    if (fechaSeleccionada) {
        document.getElementById('resumen-fecha').textContent = new Date(fechaSeleccionada).toLocaleDateString('es-PE');
    }
    
    if (horaSeleccionada) {
        document.getElementById('resumen-hora').textContent = horaSeleccionada;
    }
}
</script>
