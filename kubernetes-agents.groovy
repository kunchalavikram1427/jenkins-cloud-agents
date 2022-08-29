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
            image: maven:alpine
            command:
            - cat
            tty: true
          - name: node
            image: node:7-alpine
            command:
            - cat
            tty: true
        '''
    }
  }
  stages {
    stage('Test Maven & Node') {
      steps {
        container('maven') {
          sh 'mvn -version'
        }
        container('node') {
          sh 'node --version'
        }
      }
    }
  }
}