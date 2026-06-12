pipeline {

    agent any
    
    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        timestamps()
        ansiColor('xterm')
        disableConcurrentBuilds()
    }

    triggers {
        cron('H/5 * * * *') // Poll or run periodically every 5 minutes
    }

    environment {
        PIPELINE_NAME = 'Debug Doctors — CI/CD Pipeline'
        POSTGRES_PASSWORD = credentials('postgres-db-password')
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "Checking out source code..."
                checkout scm
            }
            post {
                success {
                    echo "Checkout: SOURCE CODE RETRIEVED SUCCESSFULLY"
                }
                failure {
                    echo "Checkout: FAILED TO RETRIEVE SOURCE CODE"
                    error("Pipeline aborted due to checkout failure.")
                }
            }
        }
        stage('Static Analysis') {
            steps {
                echo "Running static code analysis..."
                sh 'mvn checkstyle:check'
            }
            post {
                success {
                    echo "Static Analysis: PASSED"
                }
                failure {
                    echo "Static Analysis: FAILED"
                    error("Pipeline aborted due to static analysis failures.")
                }
            }
        }

        stage('Tests') {
            steps {
                echo "Running unit tests..."
                sh 'mvn clean test'
            }
            post {
                success {
                    echo "Tests: ALL BUSINESS RULES PASSED"
                    jacoco execPattern: 'target/*.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java'
                }
                failure {
                    echo "Tests: FAILURE"
                    error("Pipeline aborted due to test failures.")
                }
            }
        }

        stage('Build') {
            steps {
                echo "Packaging the Spring Boot application..."
                sh 'mvn package -DskipTests'
            }

            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
                failure {
                    echo "Build: FAILED"
                    error("Pipeline aborted due to build failures.")
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
                success {
                    echo "Deploy: CONTAINERS ARE UP AND RUNNING ON PORT 8000"
                }
                failure {
                    echo "Deploy: FAILED TO START CONTAINERS"
                    error("Pipeline aborted due to deploy failures.")
                }
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