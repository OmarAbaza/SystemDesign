# Project files management system

Spring boot application with postgres DB to manage files on multible layers.

A permission group "admin" and three user ids are created automatically for testing purposes with the following permission levels
view@example.com VIEW
edit@example.com EDIT
all@example.com VIEW and EDIT
## Create space API
    POST localhost:8080/items/space
	Request Headers:
		Content-Type: application/json
	Request Body:
		{
		    "name" : "stc-assessments",
		    "permissionGroupName" : "admin"
		}
	Ex:
		curl --location --request POST 'localhost:8080/items/space' --header 'Content-Type: application/json' --data-raw '{"name" : "stc-assessments","permissionGroupName" : "admin"}'
## Create folder API
    POST localhost:8080/items/folder
	Request Headers:
		Content-Type: application/json
		userId: edit@example.com
	Request Body:
		{
		    "name" : "backend",
		    "permissionGroupName" : "admin",
		    "parentType" : "Space",
		    "parentName" : "stc-assessments"
		}
	Ex:
		curl --location --request POST 'localhost:8080/items/folder' --header 'Content-Type: application/json' --header 'userId: edit@example.com' --data-raw '{"name" : "backend","permissionGroupName" : "admin", "parentType" : "Space", "parentName" : "stc-assessments"}'
## Create file API
POST localhost:8080/items/file
	Request Headers:
		Content-Type: multipart/form-data
		userId: edit@example.com
	Form Data:
		name:assessment.pdf
		permissionGroupName:admin
		parentType:Folder
		parentName:backend
	Ex:
		curl --location --request POST 'localhost:8080/items/file' --header 'userId: edit@example.com' --form 'name="assessment.pdf"' --form 'permissionGroupName="admin"' --form 'parentType="Folder"' --form 'parentName="backend"' --form 'file=@"assessment.pdf"'
## Get file metadata API
	GET localhost:8080/items/file
	Request Headers:
		Content-Type: application/json
		userId: view@example.com
	Query Params:
		name:assessment.pdf
	Ex:
		curl --location --request GET 'localhost:8080/items/file?name=assessment.pdf' --header 'userId: view@example.com'

## Download file API
	GET localhost:8080/items/file/download
	Request Headers:
		Content-Type: application/json
		userId: view@example.com
	Query Params:
		name:assessment.pdf
	Ex:
		curl --location --request GET 'localhost:8080/items/file/download?name=assessment.pdf' --header 'userId: view@example.com'
## Deployment

To deploy this project run

docker compose -f "docker-compose.yml" up -d --build 