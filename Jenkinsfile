pipeline {
   agent any

   tools {
      maven "3"
   }

   stages {
      stage('Build') {
         steps {
            git branch: 'master',
                credentialsId: 'GitHub',
                url: 'git@github.com:ariielm/tasks-api.git'

            sh "mvn clean package"
         }

//          post {
//             // If Maven was able to run the tests, even if some of the test
//             // failed, record the test results and archive the jar file.
//             success {
//                junit '**/target/surefire-reports/TEST-*.xml'
//                archiveArtifacts 'target/*.jar'
//             }
//          }
      }
   }
}