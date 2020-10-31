pipeline {
    agent any
    stages {
        stage('build') {
            steps {
               sh 'mvn package'
            }
        }
        stage('test') {
            steps {
               sh 'mvn test'
            }
        }
    }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
