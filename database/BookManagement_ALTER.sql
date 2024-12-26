create table Account (
	Username varchar(50) primary key,
	[Password] varchar(50) NOT NULL,
	Fullname nvarchar(100) NOT NULL,
	Age int NOT NULL,
	IsAvailable bit NOT NULL,
);

create table AccountSession (
	SessionId UNIQUEIDENTIFIER DEFAULT NEWID() primary key,
	ExpiredAt datetime NOT NULL,
	Username varchar(50),

	constraint FK_USERNAME foreign key(Username) references Account(Username)
);

insert into Account(Username, [Password], Fullname, Age, IsAvailable) values 
('admin', '123', N'Issei Nguyen', 18, 1);

alter table Book add Username varchar(50);
go
alter table Book add foreign key(Username) references Account(Username);
update Book set Username = 'admin';
alter table Book alter column Username varchar(50) NOT NULL;

alter table Category add Username varchar(50);
go
alter table Category add foreign key(Username) references Account(Username);
update Category set Username = 'admin';
alter table Category alter column Username varchar(50) NOT NULL;