# Dockerfile pour déploiement Tomcat
FROM tomcat:9-jdk11-openjdk-slim

# Supprime les applications par défaut (optionnel)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copie notre application
COPY target/agecalculator.war /usr/local/tomcat/webapps/ROOT.war

# Expose le port
EXPOSE 8080

# Commande pour démarrer Tomcat
CMD ["catalina.sh", "run"]