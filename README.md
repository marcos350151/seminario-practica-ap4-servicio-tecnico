Sistema de Gestión de Servicio Técnico de Equipos Informáticos
Proyecto integrador de Seminario de Práctica — Licenciatura en Informática, Universidad Siglo 21.
Autor: Marcos Waigel  
Versión: AP4 — 2026  
Tecnologías: Java, JDBC y MySQL 8
Descripción
Aplicación Java de consola para gestionar clientes, equipos informáticos y órdenes de servicio técnico. Permite registrar la recepción de equipos, diagnósticos, presupuestos, decisiones, reparaciones, repuestos y entregas. También ofrece consultas, filtrado de órdenes, reportes agrupados por estado y exportación e importación de reportes CSV.
El desarrollo corresponde a la cuarta iteración de un prototipo académico. Mantiene el esquema de base de datos y el flujo operativo de las etapas anteriores.
Arquitectura
Se aplica el patrón DAO (Data Access Object) para separar la lógica de consulta y persistencia del modelo de dominio y de la interfaz por consola.
`Main`: interacción con el operador mediante menú de consola.
`Persona`, `Cliente`, `Tecnico`, `Equipo`, `OrdenServicio`: entidades del dominio.
`OrdenRepositorio`: interfaz de acceso a órdenes.
`ClienteDAO`, `TecnicoDAO`, `OrdenDAO`, `ProcesoDAO`: consultas y operaciones de persistencia mediante JDBC.
`Database`: configuración de conexiones a MySQL.
`ReporteOrdenes`: generación de reportes utilizando arreglos y `ArrayList`.
`ArchivoOrdenesCsv`: lectura y escritura de archivos CSV.
Las operaciones de varias sentencias utilizan transacciones y control de excepciones. Las consultas parametrizadas utilizan `PreparedStatement`.
Estructura del repositorio
```text
src/                  Código fuente Java
sql/                  Esquema, migración, datos y consultas SQL
docs/                 Diagramas de arquitectura, clases y secuencia
evidencias/           Resultados de compilación y pruebas
lib/                  Indicaciones sobre el controlador JDBC
01_compilar_y_probar.bat
02_ejecutar_con_mysql.bat
```
Requisitos
JDK 17 o superior.
MySQL Server 8.x.
MySQL Connector/J compatible, ubicado en `lib/mysql-connector-j.jar`.
Compilación y pruebas
Desde la raíz del proyecto, en Windows CMD:
```bat
javac -encoding UTF-8 -d out src\*.java
java -cp out PruebasPOO
java -cp out PruebasAP4
```
Las pruebas de clases y reportes se ejecutan sin conexión a la base de datos. Los resultados registrados se encuentran en `evidencias/`.
Base de datos
Para una instalación nueva, ejecutar en MySQL Workbench los scripts `sql/schema.sql` y luego `sql/ap2_migracion.sql`. Los scripts iniciales no deben repetirse sobre una base ya migrada. `sql/datos_demo.sql` contiene registros de ejemplo.
Configurar las variables de entorno para la ejecución local:
```bat
set ST_DB_URL=jdbc:mysql://localhost:3306/servicio_tecnico?useSSL=false&serverTimezone=UTC
set ST_DB_USER=usuario_local
set ST_DB_PASS=clave_local
java -cp "out;lib/mysql-connector-j.jar" Main
```
La configuración mostrada corresponde a un entorno de desarrollo local. En entornos remotos corresponde habilitar TLS y validar certificados.
La verificación del estado de las órdenes, presupuestos, reparaciones y repuestos puede efectuarse con `sql/verificacion_ap4.sql`.
Funcionalidades adicionales AP4
Opción 15: reporte por estado de las órdenes.
Opción 16: exportación de órdenes a CSV.
Opción 17: lectura de reportes CSV.
La base de datos MySQL es la fuente principal de persistencia; el CSV funciona como exportación complementaria.
Verificación y alcance
Se dispone de resultados de compilación Java y de pruebas automatizadas de dominio y reportes. La integración completa con MySQL requiere una ejecución local con el controlador JDBC y un servidor configurado.
El sistema es un prototipo académico. No incluye autenticación de usuarios, interfaz gráfica ni funcionalidades de producción como auditoría centralizada y copias de seguridad automáticas.
Antecedentes
AP3: https://github.com/marcos350151/seminario-practica-ap3-servicio-tecnico
