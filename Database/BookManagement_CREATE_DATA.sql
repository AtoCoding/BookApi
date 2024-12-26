create database BookManagement;
go

use BookManagement;
go

create table Book (
	BookId int primary key identity,
	BookName nvarchar(150) NOT NULL,
	Author nvarchar(50) NOT NULL,
	DateCreated date NOT NULL,
	Quantity int NOT NULL,
);

create table Category (
	CategoryId int primary key identity,
	CategoryName nvarchar(100) NOT NULL,
);

create table BookCategory (
	BookId int,
	CategoryId int,
	constraint PK_BOOKCATEGORY primary key(BookId, CategoryId),
	constraint FK_BOOKID foreign key(BookId) references Book(BookId),
	constraint FK_CATEGORYID foreign key(CategoryId) references Category(CategoryId),
);

go 

insert into Book(BookName, Author, DateCreated, Quantity) values 
(N'Tô Bình Yên Vẽ Hạnh Phúc', N'Kulzsc', '2022', 15),
(N'Đường Xưa Mây Trắng - Theo Gót Chân Bụt', N'Thích Nhất Hạnh', '2024', 2),
(N'Lan Tỏa Ảnh Hưởng Thời Kỹ Thuật Số - Chiến Thuật Tâm Lý Để Thu Hút Và Thuyết Phục Khách Hàng', N'Nathalie Nahai', '2018', 51);

insert into Category(CategoryName) values 
(N'Đời sống'),
(N'Sức khỏe'),
(N'Lối sống'),
(N'Trinh thám'),
(N'Khoa học viễn tưởng'),
(N'Lịch sử');

insert into BookCategory(BookId, CategoryId) values
(1, 1),
(1, 2),
(3, 2),
(3, 5),
(3, 6);