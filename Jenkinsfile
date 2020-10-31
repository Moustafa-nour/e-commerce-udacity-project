pipeline {
    agent {maven:3.3.3}
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
