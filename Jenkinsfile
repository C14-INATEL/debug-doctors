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
    }

    stages {
        stage('Tests') {
            steps {
                echo "Running unit tests..."
                // Runs the FIRST principles tests for the business rules
                sh './mvnw clean test'
            }
            post {
                success {
                    echo "Tests: ALL BUSINESS RULES PASSED"
                }
                failure {
                    echo "Tests: FAILURE"
                    // Ensures Jenkins marks the build as failed immediately
                    error("Pipeline aborted due to test failures.")
                }
            }
        }

        stage('Build') {
            steps {
                echo "Packaging the Spring Boot application..."
                // Skips tests here since they already passed in the previous stage
                sh './mvnw package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                echo "Starting PostgreSQL Database and API containers..."
                // Explicitly targeting 'api' and 'db' so Jenkins doesn't reboot itself
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