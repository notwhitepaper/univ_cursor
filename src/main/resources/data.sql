INSERT INTO users (id, login_id, password_hash, name, role, major, max_credits)
VALUES (1, 'student1', '$2a$10$DowSD2d4n0SgGJE0E7E5E.8ij4RS0LlwM651c01qmPvvrLpzjAUu', '홍길동', 'STUDENT', '컴퓨터공학과', 20),
       (2, 'admin1', '$2a$10$DowSD2d4n0SgGJE0E7E5E.8ij4RS0LlwM651c01qmPvvrLpzjAUu', '관리자', '교무처', 20);

INSERT INTO notices (id, title, content, created_at)
VALUES (1, '2025학년도 1학기 개강 안내', '개강은 3월 첫째 주입니다.', DATEADD('DAY', -10, CURRENT_TIMESTAMP)),
       (2, '도서관 휴관 안내', '시설 점검으로 2일간 휴관합니다.', DATEADD('DAY', -5, CURRENT_TIMESTAMP)),
       (3, '등록금 납부 안내', '등록금 납부 기간을 확인하세요.', DATEADD('DAY', -1, CURRENT_TIMESTAMP));

INSERT INTO academic_events (id, title, date)
VALUES (1, '수강신청 시작', DATEADD('DAY', 1, CURRENT_DATE)),
       (2, '중간고사 기간', DATEADD('DAY', 30, CURRENT_DATE)),
       (3, '기말고사 기간', DATEADD('DAY', 90, CURRENT_DATE));

INSERT INTO classrooms (id, name, building)
VALUES (1, '101', '공학관'),
       (2, '202', '인문관'),
       (3, '303', '자연관');

INSERT INTO courses (id, code, title, dept, credit, capacity, enrolled_count)
VALUES (1, 'CSE101', '자료구조', '컴퓨터공학과', 3, 50, 0),
       (2, 'CSE201', '알고리즘', '컴퓨터공학과', 3, 40, 0),
       (3, 'MAT101', '미적분학', '수학과', 3, 60, 0),
       (4, 'PHY201', '일반물리', '물리학과', 2, 45, 0),
       (5, 'ENG101', '영어회화', '어문학부', 2, 35, 0),
       (6, 'BUS301', '경영학개론', '경영학과', 3, 55, 0);

INSERT INTO course_times (id, course_id, day_of_week, start_min, end_min, classroom_id)
VALUES (1, 1, 1, 540, 600, 1),
       (2, 1, 3, 540, 600, 1),
       (3, 2, 2, 600, 660, 1),
       (4, 2, 4, 600, 660, 1),
       (5, 3, 1, 660, 720, 2),
       (6, 3, 3, 660, 720, 2),
       (7, 4, 5, 540, 600, 3),
       (8, 5, 2, 480, 540, 2),
       (9, 5, 4, 480, 540, 2),
       (10, 6, 1, 720, 780, 3),
       (11, 6, 3, 720, 780, 3);

INSERT INTO registration_windows (id, start_at, end_at, active, rollover_executed)
VALUES (1, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', 30, CURRENT_TIMESTAMP), true, false);
