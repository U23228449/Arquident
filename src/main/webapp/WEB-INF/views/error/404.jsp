<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página No Encontrada - ArquiDent</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div style="min-height: 100vh; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
        <div style="text-align: center; color: white; max-width: 500px; padding: 2rem;">
            <h1 style="font-size: 6rem; margin-bottom: 1rem;">404</h1>
            <h2 style="font-size: 2rem; margin-bottom: 1rem;">Página No Encontrada</h2>
            <p style="font-size: 1.2rem; margin-bottom: 2rem; opacity: 0.9;">
                Lo sentimos, la página que buscas no existe o ha sido movida.
            </p>
            <a href="${pageContext.request.contextPath}/inicio" class="btn btn-primary">
                🏠 Volver al Inicio
            </a>
        </div>
    </div>
</body>
</html>
