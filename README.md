## Demo de ejecución token de acceso en registro

autor: David Alejandro Gallego Valcárcel

### ESTE REPO ES UNA DEMO TECNICA PARA NISUM

#### REQUISITOS

* Java 17
* Gradle 8.10.2 o en su defecto usar el wrapper

> **La ejecución requiere que tenga la variable de entorno `JWT_SECRET` seteada en el sistema con un valor
aleatorio sin espacios de por lo menos 32 caracteres, en su defecto puede usar este: `9lfw0a1zcks449lputgaghsh7ilxt04c`
la razón por la cuál no se deja en código, es porque las actions de github me bloquean el repositorio.**


una vez se se tengan los requisitos es solo ubicarse en la ruta raíz del proyecto y lanzar: `.\gradlew clean build bootRun` la aplicación no es multimodular, por lo que no deben ejecutarse más comandos**

orden de ejecución:

Windows en un terminal:

```shell
setx JWT_SECRET 9lfw0a1zcks449lputgaghsh7ilxt04c

cd .\test-user-registry\

.\gradlew clean build bootRun
```


### Consideraciones sobre los requisitos


- el código está en inglés, costumbre de ya trabajar en ese idioma.
- se da una cobertura del 74% ya que el 26% restantes son ejecuciones fuera de los requisitos solicitados
- la validación del password es de: 
  - que haya al menos una letra (mayúscula o minúscula).
  - que haya al menos un dígito.
  - se permiten letras y dígitos, se requiere una longitud minima de 8 caracteres
- se adicionó dos endpoint de pruebas para garantizar la fiabilidad del token.
  - como se especificó en el requisito, a cada login el token se renueva, se puede comprobar yendo a la consola de la BD
    en memoria (H2) al path `/h2-console` (nota: la aplicación quedó con el puerto por defecto 8080)
  - se agregó un path demo para ver la fiabilidad del token generado


### Consideraciones sobre arquitectura

![flowchart](\docs\flowchart.png)

- Se siguió una arquitectura hexagonal, separada en 3 capas, no se siguió la notación de puertos y adaptadores ya que no había
  validaciones de dominio dentro de los requisitos
- En la capa de aplicación, está la configuración del caso de uso y utilería
- En la capa de domain está el usecase de acceso y consulta de contactos (core domain) que usa los otros adaptadores
- En la capa de infra está la configuración de seguridad y los adaptadores cuyas abstracciones están en los model del domain

### Evidencia de pruebas

- Cómo se especificó, la cobertura está al 74% se puede verificar lanzando: `.\gradlew clean build test jacocoTestReport`
  la ruta del reporte se encuentra en la ruta: `build/reports/jacoco/test/html/index.html`
  la ruta de los success cases se encuentra en: `build/reports/tests/test/index.html`
  ambas en la raíz del proyecto
- No se hizo mutation testing, pero se hizo un integration con todo el caso de uso de la aplicación en la clase
  `co/com/dgallego58/GlobalIntegrationTest.java`


![coverage](\docs\coverage.png)

