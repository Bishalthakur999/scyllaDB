use mykeyspace;
CREATE table Student( id INT, email TEXT ,name TEXT,address TEXT,age INT,gender TEXT, PRIMARY KEY ((id), email));
DESCRIBE TABLE mykeyspace.Student;
CREATE table Course( cId INT, cName TEXT,cHrs INT, PRIMARY KEY ((cId),cName));
DESCRIBE TABLE mykeyspace.Course