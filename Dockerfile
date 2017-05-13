# Dockerfile for maven image is at
# https://github.com/carlossg/docker-maven/blob/ecf54b9839caed8aa2bcf9b8f7bb19594634ee89/jdk-8/onbuild/Dockerfile
# This image will copy entire source code under /usr/src/app in container and then run 'mvn install'

# docker build -t myjavaproject:1.0 .
# docker run -i --name myjavaprojectcontainer -t myjavaproject:1.0

# To override CMD
# docker run -i --name myjavaprojectcontainer -t myjavaproject:1.0 /bin/bash


FROM maven:3.3-jdk-8-onbuild
#ADD target/MyJavaProject-0.0.1-SNAPSHOT.jar /opt/demo/MyJavaProject-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","target/MyJavaProject-0.0.1-SNAPSHOT.jar"]

