def remote = [:]
    remote.name = "ariielm-server"
    remote.host = "104.236.41.39"
    remote.allowAnyHosts = true

pipeline {
    agent any

    tools {
        maven "3"
    }

    stages {
        stage('git clone') {
            steps {
                git branch: 'master',
                    credentialsId: 'GitHub',
                    url: 'git@github.com:ariielm/tasks-api.git'
            }
        }

        stage('build & tests') {
            steps {
                sh "mvn clean package"
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/tasks-api*.jar'
                }
            }
        }

//         stage('release docker image') {
//             steps {
//
//             }
//         }

        stage('deploy') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'GitHub', keyFileVariable: 'identity', passphraseVariable: 'passphrase', usernameVariable: 'userName')]) {
                    script {
                        remote.user = userName
                        remote.identityFile = identity
                        remote.passphrase = passphrase
                    }

                    sshPut remote: remote, from: 'target/tasks-api-0.0.1-SNAPSHOT.jar', into: '.'
                }
            }
        }
    }

}