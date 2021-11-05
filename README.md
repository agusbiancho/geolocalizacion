# geolocalizacion

El desarrollo de la API cuenta con Swagger, para poder visualizar los dos recursos
/ip-direction/get
	*Recibe como parametro el IP para consultar su información.
	*Retorna el objecto InfoIPDirectionResponse con la información solicitada.

/ip-direction/listar
	*Retorna el objeto IPDirection que contiene la dirección ip y si fue bloqueada o no.
	
Se agregó un Filter para validar el ip que se consulta, en la primera, le dá acceso y lo registra;
en la segunda devuelve un estado 401. Solo se applica para el primer recurso.

Se implementó circuit breaker para controlar los tiempos de respuesta internos al llamar
al servicio que devuelve la info de la ip. Cuando intenta 5 veces el llamado y demora 2 segundos o más
pasa a modo abierto y las llamadas siguientes devuelven un estado 307. Hay que habilitarlo desde el código
porque tiene un sleep que se activa con un boolean desde el propertie.

El conocimiento que tengo sobre los tests es básico y es lo que complete.

El Dockerfile que creé es básico, solo tiene un paso que es crear la imagen con una base de java 8 y luego
los comandos para levantar el tomcat. Hasta donde conozco y en mi experiencia, creé un Dockerfile con dos pasos
donde el primero es para buildear a partir de un repo y luego crear la imagen a partir de ese build.

Con respecto al manejo de cargas, la verdad que no se adonde se quería llegar con eso, la forma que conozco
es con un balanceador previo, pero nunca lo implementé.

Quedo a disposición para cualquier consulta.