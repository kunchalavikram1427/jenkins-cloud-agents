pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            app: test
        spec:
          containers:
          - name: maven
            image: maven:3.8.3-adoptopenjdk-11
            command:
            - cat
            tty: true
          - name: git
            image: bitnami/git:latest
            command:
            - cat
            tty: true
        '''
    }
  }
  stages {
    stage('Clone SCM') {
      steps{
        container('git') {
          sh 'git clone https://github.com/kunchalavikram1427/maven-employee-web-application.git .'
        }
      }
    }
    stage('Build Project') {
      steps {
        container('maven') {
          sh 'mvn clean package'
        }
      }
    }
  }
}