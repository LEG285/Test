-- Creation table BCOUSFD - 

-- --------------------------------------------------------------------
-- AVTGPMDR (DEV) 
-- --------------------------------------------------------------------

drop table AVTGPMDR.BCOUSFD;

CREATE TABLE AVTGPMDR.BCOUSFD ( 
	FECCOU DECIMAL(11, 0) NOT NULL DEFAULT 0 , 
	FECBCOU CHAR(8) NOT NULL DEFAULT '' , 
	FEVERS DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FENLIGC DECIMAL(10, 0) NOT NULL DEFAULT 0 , 
	FENIBLO DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FENBBLO DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FELGBLO DECIMAL(10, 0) NOT NULL DEFAULT 0 , 
	FEZDETCOU VARCHAR(2000) NOT NULL DEFAULT '' )   
; 

-- Vérification
select * from AVTGPMDR.BCOUSFD where FECCOU = 25297000005;   

select count(*) from AVTGPMDR.BCOUSFD;

delete from AVTGPMDR.BCOUSFD;

-- --------------------------------------------------------------------
-- AVTGPMDTA (PROD) 
-- --------------------------------------------------------------------

DROP TABLE AVTGPMDTA.BCOUSFD;

CREATE TABLE AVTGPMDTA.BCOUSFD ( 
	FECCOU DECIMAL(11, 0) NOT NULL DEFAULT 0 , 
	FECBCOU CHAR(8) NOT NULL DEFAULT '' , 
	FEVERS DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FENLIGC DECIMAL(10, 0) NOT NULL DEFAULT 0 , 
	FENIBLO DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FENBBLO DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FELGBLO DECIMAL(10, 0) NOT NULL DEFAULT 0 , 
	FEZDETCOU VARCHAR(2000) NOT NULL DEFAULT '' )   
; 

-- Vérification
select * from AVTGPMDTA.BCOUSFD 
--where FECCOU =  25293000011 
;

-- --------------------------------------------------------------------
-- AVTMPGDTA (RECETTE) 
-- --------------------------------------------------------------------

DROP TABLE AVTMPGDTA.BCOUSFD;

CREATE TABLE AVTMPGDTA.BCOUSFD ( 
	FECCOU DECIMAL(11, 0) NOT NULL DEFAULT 0 , 
	FECBCOU CHAR(8) NOT NULL DEFAULT '' , 
	FEVERS DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FENLIGC DECIMAL(10, 0) NOT NULL DEFAULT 0 , 
	FENIBLO DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FENBBLO DECIMAL(2, 0) NOT NULL DEFAULT 0 , 
	FELGBLO DECIMAL(10, 0) NOT NULL DEFAULT 0 , 
	FEZDETCOU VARCHAR(2000) NOT NULL DEFAULT '' )   
; 

-- Vérification
select * from AVTMPGDTA.BCOUSFD where FECCOU = 25297000005;   

select count(*) from avtgpmdta.bcousfd;






