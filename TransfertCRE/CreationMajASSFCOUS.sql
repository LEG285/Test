-- --------------------------------------------------------------------
-- Creation table ASSFCOUS dans NPFIC & AVTGPMDR   
-- --------------------------------------------------------------------

-- NPFIC -------------------------------------------------------------- 
CREATE schema NPFIC;

CREATE TABLE NPFIC.ASSFCOUS ( 
	ASCNUMCO DECIMAL(4, 0) NOT NULL DEFAULT 0 , 
	ASCTYPCS CHAR(8) NOT NULL DEFAULT '' , 
	ASCLIBCS CHAR(50)  NOT NULL DEFAULT '' , 
	ASCCOMSF CHAR(5)  NOT NULL DEFAULT '' , 
	                                                                                                                                                                                                                     
	ASCDATCR DATE NOT NULL DEFAULT CURRENT_DATE , 
	ASCDATMD DATE NOT NULL DEFAULT CURRENT_DATE , 
	ASCUSRCR CHAR(8)  NOT NULL DEFAULT '' , 
	ASCUSRMD CHAR(8)  NOT NULL DEFAULT '' , 
	ASCTP DECIMAL(1, 0) NOT NULL DEFAULT 0 , 
	ASCBATCH DECIMAL(1, 0) NOT NULL DEFAULT 0 , 
	ASCREPOR DECIMAL(3, 0) NOT NULL DEFAULT 0 , 
	ASTCOUR CHAR(30)  NOT NULL DEFAULT '' , 
	ASTEDT CHAR(10)  NOT NULL DEFAULT '' , 
	ASTSIG CHAR(10) NOT NULL DEFAULT '' , 
	ASTBTW DECIMAL(1, 0) NOT NULL DEFAULT 0 )   
; 






-- AVTGPMDR (DEV) --------------------------------------
DROP TABLE AVTGPMDR.ASSFCOUS;

CREATE TABLE AVTGPMDR.ASSFCOUS ( 
	ASCNUMCO DECIMAL(4, 0) NOT NULL DEFAULT 0 , 
	ASCTYPCS CHAR(8) NOT NULL DEFAULT '' , 
	ASCLIBCS CHAR(50)  NOT NULL DEFAULT '' , 
	ASCCOMSF CHAR(5)  NOT NULL DEFAULT '' , 
	                                                                                                                                                                                                                     
	ASCDATCR DATE NOT NULL DEFAULT CURRENT_DATE , 
	ASCDATMD DATE NOT NULL DEFAULT CURRENT_DATE , 
	ASCUSRCR CHAR(8)  NOT NULL DEFAULT '' , 
	ASCUSRMD CHAR(8)  NOT NULL DEFAULT '' , 
	ASCTP DECIMAL(1, 0) NOT NULL DEFAULT 0 , 
	ASCBATCH DECIMAL(1, 0) NOT NULL DEFAULT 0 , 
	ASCREPOR DECIMAL(3, 0) NOT NULL DEFAULT 0 , 
	ASTCOUR CHAR(30)  NOT NULL DEFAULT '' , 
	ASTEDT CHAR(10)  NOT NULL DEFAULT '' , 
	ASTSIG CHAR(10) NOT NULL DEFAULT '' , 
	ASTBTW DECIMAL(1, 0) NOT NULL DEFAULT 0 )   
; 

-- Vérification
SELECT * FROM AVTGPMDR.ASSFCOUS;   


