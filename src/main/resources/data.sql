INSERT INTO permission_group (id,group_name) VALUES
	 (1,'admin');
INSERT INTO permission (id,permission_level,user_email,group_id) VALUES
	 (1,'VIEW','view@example.com',1),
	 (2,'EDIT','edit@example.com',1),
	 (3,'VIEW','admin@example.com',1),
	 (4,'EDIT','admin@example.com',1);