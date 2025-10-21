node{
   stage('git Checkout'){
       git 'https://github.com/HamzaGbada/postgres-demo.git'
   }
//     stage('SonarQube analysis') {
//    withSonarQubeEnv('GTE3-sonar') { // You can override the credential to be used
//      sh 'mvn clean install -DskipTests'
//      sh 'mvn sonar:sonar -Dsonar.java.binaries=target/classes'
//    }
//  }
   stage('Build Docker Image'){
     sh '''if [[ "$(docker images -q hamzagbada18/gte3:1.0 2> /dev/null)" == "" ]]; then
                   docker build --force-rm -t hamzagbada18/gte3:1.0 .
                 fi'''
   }
   stage('Push Docker Image to dockerHub'){
       withCredentials([string(credentialsId: 'docker-pwd', variable: 'dockerHubPass')]) {
            sh 'docker login -u hamzagbada18 -p ${dockerHubPass}'
            sh 'docker push hamzagbada18/gte3:1.0'
        }
    }
    stage('Run the application'){
        sh 'docker-compose up -d --force-recreate'
    }
}
