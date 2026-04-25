<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ArquiDent - Clínica Dental</title>
    
    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- Animate.css para animaciones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
    
    <!-- AOS (Animate On Scroll) -->
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            50: '#f0f4ff',
                            100: '#e0e7ff',
                            500: '#667eea',
                            600: '#5a67d8',
                            700: '#4c51bf',
                            800: '#434190',
                            900: '#3c366b'
                        },
                        secondary: {
                            500: '#764ba2',
                            600: '#6b46c1'
                        }
                    },
                    animation: {
                        'fade-in': 'fadeIn 0.5s ease-in-out',
                        'slide-up': 'slideUp 0.6s ease-out',
                        'bounce-slow': 'bounce 2s infinite',
                        'pulse-slow': 'pulse 3s infinite'
                    }
                }
            }
        }
    </script>
    
    <style>
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        @keyframes slideUp {
            from { transform: translateY(30px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }
        .gradient-bg {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .glass-effect {
            backdrop-filter: blur(10px);
            background: rgba(255, 255, 255, 0.1);
        }
    </style>
</head>
<body class="bg-gray-50">
    <!-- Navigation -->
    <nav class="gradient-bg shadow-lg sticky top-0 z-50">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between items-center h-16">
                <!-- Logo -->
                <div class="flex items-center">
                    <a href="${pageContext.request.contextPath}/inicio" class="flex items-center text-white hover:text-blue-200 transition-colors duration-300">
                        <i class="fas fa-tooth text-2xl mr-2"></i>
                        <span class="text-xl font-bold">ArquiDent</span>
                    </a>
                </div>
                
                <!-- Desktop Menu -->
                <div class="hidden md:block">
                    <div class="ml-10 flex items-baseline space-x-4">
                        <a href="${pageContext.request.contextPath}/inicio" class="text-white hover:bg-white hover:bg-opacity-20 px-3 py-2 rounded-md text-sm font-medium transition-all duration-300 transform hover:scale-105">
                            <i class="fas fa-home mr-1"></i>Inicio
                        </a>
                        <a href="${pageContext.request.contextPath}/servicios" class="text-white hover:bg-white hover:bg-opacity-20 px-3 py-2 rounded-md text-sm font-medium transition-all duration-300 transform hover:scale-105">
                            <i class="fas fa-stethoscope mr-1"></i>Servicios
                        </a>
                        <a href="${pageContext.request.contextPath}/nosotros" class="text-white hover:bg-white hover:bg-opacity-20 px-3 py-2 rounded-md text-sm font-medium transition-all duration-300 transform hover:scale-105">
                            <i class="fas fa-users mr-1"></i>Nosotros
                        </a>
                        <a href="${pageContext.request.contextPath}/faq" class="text-white hover:bg-white hover:bg-opacity-20 px-3 py-2 rounded-md text-sm font-medium transition-all duration-300 transform hover:scale-105">
                            <i class="fas fa-question-circle mr-1"></i>FAQ
                        </a>
                    </div>
                </div>
                
                <!-- Auth Section -->
                <div class="flex items-center space-x-4">
                    <c:choose>
                        <c:when test="${sessionScope.usuario != null}">
                            <span class="text-white text-sm">
                                <i class="fas fa-user mr-1"></i>
                                Hola, ${sessionScope.usuario.nombre}
                            </span>
                            
                            <!-- Botón Panel de Control -->
                            <c:choose>
                                <c:when test="${sessionScope.rolUsuario == 'paciente'}">
                                    <a href="${pageContext.request.contextPath}/dashboard-paciente" class="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-full text-sm font-medium transition-all duration-300 transform hover:scale-105">
                                        <i class="fas fa-tachometer-alt mr-1"></i>Mi Panel
                                    </a>
                                </c:when>
                                <c:when test="${sessionScope.rolUsuario == 'odontologo'}">
                                    <a href="${pageContext.request.contextPath}/dashboard-odontologo" class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-full text-sm font-medium transition-all duration-300 transform hover:scale-105">
                                        <i class="fas fa-user-md mr-1"></i>Panel Médico
                                    </a>
                                </c:when>
                                <c:when test="${sessionScope.rolUsuario == 'secretaria'}">
                                    <a href="${pageContext.request.contextPath}/dashboard-secretaria" class="bg-purple-500 hover:bg-purple-600 text-white px-4 py-2 rounded-full text-sm font-medium transition-all duration-300 transform hover:scale-105">
                                        <i class="fas fa-clipboard-list mr-1"></i>Panel Secretaría
                                    </a>
                                </c:when>
                                <c:when test="${sessionScope.rolUsuario == 'administrador'}">
                                    <a href="${pageContext.request.contextPath}/dashboard-admin" class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-full text-sm font-medium transition-all duration-300 transform hover:scale-105">
                                        <i class="fas fa-cogs mr-1"></i>Panel Admin
                                    </a>
                                </c:when>
                            </c:choose>
                            
                            <a href="${pageContext.request.contextPath}/logout" class="bg-white bg-opacity-20 text-white px-4 py-2 rounded-full text-sm font-medium hover:bg-opacity-30 transition-all duration-300 transform hover:scale-105">
                                <i class="fas fa-sign-out-alt mr-1"></i>Cerrar Sesión
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login" class="bg-white bg-opacity-20 text-white px-4 py-2 rounded-full text-sm font-medium hover:bg-opacity-30 transition-all duration-300 transform hover:scale-105">
                                <i class="fas fa-sign-in-alt mr-1"></i>Iniciar Sesión
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- Mobile menu button -->
                <div class="md:hidden">
                    <button type="button" class="text-white hover:text-blue-200 focus:outline-none focus:text-blue-200" onclick="toggleMobileMenu()">
                        <i class="fas fa-bars text-xl"></i>
                    </button>
                </div>
            </div>
        </div>
        
        <!-- Mobile Menu -->
        <div id="mobile-menu" class="md:hidden hidden">
            <div class="px-2 pt-2 pb-3 space-y-1 sm:px-3 bg-black bg-opacity-20">
                <a href="${pageContext.request.contextPath}/inicio" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                    <i class="fas fa-home mr-2"></i>Inicio
                </a>
                <a href="${pageContext.request.contextPath}/servicios" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                    <i class="fas fa-stethoscope mr-2"></i>Servicios
                </a>
                <a href="${pageContext.request.contextPath}/nosotros" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                    <i class="fas fa-users mr-2"></i>Nosotros
                </a>
                <a href="${pageContext.request.contextPath}/faq" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                    <i class="fas fa-question-circle mr-2"></i>FAQ
                </a>
                
                <!-- Panel de Control en móvil -->
                <c:if test="${sessionScope.usuario != null}">
                    <div class="border-t border-white border-opacity-20 pt-2 mt-2">
                        <c:choose>
                            <c:when test="${sessionScope.rolUsuario == 'paciente'}">
                                <a href="${pageContext.request.contextPath}/dashboard-paciente" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                                    <i class="fas fa-tachometer-alt mr-2"></i>Mi Panel
                                </a>
                            </c:when>
                            <c:when test="${sessionScope.rolUsuario == 'odontologo'}">
                                <a href="${pageContext.request.contextPath}/dashboard-odontologo" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                                    <i class="fas fa-user-md mr-2"></i>Panel Médico
                                </a>
                            </c:when>
                            <c:when test="${sessionScope.rolUsuario == 'secretaria'}">
                                <a href="${pageContext.request.contextPath}/dashboard-secretaria" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                                    <i class="fas fa-clipboard-list mr-2"></i>Panel Secretaría
                                </a>
                            </c:when>
                            <c:when test="${sessionScope.rolUsuario == 'administrador'}">
                                <a href="${pageContext.request.contextPath}/dashboard-admin" class="text-white hover:bg-white hover:bg-opacity-20 block px-3 py-2 rounded-md text-base font-medium">
                                    <i class="fas fa-cogs mr-2"></i>Panel Admin
                                </a>
                            </c:when>
                        </c:choose>
                    </div>
                </c:if>
            </div>
        </div>
    </nav>
    
    <script>
        function toggleMobileMenu() {
            const menu = document.getElementById('mobile-menu');
            menu.classList.toggle('hidden');
        }
    </script>
