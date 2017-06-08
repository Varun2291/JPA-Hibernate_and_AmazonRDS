# JPA-Hibernate_and_AmazonRDS

Included Files:
1) .war file
2) XHTML files and Java files
3) Readme File
4) index.html

Instructions :

1) Install Eclipse, WinSCP, Tomcat apache(version 7.0)
2) Create the Homepage and the "Student survey form" using eclipse dynamic web project
3) Export the project into a war file
4) Create new instance in EC2 and a bucket in the S3 Storage cloud
5) Connect to the EC2 instance using the public DNS as the hostname and username as "bitnami".
6) Include the private key by choosing the appropriate .ppk file
7) Once the connection is established drag and drop (using WinSCP) the .war file in the /home/bitnami/
8) Copy the files from the home folder to the webapps folder with this command:
   sudo cp -r Swe645_Assignment_4.war /home/bitnami/stack/apache-tomcat/webapps
9) The project is now uploaded and the home page on the S3 bucket is deployed on the cloud and is made publicly accessible.
10) The project can be accessed using the following link: 	
	https://s3-us-west-2.amazonaws.com/yourBucket/index.html


