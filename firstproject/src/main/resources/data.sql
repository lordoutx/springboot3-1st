-- Article
insert into article (title,content) values('가가가가','1111');
insert into article (title,content) values('나나나나','2222');
insert into article (title,content) values('다다다다','3333');
insert into article (title,content) values('당신의 인생 영화는?','댓글 Go');
insert into article (title,content) values('당신의 소울푸드는?','댓글 Go Go');
insert into article (title,content) values('당신의 취미는?','댓글 Go Go Go');
-- Comment
insert into comment (article_id,nickname,body) values(4,'Park','굿 윌 헌팅');
insert into comment (article_id,nickname,body) values(4,'Kim','아이 엠 샘');
insert into comment (article_id,nickname,body) values(4,'Choi','쇼생크 탈출');
insert into comment (article_id,nickname,body) values(5,'Park','치킨');
insert into comment (article_id,nickname,body) values(5,'Kim','샤브샤브');
insert into comment (article_id,nickname,body) values(5,'Choi','초밥');
insert into comment (article_id,nickname,body) values(6,'Park','조깅');
insert into comment (article_id,nickname,body) values(6,'Kim','유튜브 시청');
insert into comment (article_id,nickname,body) values(6,'Choi','독서');
-- Member
insert into member (email,password) values('lordout@gmail.com','1111');
insert into member (email,password) values('lordout@naver.com','2222');
insert into member (email,password) values('doit@dot.com','3333');
-- Coffee
insert into coffee (name,price) values('아메리카노','4500');
insert into coffee (name,price) values('라테','5000');
insert into coffee (name,price) values('카페 모카','5500');

