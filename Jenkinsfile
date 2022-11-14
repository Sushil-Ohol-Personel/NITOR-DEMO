pipeline {
    
    agent any
    
    tools {
        git 'Default'
        maven 'Maven'
        snyk 'Snyk-latest'
    }
    
    environment {
        DOCKERHUB_USERNAME = "saurabh.bhagat"
        APP_NAME = "devsecops"
        IMAGE_TAG = "${BUILD_NUMBER}"
        IMAGE_NAME = "${DOCKERHUB_USERNAME}" + "/" + "${APP_NAME}" 
        REGISTRY_CREDS = 'Dockerhub'
        //JAVA_HOME='/usr/lib/jvm/java-8-openjdk-amd64'
        JAVA_HOME='/usr/lib/jvm/java-11-openjdk-amd64'
        sonar='/home/ashwini/sonar-scanner-4.7.0.2747-linux/bin/sonar-scanner'
        SNYK_TOKEN='c59b4dde-f427-4e3c-8799-276ebfb23dc5'
          
    }

    stages {
        
        /*stage('Clean workspace'){
            steps{
                cleanWs()
            }
        }*/
       
        stage('Git Clone Source code') {
            steps {
                git credentialsId: 'git',
                    url: 'https://github.com/Saurabh-bhagat/javahib',
                    branch: 'main'
            }
        }
        
        stage('Build stage'){
            steps{
                sh 'mvn clean package'
            }
        }
        
       stage('Sonar scan'){
            steps{ 
                script{
                    
                withSonarQubeEnv('sonarqube'){
               
                sh'''
                mvn clean verify sonar:sonar \
                -Dsonar.projectKey=Javaspirng \
                -Dsonar.host.url=http://10.21.12.183:9000 \
                -Dsonar.login=sqp_405936fb695d195ee4201817ac58e4c8cbe5f653
                
               '''
                // publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: '', useWrapperFileDirectly: true])
              
                 }
              }
            }   
        }
    
        stage('Snyk test'){
            steps{
                snykSecurity(
                    organisation:'saurabh-bhagat', 
                    projectName:'javahib',
                    severity: 'medium', 
                    snykInstallation: 'Snyk-latest', 
                    snykTokenId: 'Snyk-token',
                    failOnIssues: 'false'
                )
            }
        }
        
       stage('Build Docker image'){
            steps{
                script{
                        docker_image = docker.build "${IMAGE_NAME}"
                 }
             }
        }
        
    stage('Docker image scan'){
       steps{
          	script{
          	    try {
                    sh '''
        			    snyk auth ${SNYK_TOKEN}
        			    snyk container test ${IMAGE_NAME} --json-file-output=reports.json
        			'''
                } catch (Exception ex) {
                    currentBuild.result = 'SUCCESS'
                    sh 'snyk-to-html -i reports.json -o reports.html'
                }          	    
          	    
          	}
        }
    }
    
    stage('Docker image publish'){
       steps{
          	script{
          	    publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'reports.html', reportName: 'Synk_Report', reportTitles: '', useWrapperFileDirectly: true])
          	}
        }
    }
    
    
     stage('DAST scan'){
         steps{
             script{
                
             sh '''/home/ashwini/arachni-1.6.1.3-0.6.1.1/bin/arachni https://example.com --report-save-path=${BUILD_TAG}.afr

                    /home/ashwini/arachni-1.6.1.3-0.6.1.1/bin/arachni_reporter ${BUILD_TAG}.afr --reporter=html:outfile=${BUILD_TAG}.zip

                    unzip -o ${BUILD_TAG}.zip

                    rm -f ${BUILD_TAG}.zip
                    
                '''
                
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'reports1.html', reportName: 'DAST_Report', reportTitles: '', useWrapperFileDirectly: true])
                
             }
                
         }
     }
    
    }    
}
