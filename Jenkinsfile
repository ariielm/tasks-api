def remote = [:]
    remote.name = "ariielm-server"
    remote.host = "104.236.41.39"
    remote.allowAnyHosts = true

pipeline {
    agent any

    tools {
        maven "M3"
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

        stage('build & deploy docker image') {
            steps {
                script {
                    docker.withTool("docker-latest") {
                        docker.withRegistry('https://index.docker.io/v1/','dockerhub-credentials') {
                            def tasksApiImage = docker.build("ariielm/tasks-api")
                            tasksApiImage.push()
                        }
                    }
                }
            }
        }

        stage('deploy') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'SSH-ariielm-server', keyFileVariable: 'identity', passphraseVariable: 'passphrase', usernameVariable: 'userName')]) {
                    script {
                        remote.user = userName
                        remote.identityFile = identity
                        remote.passphrase = passphrase
                    }

                    sshPut remote: remote, from: 'deploy.py', into: '.'
                    sshCommand remote: remote, command: 'python deploy.py'

                }
            }
        }
    }

}