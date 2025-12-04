pipeline {
    agent any

    tools {
        maven 'Maven 4.0'            // Mets ici le VRAI NOM dans Jenkins
        jdk 'openjdk version 17'     // Mets ici le VRAI NOM dans Jenkins
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/oumaimabennejma05-netizen/MonProjetdevopsouma'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube - SAST') {
            steps {
                withSonarQubeEnv('SonarQube') {   // Mets ici le nom du serveur Sonar dans Jenkins
                    sh """
                        sonar-scanner \
                          -Dsonar.projectKey=MonProjetDevOps \
                          -Dsonar.sources=. \
                          -Dsonar.java.binaries=target \
                          -Dsonar.host.url=http://localhost:9000
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    timeout(time: 2, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }
    }
}
