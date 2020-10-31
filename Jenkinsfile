pipeline {
    agent any
    checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'b101c97a-e29f-43e0-96bd-6ca03dbe6bba', url: 'https://github.com/Moustafa-nour/e-commerce-udacity-project.git']]])
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
