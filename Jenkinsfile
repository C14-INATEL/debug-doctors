pipeline {

    agent any
    
    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        timestamps()
        ansiColor('xterm')
        disableConcurrentBuilds()
    }

    environment {
        PIPELINE_NAME = 'Debug Doctors — CI/CD Pipeline'
        POSTGRES_PASSWORD = credentials('postgres-db-password')
    }
    
    stages {
        stage('Static Analysis') {
            steps {
                echo "Running static code analysis..."
                sh './mvnw checkstyle:check'
            }
        }

        stage('Tests') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            steps {
                echo "Running unit tests..."
                sh 'chmod +x mvnw && ./mvnw clean test'
            }
            post {
                success {
                    echo "Tests: ALL BUSINESS RULES PASSED"
                }
                failure {
                    echo "Tests: FAILURE"
                    error("Pipeline aborted due to test failures.")
                }
            }
        }

        stage('Build') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            steps {
                echo "Packaging the Spring Boot application..."
                sh 'chmod +x mvnw && ./mvnw package -DskipTests'
            }

            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo "Starting PostgreSQL Database and API containers..."
                sh 'docker-compose up -d --build api db'
            }
            post {
                success { echo "Deploy: CONTAINERS ARE UP AND RUNNING ON PORT 8000" }
                failure { echo "Deploy: FAILED TO START CONTAINERS" }
            }
        }
    }

    post {
        success { echo "Pipeline completed with SUCCESS! Code is pristine." }
        failure { echo "Pipeline FAILED. Check the logs above." }
        unstable { echo "Pipeline UNSTABLE — some tests failed or timed out." }
        always {
            echo "========================================================"
            echo "Pipeline : ${env.PIPELINE_NAME}"
            echo "Build    : #${BUILD_NUMBER}"
            echo "Result   : ${currentBuild.currentResult}"
            echo "Duration : ${currentBuild.durationString}"
            echo "========================================================"
        }
    }
}