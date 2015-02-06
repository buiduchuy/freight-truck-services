USE [FTS]
GO
/****** Object:  Table [dbo].[Vehicle]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Vehicle](
	[VehicleID] [int] IDENTITY(1,1) NOT NULL,
	[Number] [nvarchar](50) NOT NULL,
	[Type] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Vehicle] PRIMARY KEY CLUSTERED 
(
	[VehicleID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Vehicle] ON
INSERT [dbo].[Vehicle] ([VehicleID], [Number], [Type]) VALUES (1, N'60B5-12345', N'Xe tải trên 10 tấn')
INSERT [dbo].[Vehicle] ([VehicleID], [Number], [Type]) VALUES (2, N'69A1-45678', N'Xe tải trên 8 tấn')
SET IDENTITY_INSERT [dbo].[Vehicle] OFF
/****** Object:  Table [dbo].[Role]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[RoleID] [int] NOT NULL,
	[RoleName] [nvarchar](50) NULL,
 CONSTRAINT [PK_tblRole] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Role] ([RoleID], [RoleName]) VALUES (1, N'Owner')
INSERT [dbo].[Role] ([RoleID], [RoleName]) VALUES (2, N'Driver')
INSERT [dbo].[Role] ([RoleID], [RoleName]) VALUES (3, N'Staff')
INSERT [dbo].[Role] ([RoleID], [RoleName]) VALUES (4, N'Admin')
/****** Object:  Table [dbo].[PaymentType]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PaymentType](
	[PaymentTypeID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Discription] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Payment] PRIMARY KEY CLUSTERED 
(
	[PaymentTypeID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[PaymentType] ON
INSERT [dbo].[PaymentType] ([PaymentTypeID], [Name], [Discription]) VALUES (1, N'Ngân Lượng', N'Thanh toán trực tuyến qua Ngân Lượng')
SET IDENTITY_INSERT [dbo].[PaymentType] OFF
/****** Object:  Table [dbo].[DealStatus]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DealStatus](
	[DealStatusID] [int] IDENTITY(1,1) NOT NULL,
	[DealStatusName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_DealStatus] PRIMARY KEY CLUSTERED 
(
	[DealStatusID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[DealStatus] ON
INSERT [dbo].[DealStatus] ([DealStatusID], [DealStatusName]) VALUES (1, N'Đang chờ trả lời từ tài xế')
SET IDENTITY_INSERT [dbo].[DealStatus] OFF
/****** Object:  Table [dbo].[Notification]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Notification](
	[NotificationID] [int] NOT NULL,
	[EmailFrom] [nvarchar](50) NOT NULL,
	[EmailTo] [datetime] NOT NULL,
	[Message] [nvarchar](50) NOT NULL,
	[Status] [nchar](10) NULL,
	[CreateTime] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Notification] PRIMARY KEY CLUSTERED 
(
	[NotificationID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GoodsCategory]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GoodsCategory](
	[GoodsCategoryID] [int] NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_GoodsCategory] PRIMARY KEY CLUSTERED 
(
	[GoodsCategoryID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[GoodsCategory] ([GoodsCategoryID], [Name]) VALUES (1, N'Hàng thực phẩm')
/****** Object:  Table [dbo].[OrderStatus]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderStatus](
	[OrderStatusID] [int] IDENTITY(1,1) NOT NULL,
	[OrderStatusName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Status] PRIMARY KEY CLUSTERED 
(
	[OrderStatusID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[OrderStatus] ON
INSERT [dbo].[OrderStatus] ([OrderStatusID], [OrderStatusName]) VALUES (1, N'Đang chờ xác nhận giao hàng')
SET IDENTITY_INSERT [dbo].[OrderStatus] OFF
/****** Object:  Table [dbo].[Order]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[OrderID] [int] IDENTITY(1,1) NOT NULL,
	[Price] [float] NOT NULL,
	[StaffDeliveryStatus] [bit] NOT NULL,
	[DriverDeliveryStatus] [bit] NOT NULL,
	[OwnerDeliveryStatus] [bit] NOT NULL,
	[OrderStatusID] [int] NOT NULL,
	[PaymentTypeID] [int] NOT NULL,
	[CreateTime] [datetime] NOT NULL,
 CONSTRAINT [PK_Order_1] PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Order] ON
INSERT [dbo].[Order] ([OrderID], [Price], [StaffDeliveryStatus], [DriverDeliveryStatus], [OwnerDeliveryStatus], [OrderStatusID], [PaymentTypeID], [CreateTime]) VALUES (3, 14000, 0, 1, 0, 1, 1, CAST(0x0000A43600000000 AS DateTime))
SET IDENTITY_INSERT [dbo].[Order] OFF
/****** Object:  Table [dbo].[Account]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](10) NOT NULL,
	[RoleID] [int] NOT NULL,
 CONSTRAINT [PK_Account_1] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Account] ON
INSERT [dbo].[Account] ([UserID], [Email], [Password], [RoleID]) VALUES (1, N'driver', N'driver', 1)
INSERT [dbo].[Account] ([UserID], [Email], [Password], [RoleID]) VALUES (2, N'owner', N'owner', 2)
INSERT [dbo].[Account] ([UserID], [Email], [Password], [RoleID]) VALUES (4, N'staff', N'staff', 3)
INSERT [dbo].[Account] ([UserID], [Email], [Password], [RoleID]) VALUES (5, N'admin', N'admin', 4)
SET IDENTITY_INSERT [dbo].[Account] OFF
/****** Object:  Table [dbo].[Owner]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Owner](
	[OwnerID] [int] IDENTITY(1,1) NOT NULL,
	[Email] [nvarchar](50) NULL,
	[FirstName] [nvarchar](50) NULL,
	[LastName] [nvarchar](50) NULL,
	[Sex] [int] NULL,
	[Phone] [nvarchar](50) NULL,
	[Address] [nvarchar](50) NULL,
	[IsActive] [bit] NULL,
	[CreateBy] [nvarchar](50) NULL,
	[CreateTime] [datetime] NULL,
	[UpdateBy] [nvarchar](50) NULL,
	[UpdateTime] [datetime] NULL,
 CONSTRAINT [PK_Owner] PRIMARY KEY CLUSTERED 
(
	[OwnerID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Owner] ON
INSERT [dbo].[Owner] ([OwnerID], [Email], [FirstName], [LastName], [Sex], [Phone], [Address], [IsActive], [CreateBy], [CreateTime], [UpdateBy], [UpdateTime]) VALUES (1, N'owner', N'Bui', N'Duc Huy', 1, N'09876532', N'Ho Chi Minh', 1, N'owner', CAST(0x0000A46E00000000 AS DateTime), N'owner', CAST(0x0000A46E00000000 AS DateTime))
SET IDENTITY_INSERT [dbo].[Owner] OFF
/****** Object:  Table [dbo].[Employee]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[EmployeeID] [int] IDENTITY(1,1) NOT NULL,
	[Email] [nvarchar](50) NULL,
	[FirstName] [nvarchar](50) NULL,
	[LastName] [nvarchar](50) NULL,
	[Phone] [nvarchar](50) NULL,
	[Sex] [int] NULL,
	[Image] [nvarchar](50) NULL,
 CONSTRAINT [PK_Staff] PRIMARY KEY CLUSTERED 
(
	[EmployeeID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Driver]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Driver](
	[DriverID] [int] IDENTITY(1,1) NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[FirstName] [nvarchar](50) NULL,
	[LastName] [nvarchar](50) NULL,
	[Sex] [int] NULL,
	[Phone] [nvarchar](50) NOT NULL,
	[IsActive] [bit] NOT NULL,
	[CreateBy] [nvarchar](50) NOT NULL,
	[CreateTime] [datetime] NOT NULL,
	[UpdateBy] [nvarchar](50) NOT NULL,
	[UpdateTime] [datetime] NOT NULL,
	[VehicleID] [int] NULL,
	[Age] [int] NULL,
	[Image] [nvarchar](50) NULL,
	[Point] [int] NULL,
 CONSTRAINT [PK_Driver] PRIMARY KEY CLUSTERED 
(
	[DriverID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Driver] ON
INSERT [dbo].[Driver] ([DriverID], [Email], [FirstName], [LastName], [Sex], [Phone], [IsActive], [CreateBy], [CreateTime], [UpdateBy], [UpdateTime], [VehicleID], [Age], [Image], [Point]) VALUES (1, N'driver', N'Thien', N'Noob', 1, N'06532322', 1, N'driver', CAST(0x0000A43000000000 AS DateTime), N'driver', CAST(0x0000A43000000000 AS DateTime), NULL, NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Driver] OFF
/****** Object:  Table [dbo].[Goods]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Goods](
	[GoodsID] [int] IDENTITY(1,1) NOT NULL,
	[Weight] [int] NOT NULL,
	[Price] [float] NOT NULL,
	[PickupTime] [datetime] NOT NULL,
	[PickupAddress] [nvarchar](50) NOT NULL,
	[DeliveryTime] [datetime] NOT NULL,
	[DeliveryAddress] [nvarchar](50) NOT NULL,
	[PickupMarkerLongtitude] [decimal](9, 6) NULL,
	[PickupMarkerLatidute] [decimal](9, 6) NULL,
	[DeliveryMarkerLongtitude] [decimal](9, 6) NULL,
	[DeliveryMarkerLatidute] [decimal](9, 6) NULL,
	[Notes] [nvarchar](250) NULL,
	[CreateTime] [datetime] NOT NULL,
	[Active] [int] NOT NULL,
	[OwnerID] [int] NOT NULL,
	[GoodsCategoryID] [int] NOT NULL,
 CONSTRAINT [PK_Goods_1] PRIMARY KEY CLUSTERED 
(
	[GoodsID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Goods] ON
INSERT [dbo].[Goods] ([GoodsID], [Weight], [Price], [PickupTime], [PickupAddress], [DeliveryTime], [DeliveryAddress], [PickupMarkerLongtitude], [PickupMarkerLatidute], [DeliveryMarkerLongtitude], [DeliveryMarkerLatidute], [Notes], [CreateTime], [Active], [OwnerID], [GoodsCategoryID]) VALUES (11, 3, 10000, CAST(0x0000A42B011826C0 AS DateTime), N'Hue', CAST(0x0000A48E00000000 AS DateTime), N'Ho Chi Minh', NULL, NULL, NULL, NULL, NULL, CAST(0x0000A48E00000000 AS DateTime), 1, 1, 1)
INSERT [dbo].[Goods] ([GoodsID], [Weight], [Price], [PickupTime], [PickupAddress], [DeliveryTime], [DeliveryAddress], [PickupMarkerLongtitude], [PickupMarkerLatidute], [DeliveryMarkerLongtitude], [DeliveryMarkerLatidute], [Notes], [CreateTime], [Active], [OwnerID], [GoodsCategoryID]) VALUES (17, 3, 321, CAST(0x0000A47100000000 AS DateTime), N'asdsad', CAST(0x0000A47100000000 AS DateTime), N'asdas', CAST(4.123000 AS Decimal(9, 6)), CAST(3.123100 AS Decimal(9, 6)), CAST(2.123123 AS Decimal(9, 6)), CAST(1.234240 AS Decimal(9, 6)), NULL, CAST(0x0000A47100000000 AS DateTime), 1, 1, 1)
INSERT [dbo].[Goods] ([GoodsID], [Weight], [Price], [PickupTime], [PickupAddress], [DeliveryTime], [DeliveryAddress], [PickupMarkerLongtitude], [PickupMarkerLatidute], [DeliveryMarkerLongtitude], [DeliveryMarkerLatidute], [Notes], [CreateTime], [Active], [OwnerID], [GoodsCategoryID]) VALUES (18, 3, 321, CAST(0x0000A47100000000 AS DateTime), N'asdsad', CAST(0x0000A47100000000 AS DateTime), N'asdas', CAST(4.123000 AS Decimal(9, 6)), CAST(3.123100 AS Decimal(9, 6)), CAST(2.123123 AS Decimal(9, 6)), CAST(1.234240 AS Decimal(9, 6)), NULL, CAST(0x0000A47100000000 AS DateTime), 1, 1, 1)
INSERT [dbo].[Goods] ([GoodsID], [Weight], [Price], [PickupTime], [PickupAddress], [DeliveryTime], [DeliveryAddress], [PickupMarkerLongtitude], [PickupMarkerLatidute], [DeliveryMarkerLongtitude], [DeliveryMarkerLatidute], [Notes], [CreateTime], [Active], [OwnerID], [GoodsCategoryID]) VALUES (19, 3, 321, CAST(0x0000A47100000000 AS DateTime), N'asdsad', CAST(0x0000A47100000000 AS DateTime), N'asdas', CAST(4.123000 AS Decimal(9, 6)), CAST(3.123100 AS Decimal(9, 6)), CAST(2.123123 AS Decimal(9, 6)), CAST(1.234240 AS Decimal(9, 6)), NULL, CAST(0x0000A47100000000 AS DateTime), 1, 1, 1)
INSERT [dbo].[Goods] ([GoodsID], [Weight], [Price], [PickupTime], [PickupAddress], [DeliveryTime], [DeliveryAddress], [PickupMarkerLongtitude], [PickupMarkerLatidute], [DeliveryMarkerLongtitude], [DeliveryMarkerLatidute], [Notes], [CreateTime], [Active], [OwnerID], [GoodsCategoryID]) VALUES (20, 3, 321, CAST(0x0000A47100000000 AS DateTime), N'asdsad', CAST(0x0000A47100000000 AS DateTime), N'asdas', CAST(4.123000 AS Decimal(9, 6)), CAST(3.123100 AS Decimal(9, 6)), CAST(2.123123 AS Decimal(9, 6)), CAST(1.234240 AS Decimal(9, 6)), NULL, CAST(0x0000A47100000000 AS DateTime), 1, 1, 1)
INSERT [dbo].[Goods] ([GoodsID], [Weight], [Price], [PickupTime], [PickupAddress], [DeliveryTime], [DeliveryAddress], [PickupMarkerLongtitude], [PickupMarkerLatidute], [DeliveryMarkerLongtitude], [DeliveryMarkerLatidute], [Notes], [CreateTime], [Active], [OwnerID], [GoodsCategoryID]) VALUES (21, 3, 321, CAST(0x0000A47100000000 AS DateTime), N'asdsad', CAST(0x0000A47100000000 AS DateTime), N'asdas', CAST(4.123000 AS Decimal(9, 6)), CAST(3.123100 AS Decimal(9, 6)), CAST(2.123123 AS Decimal(9, 6)), CAST(1.234240 AS Decimal(9, 6)), NULL, CAST(0x0000A47100000000 AS DateTime), 1, 1, 1)
SET IDENTITY_INSERT [dbo].[Goods] OFF
/****** Object:  Table [dbo].[Route]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Route](
	[RouteID] [int] IDENTITY(1,1) NOT NULL,
	[StartingAddress] [nvarchar](50) NOT NULL,
	[Marker1] [nvarchar](50) NULL,
	[Marker2] [nvarchar](50) NULL,
	[DestinationAddress] [nvarchar](50) NOT NULL,
	[StartTime] [datetime] NOT NULL,
	[FinishTime] [datetime] NOT NULL,
	[Notes] [nvarchar](250) NULL,
	[CreateTime] [datetime] NOT NULL,
	[Active] [int] NOT NULL,
	[VehicleID] [int] NOT NULL,
	[DriverID] [int] NOT NULL,
 CONSTRAINT [PK_Road] PRIMARY KEY CLUSTERED 
(
	[RouteID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Route] ON
INSERT [dbo].[Route] ([RouteID], [StartingAddress], [Marker1], [Marker2], [DestinationAddress], [StartTime], [FinishTime], [Notes], [CreateTime], [Active], [VehicleID], [DriverID]) VALUES (3, N'1234', NULL, NULL, N'1233', CAST(0x0000A43500000000 AS DateTime), CAST(0x0000A45100000000 AS DateTime), NULL, CAST(0x0000A45100000000 AS DateTime), 1, 1, 1)
SET IDENTITY_INSERT [dbo].[Route] OFF
/****** Object:  Table [dbo].[RouteGoodsCategory]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RouteGoodsCategory](
	[RouteID] [int] NOT NULL,
	[GoodsCategoryID] [int] NOT NULL,
 CONSTRAINT [PK_RoadGoodsCategory] PRIMARY KEY CLUSTERED 
(
	[RouteID] ASC,
	[GoodsCategoryID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Deal]    Script Date: 02/06/2015 22:14:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Deal](
	[DealID] [int] IDENTITY(1,1) NOT NULL,
	[Price] [float] NOT NULL,
	[Notes] [nvarchar](250) NULL,
	[CreateTime] [nchar](10) NOT NULL,
	[OrderID] [int] NULL,
	[Sender] [nvarchar](50) NOT NULL,
	[RouteID] [int] NOT NULL,
	[GoodsID] [int] NOT NULL,
	[DealStatusID] [int] NOT NULL,
	[IsActive] [bit] NOT NULL,
 CONSTRAINT [PK_Deal] PRIMARY KEY CLUSTERED 
(
	[DealID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Deal] ON
INSERT [dbo].[Deal] ([DealID], [Price], [Notes], [CreateTime], [OrderID], [Sender], [RouteID], [GoodsID], [DealStatusID], [IsActive]) VALUES (9, 14000, N'duyệt', N'04/02/2015', NULL, N'driver', 3, 11, 1, 1)
SET IDENTITY_INSERT [dbo].[Deal] OFF
/****** Object:  ForeignKey [FK_Account_Role]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_Role] FOREIGN KEY([RoleID])
REFERENCES [dbo].[Role] ([RoleID])
GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FK_Account_Role]
GO
/****** Object:  ForeignKey [FK_Deal_DealStatus]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Deal]  WITH CHECK ADD  CONSTRAINT [FK_Deal_DealStatus] FOREIGN KEY([DealStatusID])
REFERENCES [dbo].[DealStatus] ([DealStatusID])
GO
ALTER TABLE [dbo].[Deal] CHECK CONSTRAINT [FK_Deal_DealStatus]
GO
/****** Object:  ForeignKey [FK_Deal_Goods]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Deal]  WITH CHECK ADD  CONSTRAINT [FK_Deal_Goods] FOREIGN KEY([GoodsID])
REFERENCES [dbo].[Goods] ([GoodsID])
GO
ALTER TABLE [dbo].[Deal] CHECK CONSTRAINT [FK_Deal_Goods]
GO
/****** Object:  ForeignKey [FK_Deal_Order]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Deal]  WITH CHECK ADD  CONSTRAINT [FK_Deal_Order] FOREIGN KEY([OrderID])
REFERENCES [dbo].[Order] ([OrderID])
GO
ALTER TABLE [dbo].[Deal] CHECK CONSTRAINT [FK_Deal_Order]
GO
/****** Object:  ForeignKey [FK_Deal_Route1]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Deal]  WITH CHECK ADD  CONSTRAINT [FK_Deal_Route1] FOREIGN KEY([RouteID])
REFERENCES [dbo].[Route] ([RouteID])
GO
ALTER TABLE [dbo].[Deal] CHECK CONSTRAINT [FK_Deal_Route1]
GO
/****** Object:  ForeignKey [FK_Driver_Account]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Driver]  WITH CHECK ADD  CONSTRAINT [FK_Driver_Account] FOREIGN KEY([Email])
REFERENCES [dbo].[Account] ([Email])
GO
ALTER TABLE [dbo].[Driver] CHECK CONSTRAINT [FK_Driver_Account]
GO
/****** Object:  ForeignKey [FK_Driver_Vehicle]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Driver]  WITH CHECK ADD  CONSTRAINT [FK_Driver_Vehicle] FOREIGN KEY([VehicleID])
REFERENCES [dbo].[Vehicle] ([VehicleID])
GO
ALTER TABLE [dbo].[Driver] CHECK CONSTRAINT [FK_Driver_Vehicle]
GO
/****** Object:  ForeignKey [FK_Employee_Account]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Account] FOREIGN KEY([Email])
REFERENCES [dbo].[Account] ([Email])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Account]
GO
/****** Object:  ForeignKey [FK_Goods_GoodsCategory]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Goods]  WITH CHECK ADD  CONSTRAINT [FK_Goods_GoodsCategory] FOREIGN KEY([GoodsCategoryID])
REFERENCES [dbo].[GoodsCategory] ([GoodsCategoryID])
GO
ALTER TABLE [dbo].[Goods] CHECK CONSTRAINT [FK_Goods_GoodsCategory]
GO
/****** Object:  ForeignKey [FK_Goods_Owner]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Goods]  WITH CHECK ADD  CONSTRAINT [FK_Goods_Owner] FOREIGN KEY([OwnerID])
REFERENCES [dbo].[Owner] ([OwnerID])
GO
ALTER TABLE [dbo].[Goods] CHECK CONSTRAINT [FK_Goods_Owner]
GO
/****** Object:  ForeignKey [FK_Order_OrderStatus]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_OrderStatus] FOREIGN KEY([OrderStatusID])
REFERENCES [dbo].[OrderStatus] ([OrderStatusID])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_OrderStatus]
GO
/****** Object:  ForeignKey [FK_Order_PaymentType]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_PaymentType] FOREIGN KEY([PaymentTypeID])
REFERENCES [dbo].[PaymentType] ([PaymentTypeID])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_PaymentType]
GO
/****** Object:  ForeignKey [FK_Owner_Account]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Owner]  WITH CHECK ADD  CONSTRAINT [FK_Owner_Account] FOREIGN KEY([Email])
REFERENCES [dbo].[Account] ([Email])
GO
ALTER TABLE [dbo].[Owner] CHECK CONSTRAINT [FK_Owner_Account]
GO
/****** Object:  ForeignKey [FK_Route_Driver]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Route]  WITH CHECK ADD  CONSTRAINT [FK_Route_Driver] FOREIGN KEY([DriverID])
REFERENCES [dbo].[Driver] ([DriverID])
GO
ALTER TABLE [dbo].[Route] CHECK CONSTRAINT [FK_Route_Driver]
GO
/****** Object:  ForeignKey [FK_Route_Vehicle]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[Route]  WITH CHECK ADD  CONSTRAINT [FK_Route_Vehicle] FOREIGN KEY([VehicleID])
REFERENCES [dbo].[Vehicle] ([VehicleID])
GO
ALTER TABLE [dbo].[Route] CHECK CONSTRAINT [FK_Route_Vehicle]
GO
/****** Object:  ForeignKey [FK_RoadGoodsCategory_GoodsCategory]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[RouteGoodsCategory]  WITH CHECK ADD  CONSTRAINT [FK_RoadGoodsCategory_GoodsCategory] FOREIGN KEY([GoodsCategoryID])
REFERENCES [dbo].[GoodsCategory] ([GoodsCategoryID])
GO
ALTER TABLE [dbo].[RouteGoodsCategory] CHECK CONSTRAINT [FK_RoadGoodsCategory_GoodsCategory]
GO
/****** Object:  ForeignKey [FK_RoadGoodsCategory_Road]    Script Date: 02/06/2015 22:14:45 ******/
ALTER TABLE [dbo].[RouteGoodsCategory]  WITH CHECK ADD  CONSTRAINT [FK_RoadGoodsCategory_Road] FOREIGN KEY([RouteID])
REFERENCES [dbo].[Route] ([RouteID])
GO
ALTER TABLE [dbo].[RouteGoodsCategory] CHECK CONSTRAINT [FK_RoadGoodsCategory_Road]
GO
