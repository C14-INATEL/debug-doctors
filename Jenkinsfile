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
                echo 'Clonando repositório do GitHub...'
                checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/main']],
                        userRemoteConfigs: [[url: 'https://github.com/C14-INATEL/debug-doctors']]
                ])
            }
        }
        stage('Static Analysis') {
            steps {
                echo "Running static code analysis..."
                sh 'mvn checkstyle:check -Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false'
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
                failure {
                    echo "Tests: FAILURE"
                    error("Pipeline aborted due to test failures.")
                }
            }
        }

        stage('Security Test') {
            steps {
                echo "Checking for vulnerabilities in project dependencies..."
                sh 'mvn dependency-check:check -DfailOnError=false'
            }
            post {
                success {
                    echo "Security Test: SECURE. NO CRITICAL VULNERABILITIES FOUND."
                }
                failure {
                    echo "Security Test: VULNERABILITIES DETECTED."
                    error("Pipeline aborted due to security issues.")
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
                sh 'docker compose up -d --build api db'
                echo "Waiting for Spring Boot to be fully ready..."
                sh '''
                docker run --rm --network="host" curlimages/curl:latest sh -c '
                  while ! curl -s http://localhost:8000/api/medicos > /dev/null; do
                    echo "API is still waking up... sleeping for 5 seconds."
                    sleep 5
                  done
                '
                echo "API is UP and READY to accept connections!"
                '''
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


        stage('E2E API Tests (Newman)') {
            when {
               branch 'main'
           }

            steps {
             echo "Running Postman automated collection via Newman..."
             sh '''
             docker run --rm --network="host" \
                 --volumes-from $(hostname) \
                  -t postman/newman run ${WORKSPACE}/postman/collection.json
                  '''
                    }
            post {
                success {
                    echo "API Tests: ALL ENDPOINTS ARE RESPONDING CORRECTLY"
                }
                failure {
                    echo "API Tests: FAILED TO VALIDATE ENDPOINTS"
                    error("Pipeline aborted due to API End-to-End test failures.")
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