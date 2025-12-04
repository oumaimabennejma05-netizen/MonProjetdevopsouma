pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "monprojetdevopsouma:latest"
    }

    tools {
        maven "Maven"        // Le nom configuré dans Jenkins
        jdk "JDK17"          // Le nom configuré dans Jenkins
        sonarScanner "SonarScanner" // Le nom du scanner ajouté dans Jenkins
    }

    stages {

        stage('Checkout') {
            steps {
                echo "🔄 Clonage du dépôt..."
                git branch: 'master', url: 'https://github.com/oumaimabennejma05-netizen/MonProjetdevopsouma.git'
            }
        }

        stage('Build Maven') {
            steps {
                echo "⚙️ Build Maven..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Tests') {
            steps {
                echo "🧪 Exécution des tests..."
                sh 'mvn test'
            }
        }

        stage('SAST - Analyse SonarQube') {
            steps {
                echo "🔍 Analyse de sécurité (SAST) avec SonarQube..."
                withSonarQubeEnv("SonarQube") {
                    sh """
                        sonar-scanner \
                            -Dsonar.projectKey=MonProjet \
                            -Dsonar.projectName=MonProjet \
                            -Dsonar.sources=src \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.host.url=http://localhost:9000
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo "⏳ Vérification du Quality Gate..."
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "🐳 Construction de l’image Docker..."
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }

        stage('Docker Run') {
            steps {
                echo "🚀 Lancement du conteneur Docker..."
                sh "docker run -d -p 8080:8080 $DOCKER_IMAGE"
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline terminé avec succès !"
        }
        failure {
            echo "❌ Le pipeline a échoué."
        }
    }
}
