pipeline {
    agent any 

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning repository...'
                git branch: "main", url: "https://github.com/sergiorope/microlibrary"
            }
        }
        
        stage('Build Product Service') {
            steps {
                dir("businessdomain/product") {
                    bat 'mvn clean package'
                }
            }
        }
        
        stage('Remove Old Product Docker Image') {
            steps {
                bat "docker rmi -f sergiorodper/microlibrary:microlibrary-product-v1 || echo 'No existing image to remove for product'"
            }
        }

        stage('Build Docker Image for Product') {
            steps {
                dir("businessdomain/product") {
                    bat 'docker build -t sergiorodper/microlibrary:microlibrary-product-v1 --no-cache --build-arg JAR_FILE=target/*.jar .'
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
