pipeline {
    agent any 

    stages {
        stage('Clone') {
            steps {
                // Clonar el repositorio
                git branch: 'main', url: 'https://github.com/sergiorope/microlibrary.git'
            }
        }
        
        stage('Build Product Service') {
            steps {
              
                dir('businessdomain/product') {
                    sh 'mvn clean package'
                }
            }
        }


        stage('Build Docker Image for Product') {
            steps {

                dir('businessdomain/product') {
                    sh 'docker build -t sergiorope/microlibrary:microlibrary-product-v1 --no-cache --build-arg JAR_FILE=target/*.jar .'
                }
            }
        }

        stage('Deploy') {
            steps {

                sh 'docker-compose down'
                sh 'docker-compose up -d'
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
