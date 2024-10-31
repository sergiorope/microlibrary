pipeline {
    agent any 

    environment {
        // Usamos cadenas separadas por comas
        BUSINESS_DOMAIN_SERVICES = 'product,customer,partner,loan,loanline'
        INFRASTRUCTURE_DOMAIN_SERVICES = 'eurekaServer,apigateway'
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
                    // Convertimos la cadena a una lista
                    def businessServices = BUSINESS_DOMAIN_SERVICES.split(',')
                    for (service in businessServices) {
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
                    // Convertimos la cadena a una lista
                    def infrastructureServices = INFRASTRUCTURE_DOMAIN_SERVICES.split(',')
                    for (service in infrastructureServices) {
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
                    // Unimos las listas de servicios
                    def allServices = BUSINESS_DOMAIN_SERVICES.split(',') + INFRASTRUCTURE_DOMAIN_SERVICES.split(',')
                    for (service in allServices) {
                        bat "docker rmi -f sergiorodper/microlibrary:microlibrary-${service}-v1 || echo 'No existing image to remove for ${service}'"
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // Construimos imágenes para los servicios de negocio
                    def businessServices = BUSINESS_DOMAIN_SERVICES.split(',')
                    for (service in businessServices) {
                        dir("businessdomain/${service}") {
                            bat "docker build -t sergiorodper/microlibrary:microlibrary-${service}-v1 --no-cache --build-arg JAR_FILE=target/*.jar ."
                        }
                    }
                    // Construimos imágenes para los servicios de infraestructura
                    def infrastructureServices = INFRASTRUCTURE_DOMAIN_SERVICES.split(',')
                    for (service in infrastructureServices) {
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
