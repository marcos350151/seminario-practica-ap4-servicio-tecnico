# Sistema de Gestión de Servicio Técnico - AP4
**Seminario de Práctica | Licenciatura en Informática | Universidad Siglo 21 | Noviembre 2026**  
**Alumno: Marcos Waigel**

## Proyecto integrador
Esta versión **continúa** las entregas AP1, AP2 y AP3: mismo caso, mismo esquema relacional y mismo circuito de órdenes. Integra el **patrón DAO** (`OrdenDAO`, `ClienteDAO`, `TecnicoDAO`, `ProcesoDAO`) y explicita un contrato `OrdenRepositorio` para las consultas de órdenes.

La entrega incluye clases abstractas (`Persona`), herencia, polimorfismo, excepciones, JDBC, transacciones MySQL, arreglos (`EstadoOrden[]` e `int[]`) y `ArrayList<OrdenServicio>` para registros de tamaño variable. Las opciones 15 a 17 son nuevas: reporte por estados, exportación y vista previa CSV. Los archivos CSV son exportaciones complementarias; la persistencia transaccional continúa en MySQL.

## Estructura
- `src/`: todos los archivos Java heredados de AP3 + clases nuevas.
- `sql/schema.sql`: creación completa de BD (solo instalación inicial).
- `sql/ap2_migracion.sql`: migración histórica (solo una vez).
- `sql/datos_demo.sql`: técnico y repuesto de prueba.
- `sql/verificacion_ap4.sql`: consultas de comprobación, sin modificaciones.
- `docs/`: diagramas originales y nuevos de arquitectura/secuencia.
- `evidencias/`: compilación, pruebas unitarias y prueba de menú reales.
- `Guion_video_AP4.txt`: libreto de presentación de tres minutos.

## 1) Compilar en Windows
Abrí CMD en la carpeta principal del proyecto:

```bat
javac -encoding UTF-8 -d out src\*.java
java -cp out PruebasPOO
java -cp out PruebasAP4
```

Ambas pruebas **no necesitan servidor MySQL**. Se verificó la compilación con OpenJDK 21 y se ejecutaron las pruebas de POO (8/8) y AP4 (11/11).

## 2) Preparar MySQL (para probar conexión, consultas y actualizaciones)
1. Abrí MySQL Workbench y conectate a tu MySQL 8.x.
2. Si **ya existe** la BD AP2/AP3, **no ejecutes de nuevo schema.sql ni ap2_migracion.sql**; conservá todos tus registros.
3. Si es una instalación nueva, ejecutá `sql/schema.sql` y luego `sql/ap2_migracion.sql` **una sola vez**.
4. Ejecutá `sql/datos_demo.sql` para contar con un técnico y un repuesto de muestra (evita crear esos datos de nuevo).
5. Colocá el **MySQL Connector/J** oficial en `lib/mysql-connector-j.jar` (ese JAR no viene incluido en el ZIP).
6. En CMD ejecutá, reemplazando `USUARIO` y `CLAVE`:

```bat
set ST_DB_URL=jdbc:mysql://localhost:3306/servicio_tecnico?useSSL=false&serverTimezone=UTC
set ST_DB_USER=USUARIO
set ST_DB_PASS=CLAVE
javac -encoding UTF-8 -d out src\*.java
java -cp "out;lib/mysql-connector-j.jar" Main
```

Si tu MySQL local no tiene TLS, la URL de arriba es solo para un laboratorio local. No uses esas opciones para conexiones expuestas a redes no confiables.

## 3) Recorrido funcional y evidencia a capturar
1. Opción 1: registrar cliente.
2. Opción 3: ingresar equipo y orden (estado RECIBIDO).
3. Opción 4: listar órdenes (SELECT / JDBC).
4. Consultá IDs de técnico de la opción 12; opción 5 diagnosticar.
5. Opción 6 presupuestar; opción 7 aceptar (S); opción 8 finalizar reparación.
6. Opción 9 usar repuesto (comprobá stock); opción 10 entregar.
7. Opción 13 buscar ID; 14 filtrar; 15 reporte por estado (arreglo + ArrayList).
8. Opción 16 exportar CSV, opción 17 leer CSV; revisá `exportaciones/ordenes_ap4.csv`.
9. En Workbench ejecutá `sql/verificacion_ap4.sql`, verificá estados/historial/repuestos.

**Capturá pantallas de cada operación**, especialmente la consulta y la actualización en MySQL; esta verificación debe realizarse en tu PC. Las pruebas de integración con MySQL de la versión AP4 están pendientes de verificación en una instalación local.

## 4) Manejo de errores
- `SQLException`: conexión, sentencias SQL y transacciones fallidas.
- `IOException`: fallas de lectura y escritura CSV.
- `IllegalArgumentException`: argumentos, tipos y transiciones inválidas.
- `IllegalStateException`: precondiciones y uso no permitido de operaciones.
- `rollback` y `PreparedStatement` en las operaciones de negocio.

## 5) Video y entrega
El video de **aproximadamente tres minutos** debe grabarse mostrando la ejecución local, los diagramas y al menos una consulta y una actualización MySQL. Usá `Guion_video_AP4.txt` como guía.

La consigna pide publicar la versión completa en GitHub. **El repositorio AP4 todavía no está publicado**. Podés crear `seminario-practica-ap4-servicio-tecnico` y subir todos sus archivos (sin credenciales ni datos personales); después insertá su enlace real en el informe antes de entregar.

Antecedente AP3: https://github.com/marcos350151/seminario-practica-ap3-servicio-tecnico

## Alcance y limitaciones
Prototipo académico con interfaz por consola. No implementa login real, permisos de aplicación, interfaz gráfica, copias de seguridad programadas ni pruebas de aceptación por usuarios. No incluir claves de BD ni el JAR de Connector/J en repositorios públicos. Los datos de ejemplo son ficticios.
