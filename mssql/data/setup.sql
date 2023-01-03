USE master
DROP DATABASE IF EXISTS [WMSDatabase]
GO
CREATE DATABASE [WMSDatabase]
GO
USE [WMSDatabase]
GO
DROP SCHEMA IF EXISTS [UAM]
GO
CREATE SCHEMA [UAM]
GO

IF SUSER_ID ('WMSDefaultLogin') IS NULL
CREATE LOGIN WMSDefaultLogin WITH PASSWORD = 'DefaultPassword123!'
GO
DROP USER IF EXISTS WMSDefaultUser
GO
CREATE USER WMSDefaultUser FOR LOGIN WMSDefaultLogin WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE db_owner ADD MEMBER WMSDefaultUser

DROP TABLE IF EXISTS [UAM].[Users]
GO
CREATE TABLE [UAM].[Users] (
id smallint IDENTITY(1,1) PRIMARY KEY,
username varchar(50) NULL Unique,
[password] varchar(255) NULL,
[name] varchar(24) NULL,
surname varchar(24) NULL,
roleId tinyint NULL
);
GO
DROP TABLE IF EXISTS [UAM].[Roles]
GO
CREATE TABLE [UAM].[Roles] (
id tinyint IDENTITY(1,1) PRIMARY KEY,
[name] varchar(10) NULL,
);
GO

ALTER TABLE [UAM].[Users]
ADD CONSTRAINT FK_UsersRoles FOREIGN KEY (roleId) REFERENCES [UAM].[Roles] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP SCHEMA IF EXISTS [Products]
GO
CREATE SCHEMA [Products]
GO
DROP TABLE IF EXISTS [Products].[Units]
GO
CREATE TABLE [Products].[Units] (
id tinyint IDENTITY (1,1) PRIMARY KEY,
[name] varchar(10) NULL
);
GO
DROP TABLE IF EXISTS [Products].[Articles]
GO
CREATE TABLE [Products].[Articles] (
id int IDENTITY(1,1) PRIMARY KEY,
[name] varchar(50) NULL,
unitId tinyint NULL,
[weight] decimal(6,2) NULL,
articleCode varchar(10) NULL,
creationDate datetime NULL,
modificationDate datetime NULL,
userId smallint NULL
);
GO
ALTER TABLE [Products].[Articles]
ADD CONSTRAINT FK_ArticlesUnits FOREIGN KEY (unitID) REFERENCES [Products].[Units] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO

DROP SCHEMA IF EXISTS Operations
GO
CREATE SCHEMA Operations
GO

DROP TABLE IF EXISTS [Operations].[ReceiptElements]
GO
CREATE TABLE [Operations].[ReceiptElements] (
id int IDENTITY(1,1) PRIMARY KEY,
operationId int NULL,
operationType varchar(12) NULL,
articleId int NULL,
userId smallint NULL,
quantity smallint NULL,
[weight] decimal(6,2) NULL, 
localizationId smallint NULL,
creationDate datetime NULL,
warehouseId tinyint NULL
);
GO

ALTER TABLE [Operations].[ReceiptElements]
ADD CONSTRAINT FK_ReceiptElementsArticles FOREIGN KEY (articleId) REFERENCES [Products].[Articles] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP TABLE IF EXISTS [Operations].[MovementElements]
GO
CREATE TABLE [Operations].[MovementElements] (
id int IDENTITY(1,1) PRIMARY KEY,
operationId int NULL,
operationType varchar(12) NULL,
articleId int NULL,
userId smallint NULL,
quantity smallint NULL,
[weight] decimal(6,2) NULL,
sourceLocalizationId smallint NULL,
targetLocalizationId smallint NULL,
creationDate datetime,
sourceWarehouseId tinyint NULL,
targetWarehouseId tinyint NULL
);
GO
ALTER TABLE [Operations].[MovementElements]
ADD CONSTRAINT FK_MovementElementsArticles FOREIGN KEY (articleId) REFERENCES [Products].[Articles] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP TABLE IF EXISTS [Operations].[ReleaseElements]
GO
CREATE TABLE [Operations].[ReleaseElements] (
id int IDENTITY(1,1) PRIMARY KEY,
operationId int NULL,
operationType varchar(12) NULL,
articleId int NULL,
userId smallint NULL,
quantity smallint NULL,
[weight] decimal (6,2) NULL,
localizationId smallint NULL,
creationDate datetime NULL,
warehouseId tinyint NULL,
sourceWarehouseId tinyint NULL,
targetWarehouseId tinyint NULL
);

ALTER TABLE [Operations].[ReleaseElements]
ADD CONSTRAINT FK_ReleaseElementsArticles FOREIGN KEY (articleId) REFERENCES [Products].[Articles] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP TABLE IF EXISTS [Operations].[Receipts]
GO
CREATE TABLE [Operations].[Receipts] (
id int IDENTITY (1,1) PRIMARY KEY,
operationType varchar(12) NULL,
documentNumber varchar(50) NULL,
creationDate datetime NULL,
modificationDate datetime NULL,
warehouseId tinyint NULL,
customerId int NULL,
[description] varchar(50) NULL,
userId smallint NULL
);
GO
ALTER TABLE [Operations].[ReceiptElements]
ADD CONSTRAINT FK_ReceiptsElemntsReceipts FOREIGN KEY (operationId) REFERENCES [Operations].[Receipts] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO
ALTER TABLE [Operations].[Receipts]
ADD CONSTRAINT FK_ReceiptsUsers FOREIGN KEY (userId) REFERENCES [UAM].[Users] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP TABLE IF EXISTS [Operations].[Movements]
GO
CREATE TABLE [Operations].[Movements] (
id int IDENTITY (1,1) PRIMARY KEY,
operationType varchar(12) NULL,
documentNumber varchar(50) NULL,
creationDate datetime NULL,
modificationDate datetime NULL,
customerId int NULL,
[description] varchar(50) NULL,
userId smallint NULL,
sourceWarehouseId tinyint NULL,
targetWarehouseId tinyint NULL,
);
GO

ALTER TABLE [Operations].[MovementElements]
ADD CONSTRAINT FK_MovementElementsMovements FOREIGN KEY (operationId) REFERENCES [Operations].[Movements] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO

ALTER TABLE [Operations].[Movements]
ADD CONSTRAINT FK_MovementsUsers FOREIGN KEY (userId) REFERENCES [UAM].[Users] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO

DROP TABLE IF EXISTS [Operations].[Releases]
GO
CREATE TABLE [Operations].[Releases] (
id int IDENTITY(1,1) PRIMARY KEY,
operationType varchar(12) NULL,
documentNumber varchar(50) NULL,
creationDate datetime NULL,
modificationDate datetime NULL,
warehouseId tinyint NULL,
customerId int NULL,
[description] varchar(50) NULL,
userId smallint NULL
);
GO

ALTER TABLE [Operations].[ReleaseElements]
ADD CONSTRAINT FK_ReleaseElementsReleases FOREIGN KEY (operationId) REFERENCES [Operations].[Releases] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO

ALTER TABLE [Operations].[Releases] 
ADD CONSTRAINT FK_ReleasesUsers FOREIGN KEY (userId) REFERENCES [UAM].[Users] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO

DROP SCHEMA IF EXISTS [Counterparties]
GO
CREATE SCHEMA [Counterparties] 
GO

DROP TABLE IF EXISTS [Counterparties].[Customers] 
GO
CREATE TABLE [Counterparties].[Customers] (
id int IDENTITY (1,1) PRIMARY KEY,
[name] varchar (200) NULL,
street varchar(50) NULL,
city varchar(50) NULL,
postalCode varchar(10) NULL,
country varchar (30) NULL,
email varchar(50) NULL,
phoneNumber varchar(20) NULL,
contactPersonId int NULL
);
GO

DROP TABLE IF EXISTS [Counterparties].[CustomersContactPerson] 
GO
CREATE TABLE [Counterparties].[CustomersContactPerson] (
id int IDENTITY (1,1) PRIMARY KEY,
[name] varchar(50) NULL,
surname varchar(50) NULL,
email varchar(50) NULL,
phoneNumber varchar(20) NULL,
);
GO

ALTER TABLE [Counterparties].[Customers]
ADD CONSTRAINT FK_CustomersCustomersContactPerson FOREIGN KEY (contactPersonId) REFERENCES [Counterparties].[CustomersContactPerson] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO

DROP SCHEMA IF EXISTS [Warehouses]
GO
CREATE SCHEMA [Warehouses]
GO

DROP TABLE IF EXISTS [Warehouses].[Warehouses]
GO
CREATE TABLE [Warehouses].[Warehouses] (
id tinyint IDENTITY (1,1) PRIMARY KEY,
[name] varchar(50) NULL,
displayName varchar(5) NULL,
creationDate datetime NULL,
modificationDate datetime NULL,
capacity decimal(8,2) NULL
);

DROP TABLE IF EXISTS [Warehouses].[Rows]
GO 
CREATE TABLE [Warehouses].[Rows] (
id tinyint IDENTITY (1,1) PRIMARY KEY,
[name] varchar(1) NULL,
capacity decimal (8,2) NULL,
warehouseId tinyint NULL
);
GO

ALTER TABLE [Warehouses].[Rows]
ADD CONSTRAINT FK_RowsWarehouses FOREIGN KEY (warehouseId) REFERENCES [Warehouses].[Warehouses] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP TABLE IF EXISTS [Warehouses].[Racks]
GO
CREATE TABLE [Warehouses].[Racks] (
id smallint IDENTITY (1,1) PRIMARY KEY,
rowId tinyint NULL,
[name] varchar(1) NULL,
capacity decimal(8,2) NULL,
warehouseId tinyint NULL
);
GO
ALTER TABLE [Warehouses].[Racks]
ADD CONSTRAINT FK_RacksRows FOREIGN KEY (rowId) REFERENCES [Warehouses].[Rows] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP TABLE IF EXISTS [Warehouses].[Levels] 
GO
CREATE TABLE [Warehouses].[Levels] (
id smallint IDENTITY (1,1) PRIMARY KEY,
rowId tinyint NULL,
rackId smallint NULL,
[name] varchar(1) NULL,
capacity decimal (8,2) NULL,
warehouseId tinyint NULL
);

ALTER TABLE [Warehouses].[Levels]
ADD CONSTRAINT FK_LevelsRacks FOREIGN KEY (rackId) REFERENCES [Warehouses].[Racks] (id)
ON DELETE CASCADE
ON UPDATE CASCADE

DROP TABLE IF EXISTS [Warehouses].[Places] 
GO
CREATE TABLE [Warehouses].[Places] (
id int IDENTITY (1,1) PRIMARY KEY,
rowId tinyint NULL,
rackId smallint NULL,
levelId smallint NULL,
[name] varchar(1) NULL,
capacity decimal (8,2) NULL,
warehouseId tinyint NULL
);

ALTER TABLE [Warehouses].[Places]
ADD CONSTRAINT FK_PlacesLevels FOREIGN KEY (levelId) REFERENCES [Warehouses].[Levels] (id)
ON DELETE CASCADE
ON UPDATE CASCADE
GO

DROP TABLE IF EXISTS [Warehouses].[Localization]
GO
CREATE TABLE [Warehouses].[Localization] (
id smallint IDENTITY(1,1) PRIMARY KEY,
[name] varchar(30) NULL,
displayName varchar(8) NULL,
warehouseId tinyint NULL,
capacity decimal (8,2) NULL,
placeId int NULL,
);
GO

ALTER TABLE [Warehouses].[Localization]
ADD CONSTRAINT FK_LocalizationPlaces FOREIGN KEY (placeId) REFERENCES [Warehouses].[Places] (id)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

DROP TABLE IF EXISTS [Warehouses].[LocalizationResources] 
GO
CREATE TABLE [Warehouses].[LocalizationResources](
id int IDENTITY (1,1) PRIMARY KEY,
articleCode varchar(10) NULL,
warehouseId tinyint NULL,
quantity int NULL,
articleId int NULL,
localizationId smallint NULL,
[weight] decimal (6,2) NULL
);
GO
ALTER TABLE [Warehouses].[LocalizationResources] 
ADD CONSTRAINT FK_LocalizationResourcesWarehouses FOREIGN KEY (warehouseId) REFERENCES [Warehouses].[Warehouses] (id)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

ALTER TABLE [Warehouses].[LocalizationResources] 
ADD CONSTRAINT FK_LocalizationResourcesArticles FOREIGN KEY (articleId) REFERENCES [Products].[Articles] (id)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

ALTER TABLE [Warehouses].[LocalizationResources] 
ADD CONSTRAINT FK_LocalizationResourcesLocalization FOREIGN KEY (localizationId) REFERENCES [Warehouses].[Localization] (id)
ON UPDATE NO ACTION
ON DELETE NO ACTION
GO


DROP VIEW IF EXISTS View01_ReceiptsHistory
GO
CREATE VIEW View01_ReceiptsHistory AS
SELECT   ROW_NUMBER() OVER (ORDER BY re.creationdate) as UUID,
		 re.operationId,
		 r.operationType,
		 re.articleId,
		 re.userId,
		 re.quantity,
		 re.localizationId,
		 re.warehouseId,
		 r.customerId,
		 re.creationDate
FROM [Operations].[Receipts] as r LEFT JOIN [Operations].[ReceiptElements] as re 
ON r.id = re.operationId
GO

DROP VIEW IF EXISTS View02_ReleasesHistory
GO
CREATE VIEW View02_ReleasesHistory AS
SELECT   ROW_NUMBER() OVER (ORDER BY re.creationdate) as UUID,
		 re.operationId,
		 r.operationType,
		 re.articleId,
		 re.userId,
		 re.quantity,
		 re.localizationId,
		 re.warehouseId,
		 r.customerId,
		 re.creationDate
FROM [Operations].[Releases] as r INNER JOIN [Operations].[ReleaseElements] as re 
ON r.id = re.operationId
GO

DROP VIEW IF EXISTS View03_MovementsHistory
GO
CREATE VIEW View03_MovementsHistory AS
SELECT   ROW_NUMBER() OVER (ORDER BY me.creationdate) as UUID, 
		 me.operationId,
		 m.operationType,
		 me.articleId,
		 me.userId,
		 me.quantity,
		 me.sourceLocalizationId,
		 me.targetLocalizationId,
		 me.targetWarehouseId,
		 me.sourceWarehouseId,
		 m.customerId,
		 me.creationDate
FROM [Operations].[Movements] as m FULL JOIN [Operations].[MovementElements] as me 
ON m.id = me.operationId
GO

DROP VIEW IF EXISTS View04_OperationsAudit
GO
CREATE VIEW View04_OperationsAudit AS
SELECT ROW_NUMBER() OVER (ORDER BY vw1.creationdate) as UUID,
	   vw1.operationId,
	   vw1.operationType,
	   vw1.articleId,
	   vw1.userId,
	   vw1.quantity,
	   vw1.localizationId,
	   vw3.sourceLocalizationId,
	   vw3.targetLocalizationId,
	   vw1.warehouseId,
	   vw3.sourceWarehouseId,
	   vw3.targetWarehouseId,
	   vw1.customerId,
	   vw1.creationDate
FROM [View01_ReceiptsHistory] as vw1 LEFT JOIN [View03_MovementsHistory] as vw3 ON vw1.operationType=vw3.operationType
UNION ALL 
SELECT ROW_NUMBER() OVER (ORDER BY vw2.creationdate) as UUID,
	   vw2.operationId,
	   vw2.operationType,
	   vw2.articleId,
	   vw2.userId,
	   vw2.quantity,
	   vw2.localizationId,
	   vw3.sourceLocalizationId,
	   vw3.targetLocalizationId,
	   vw2.warehouseId,
	   vw3.sourceWarehouseId,
	   vw3.targetWarehouseId,
	   vw2.customerId,
	   vw2.creationDate 
FROM [View02_ReleasesHistory] as vw2 LEFT JOIN View03_MovementsHistory as vw3 ON vw2.operationType = vw3.operationType
UNION ALL
SELECT ROW_NUMBER() OVER (ORDER BY vw3.creationdate) as UUID,
	   vw3.operationId,
	   vw3.operationType,
	   vw3.articleId,
	   vw3.userId,
	   vw3.quantity,
	   vw2.localizationId,
	   vw3.sourceLocalizationId,
	   vw3.targetLocalizationId,
	   vw2.warehouseId,
	   vw3.sourceWarehouseId,
	   vw3.targetWarehouseId,
	   vw3.customerId,
	   vw3.creationDate 
FROM View03_MovementsHistory as vw3 LEFT JOIN View02_ReleasesHistory AS vw2 ON vw3.operationType = vw2.operationType;
GO

INSERT INTO [UAM].[Roles] ([name]) VALUES ('Manager');
INSERT INTO [UAM].[Roles] ([name]) VALUES ('User');
INSERT INTO [UAM].[Roles] ([name]) VALUES ('RO User');
INSERT INTO [UAM].[Users] (username, [password], [name], surname, roleId) VALUES ('admin@bmm-wms.pl','$2a$12$GmauV0YVNq7LCOa0PTYzluGHD6RyXiqvibDi/VPBVCsuROJOCpzga','Default','Account',1);
INSERT INTO [Products].[Units] ([name]) VALUES ('g');
INSERT INTO [Products].[Units] ([name]) VALUES ('dag');
INSERT INTO [Products].[Units] ([name]) VALUES ('kg');
INSERT INTO [Products].[Units] ([name]) VALUES ('t');
INSERT INTO [Products].[Units] ([name]) VALUES ('l');

GO
WAITFOR TIME '05:20'; 
go