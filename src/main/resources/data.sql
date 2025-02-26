-- treat_available 테이블 데이터 삭제
DELETE FROM treat_available;

-- Vet 더미 데이터
INSERT INTO vet (vet_id, vet_name, status, time) VALUES (10, '김수의', '영업중', CURRENT_TIMESTAMP);
INSERT INTO vet (vet_id, vet_name, status, time) VALUES (11, '이동물', '영업중', CURRENT_TIMESTAMP);
INSERT INTO vet (vet_id, vet_name, status, time) VALUES (12, '박의사', '점심시간', CURRENT_TIMESTAMP);
INSERT INTO vet (vet_id, vet_name, status, time) VALUES (13, '최닥터', '영업중', CURRENT_TIMESTAMP);
INSERT INTO vet (vet_id, vet_name, status, time) VALUES (14, '정수의', '영업종료', CURRENT_TIMESTAMP);
INSERT INTO vet (vet_id, vet_name, status, time) VALUES (15, '한의사', '영업중', CURRENT_TIMESTAMP);

-- TreatAvailable 더미 데이터
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (1, '포유류', '개', '정형외과', 10);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (2, '포유류', '고양이', '내과', 11);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (3, '설치류', '햄스터', '내과', 12);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (4, '어류', '금붕어', '내과', 13);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (5, '파충류', '거북이', '정형외과', 14);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (6, '조류', '앵무새', '안과', 15);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (7, '포유류', '토끼', '안과', 10);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (8, '조류', '닭', '내과', 11);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (9, '파충류', '뱀', '내과', 12);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (10, '설치류', '다람쥐', '정형외과', 13);

-- 추가 TreatAvailable 더미 데이터
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (11, '포유류', '말', '정형외과', 14);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (12, '파충류', '악어', '안과', 15);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (13, '조류', '비둘기', '정형외과', 10);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (14, '어류', '상어', '내과', 11);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (15, '설치류', '비버', '안과', 12);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (16, '포유류', '기린', '내과', 13);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (17, '조류', '독수리', '정형외과', 14);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (18, '파충류', '코브라', '안과', 15);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (19, '어류', '참치', '정형외과', 10);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (20, '설치류', '청설모', '내과', 11);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (21, '포유류', '여우', '안과', 12);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (22, '조류', '펭귄', '내과', 13);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (23, '파충류', '이구아나', '정형외과', 14);
INSERT INTO treat_available (treat_id, species, full_name, avail_treat, vet_id) VALUES (24, '어류', '돌고래', '안과', 15); 