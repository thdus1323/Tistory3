INSERT INTO user_tb  (user_id, user_name, user_password, comfirm_user_password,user_email, created_at)
values (1,'ssar','1234','1234', 'ssar@nate.com', now());

-- 카테고리 테이블
INSERT INTO category_tb  (category_name, user_id) values('취미', 1);
INSERT INTO category_tb  (category_name, user_id) values('음식', 1);
INSERT INTO category_tb  (category_name, user_id) values('여행', 1);