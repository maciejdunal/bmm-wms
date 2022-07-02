DROP DATABASE IF EXISTS [WMSDatabase]
GO
CREATE DATABASE [WMSDatabase]
GO
ALTER DATABASE [WMSDatabase] SET AUTO_CLOSE OFF;
GO
USE [WMSDatabase]
GO
DROP SCHEMA IF EXISTS [UAM]
    GO
CREATE SCHEMA [UAM]
    GO

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
    receiptId int NULL,
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
CREATE TABLE [Operations].[Movementslements] (
                                                 id int IDENTITY(1,1) PRIMARY KEY,
    movementId int NULL,
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
    releaseId int NULL,
    operationType varchar(12) NULL,
    articleId int NULL,
    userId smallint NULL,
    quantity smallint NULL,
    [weight] decimal (6,2) NULL,
    localizationId smallint NULL,
    creationDate datetime NULL,
    warehouseId tinyint NULL
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
    documentNumber varchar(255) NULL,
    creationDate datetime NULL,
    modificationDate datetime NULL,
    warehouseId tinyint NULL,
    customerId int  NULL,
    [description] varchar(50) NULL,
    userId smallint NULL
    );
GO
ALTER TABLE [Operations].[ReceiptElements]
    ADD CONSTRAINT FK_ReceiptsElemntsReceipts FOREIGN KEY (receiptId) REFERENCES [Operations].[Receipts] (id)
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
    documentNumber varchar(255) NULL,
    creationDate datetime NULL,
    modificationDate datetime NULL,
    customerId int NULL,
    [description] varchar(50) NULL,
    userId smallint NULL
    );
GO

ALTER TABLE [Operations].[MovementElements]
    ADD CONSTRAINT FK_MovementElementsMovements FOREIGN KEY (movementId) REFERENCES [Operations].[Movements] (id)
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
    documentNumber varchar(255) NULL,
    creationDate datetime NULL,
    modificationDate datetime NULL,
    warehouseId tinyint NULL,
    customerId int NULL,
    [description] varchar(50) NULL,
    userId smallint NULL
    );
GO

ALTER TABLE [Operations].[ReleaseElements]
    ADD CONSTRAINT FK_ReleaseElementsReleases FOREIGN KEY (movementId) REFERENCES [Operations].[Releases] (id)
    ON DELETE CASCADE
ON UPDATE CASCADE
                     GO

ALTER TABLE [Operations].[Releases]
    ADD CONSTRAINT FK_ReleasesUsers FOREIGN KEY (userId) REFERENCES [UAM].[Users] (id)
    ON DELETE CASCADE
ON UPDATE CASCADE
                     GO

DROP TABLE IF EXISTS [Operations].[OperationsAudit]
    GO
CREATE TABLE [Operations].[OperationsAudit] (
                                                id int IDENTITY(1,1) PRIMARY KEY,
    operationId int NULL,
    operationType varchar(12) NULL,
    articleId int NULL,
    userId smallint NULL,
    quantity smallint NULL CHECK (quantity >= 1),
    sourceLocalizationId smallint NULL,
    targetLocalizationId smallint NULL,
    sourceWarehouseId tinyint NULL,
    targetWarehouseId tinyint NULL,
    customerId int NULL,
    [date] datetime NULL,
    );
GO

ALTER TABLE [Operations].[OperationsAudit]
    ADD CONSTRAINT FK_OperationsAuditReceipts FOREIGN KEY (operationId) REFERENCES [Operations].[Receipts] (id)
    ON DELETE NO ACTION
ON UPDATE NO ACTION
                     GO

ALTER TABLE [Operations].[OperationsAudit]
    ADD CONSTRAINT FK_OperationsAuditMovements FOREIGN KEY (operationId) REFERENCES [Operations].[Movements] (id)
    ON DELETE NO ACTION
ON UPDATE NO ACTION
                     GO

ALTER TABLE [Operations].[OperationsAudit]
    ADD CONSTRAINT FK_OperationsAuditReleases FOREIGN KEY (operationId) REFERENCES [Operations].[Releases] (id)
    ON DELETE NO ACTION
ON UPDATE NO ACTION
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

ALTER TABLE [Operations].[OperationsAudit]
    ADD CONSTRAINT FK_OperationsAuditCustomers FOREIGN KEY (customerId) REFERENCES [Counterparties].[Customers] (id)
    ON DELETE CASCADE
ON UPDATE CASCADE
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

ALTER TABLE [Operations].[OperationsAudit]
    ADD CONSTRAINT FK_OperationsAuditWarehouses FOREIGN KEY (sourceWarehouseId) REFERENCES [Warehouses].[Warehouses] (id)
    ON UPDATE CASCADE
       ON DELETE CASCADE

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


INSERT INTO [UAM].[Roles] ([name]) VALUES ('Manager');
INSERT INTO [UAM].[Roles] ([name]) VALUES ('User');
INSERT INTO [UAM].[Roles] ([name]) VALUES ('RO User');
INSERT INTO [UAM].[Users] (username, [password], [name], surname, roleId) VALUES ('admin@bmm-wms.pl','Admin123!','Default','Account',1);
INSERT INTO [Products].[Units] ([name]) VALUES ('g');
INSERT INTO [Products].[Units] ([name]) VALUES ('dag');
INSERT INTO [Products].[Units] ([name]) VALUES ('kg');
INSERT INTO [Products].[Units] ([name]) VALUES ('t');
INSERT INTO [Products].[Units] ([name]) VALUES ('l');
GO