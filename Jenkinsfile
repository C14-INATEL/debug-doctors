pipeline {
    agent any

    environment {
        NOME_PIPELINE = 'Pipeline CI/CD'
    }

    stages {
        stage('Build') {
            steps {
                echo "Compiling the project..."
                sh "./mvnw clean install -DskipTests"
            }
        }
        stage('Tests') {
            steps {
                echo 'Running unit tests...'
            }
        }
    }
}

post {
    success {
        echo "Pipeline ${env.NOME_PIPELINE} completed."
    }
    failure {
        echo "Pipeline ${env.NOME_PIPELINE} failure."
    }
}