/*crear la base de datos*/
create database aulavirtual;
/*usar la base de datos*/
use aulavirtual;
/*creando tablas*/
CREATE TABLE Archivo
(
	codArchivo           INTEGER NOT NULL,
    codAsistencia        INTEGER NOT NULL,
	nombreArchivo        CHAR(30) NULL,
	descripcion          TEXT NULL,
	archivo              LONGBLOB NULL
);

ALTER TABLE Archivo
ADD PRIMARY KEY (codArchivo,codAsistencia);

CREATE TABLE Asistencia
(
	codAsistencia        INTEGER NOT NULL,
	estado               CHAR(15) NULL,
	fecha                DATE NULL,
	codSemana            CHAR(2) NULL,
	codCursoEstudianteSemestre CHAR(10) NULL,
	horaInicio           DATE NULL,
	horaFin              DATE NULL
);

ALTER TABLE Asistencia
ADD PRIMARY KEY (codAsistencia);

CREATE TABLE Ciclo
(
	numCiclo             CHAR(10) NOT NULL,
	nombreCiclo          CHAR(18) NULL
);

ALTER TABLE Ciclo
ADD PRIMARY KEY (numCiclo);

CREATE TABLE Curso
(
	codCurso             CHAR(5) NOT NULL,
	nombre_curso         CHAR(50) NULL,
	numCiclo             CHAR(10) NULL,
	dni                  CHAR(8) NULL,
	codEscuela           CHAR(3) NULL,
	codFacultad          CHAR(3) NULL
);

ALTER TABLE Curso
ADD PRIMARY KEY (codCurso);

CREATE TABLE Curso_EstudianteSemestre
(
	codCurso             CHAR(5) NOT NULL,
	dni                  CHAR(8) NOT NULL,
	codSemestre          CHAR(5) NOT NULL,
	codCursoEstudianteSemestre CHAR(10) NOT NULL
);

ALTER TABLE Curso_EstudianteSemestre
ADD PRIMARY KEY (codCursoEstudianteSemestre);

CREATE TABLE Escuela
(
	codEscuela           CHAR(3) NOT NULL,
	nombreEscuela        CHAR(30) NULL,
	codFacultad          CHAR(3) NOT NULL
);

ALTER TABLE Escuela
ADD PRIMARY KEY (codEscuela,codFacultad);

CREATE TABLE Estudiante
(
	dni                  CHAR(8) NOT NULL,
	codEstudiante        CHAR(10) NULL,
	codEscuela           CHAR(3) NULL,
	codFacultad          CHAR(3) NULL
);

ALTER TABLE Estudiante
ADD PRIMARY KEY (dni);

CREATE TABLE Facultad
(
	codFacultad          CHAR(3) NOT NULL,
	nombreFacultad       CHAR(50) NULL
);

ALTER TABLE Facultad
ADD PRIMARY KEY (codFacultad);

CREATE TABLE Login
(
	dni                  CHAR(8) NOT NULL,
	contraseña           CHAR(10) NULL
);

ALTER TABLE Login
ADD PRIMARY KEY (dni);

CREATE TABLE Persona
(
	dni                  CHAR(8) NOT NULL,
	nombre               CHAR(80) NULL,
	fechaNacimiento      DATE NULL,
	sexo                 CHAR(9) NULL,
	direccion            CHAR(50) NULL,
    foto                 LONGBLOB NULL
);

ALTER TABLE Persona
ADD PRIMARY KEY (dni);

CREATE TABLE Profesor
(
	dni                  CHAR(8) NOT NULL,
	codEscuela           CHAR(3) NULL,
	codFacultad          CHAR(3) NULL
);

ALTER TABLE Profesor
ADD PRIMARY KEY (dni);

CREATE TABLE Semana
(
	codSemana            CHAR(2) NOT NULL,
	codSemestre          CHAR(5) NULL,
	nombreSemana         CHAR(10) NULL
);

ALTER TABLE Semana
ADD PRIMARY KEY (codSemana);

CREATE TABLE Semestre
(
	codSemestre          CHAR(5) NOT NULL,
	nombreSemestre       CHAR(10) NULL
);

ALTER TABLE Semestre
ADD PRIMARY KEY (codSemestre);

CREATE TABLE Silabu
(
	codCursoEstudianteSemestre CHAR(10) NOT NULL,
	nombreSilabu         CHAR(30) NULL,
	archivopdf           MEDIUMBLOB NULL
);

ALTER TABLE Silabu
ADD PRIMARY KEY (codCursoEstudianteSemestre);

ALTER TABLE Archivo
ADD FOREIGN KEY R_66 (codAsistencia) REFERENCES Asistencia (codAsistencia);

ALTER TABLE Asistencia
ADD FOREIGN KEY R_52 (codSemana) REFERENCES Semana (codSemana);

ALTER TABLE Asistencia
ADD FOREIGN KEY R_61 (codCursoEstudianteSemestre) REFERENCES Curso_EstudianteSemestre (codCursoEstudianteSemestre);

ALTER TABLE Curso
ADD FOREIGN KEY R_18 (numCiclo) REFERENCES Ciclo (numCiclo);

ALTER TABLE Curso
ADD FOREIGN KEY R_20 (dni) REFERENCES Profesor (dni);

ALTER TABLE Curso
ADD FOREIGN KEY R_23 (codEscuela, codFacultad) REFERENCES Escuela (codEscuela, codFacultad);

ALTER TABLE Curso_EstudianteSemestre
ADD FOREIGN KEY R_28 (codCurso) REFERENCES Curso (codCurso);

ALTER TABLE Curso_EstudianteSemestre
ADD FOREIGN KEY R_30 (dni) REFERENCES Estudiante (dni);

ALTER TABLE Curso_EstudianteSemestre
ADD FOREIGN KEY R_60 (codSemestre) REFERENCES Semestre (codSemestre);

ALTER TABLE Escuela
ADD FOREIGN KEY R_5 (codFacultad) REFERENCES Facultad (codFacultad);

ALTER TABLE Estudiante
ADD FOREIGN KEY (dni) REFERENCES Persona(dni)
		ON DELETE CASCADE;

ALTER TABLE Estudiante
ADD FOREIGN KEY R_17 (codEscuela, codFacultad) REFERENCES Escuela (codEscuela, codFacultad);

ALTER TABLE Login
ADD FOREIGN KEY R_69 (dni) REFERENCES Persona (dni);

ALTER TABLE Profesor
ADD FOREIGN KEY (dni) REFERENCES Persona(dni)
		ON DELETE CASCADE;

ALTER TABLE Profesor
ADD FOREIGN KEY R_16 (codEscuela, codFacultad) REFERENCES Escuela (codEscuela, codFacultad);

ALTER TABLE Semana
ADD FOREIGN KEY R_51 (codSemestre) REFERENCES Semestre (codSemestre);

ALTER TABLE Silabu
ADD FOREIGN KEY R_65 (codCursoEstudianteSemestre) REFERENCES Curso_EstudianteSemestre (codCursoEstudianteSemestre);
/*le añado el auto incremento a curso*/
ALTER TABLE Archivo CHANGE codArchivo codArchivo INTEGER NOT NULL AUTO_INCREMENT