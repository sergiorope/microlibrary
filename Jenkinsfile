pipeline {
    agent any 

    environment {
        BUSINESS_DOMAIN_SERVICES = ['product', 'customer', 'partner', 'loan', 'loanline']
        INFRASTRUCTURE_DOMAIN_SERVICES = ['eurekaServer', 'apigateway']
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning repository...'
                git branch: "main", url: "https://github.com/sergiorope/microlibrary"
            }
        }

        stage('Build Business Domain Services') {
            steps {
                script {
                    for (service in BUSINESS_DOMAIN_SERVICES) {
                        dir("businessdomain/${service}") {
                            bat 'mvn clean package'
                        }
                    }
                }
            }
        }

        stage('Build Infrastructure Services') {
            steps {
                script {
                    for (service in INFRASTRUCTURE_DOMAIN_SERVICES) {
                        dir("infrastructure/${service}") {
                            bat 'mvn clean package'
                        }
                    }
                }
            }
        }

        stage('Remove Old Docker Images') {
            steps {
                script {
                    for (service in BUSINESS_DOMAIN_SERVICES + INFRASTRUCTURE_DOMAIN_SERVICES) {
                        bat "docker rmi -f sergiorodper/microlibrary:microlibrary-${service}-v1 || echo 'No existing image to remove for ${service}'"
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    for (service in BUSINESS_DOMAIN_SERVICES) {
                        dir("businessdomain/${service}") {
                            bat "docker build -t sergiorodper/microlibrary:microlibrary-${service}-v1 --no-cache --build-arg JAR_FILE=target/*.jar ."
                        }
                    }
                    for (service in INFRASTRUCTURE_DOMAIN_SERVICES) {
                        dir("infrastructure/${service}") {
                            bat "docker build -t sergiorodper/microlibrary:microlibrary-${service}-v1 --no-cache --build-arg JAR_FILE=target/*.jar ."
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                dir(".") {
                    bat 'echo Current directory: %cd%'
                    bat 'docker-compose down'
                    bat 'docker-compose up -d --remove-orphans'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
