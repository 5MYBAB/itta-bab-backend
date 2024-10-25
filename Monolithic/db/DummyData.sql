-- Bootcamp
INSERT INTO bootcamp (boot_name, latitude, longitude)
VALUES ('CodeCamp', 37.5665, 126.9780),
       ('DevAcademy', 35.1796, 129.0756),
       ('WebHub', 37.7749, 127.9780),
       ('SpringAcademy', 35.8254, 127.1480),
       ('FullStackLab', 37.541, 126.986),
       ('BackendMasters', 37.401, 127.404);

-- Course
INSERT INTO course (boot_id, start_date, end_date, class_name, season_num)
VALUES (1, '2024-01-01', '2024-06-30', 'Java Backend', 1),
       (2, '2024-02-01', '2024-07-31', 'Fullstack Web', 2),
       (3, '2024-03-01', '2024-08-31', 'Data Science', 3),
       (4, '2024-04-01', '2024-09-30', 'Machine Learning', 4),
       (5, '2024-05-01', '2024-10-31', 'Frontend Basics', 5),
       (6, '2024-06-01', '2024-11-30', 'Spring Boot Mastery', 6);

-- User Info
INSERT INTO user_info (username, login_id, pwd, email, phone, birth, course_id, user_status, user_role, sign_up_date)
VALUES ('Alice', 'alice01', 'pwd12345', 'alice@example.com', '01012345678', '1990-01-01', 1, 'ACTIVE', 'USER',
        '2024-03-01'),
       ('Bob', 'bob02', 'pwd12345', 'bob@example.com', '01087654321', '1992-02-02', 2, 'ACTIVE', 'USER', '2024-03-05'),
       ('Charlie', 'charlie03', 'pwd12345', 'charlie@example.com', '01011223344', '1988-03-03', 3, 'ACTIVE', 'USER',
        '2024-04-01'),
       ('David', 'david04', 'pwd12345', 'david@example.com', '01055667788', '1985-04-04', 4, 'REPORTED', 'USER',
        '2024-05-01'),
       ('Eve', 'eve05', 'pwd12345', 'eve@example.com', '01099887766', '1995-05-05', 5, 'WITHDRAWN', 'USER',
        '2024-06-01'),
       ('Frank', 'frank06', 'pwd12345', 'frank@example.com', '01055443322', '1989-06-06', 6, 'ACTIVE', 'USER',
        '2024-07-01');

-- Friend
INSERT INTO friend (from_user_id, to_user_id, friend_status)
VALUES (1, 2, 'ACCEPTED'),
       (3, 4, 'PENDING'),
       (2, 5, 'REJECTED'),
       (6, 1, 'ACCEPTED'),
       (4, 5, 'PENDING'),
       (2, 3, 'ACCEPTED');

-- Inquiry
INSERT INTO inquiry (inquiry_content, create_date, inquiry_user_id)
VALUES ('How can I reset my password?', '2024-08-01 10:00:00', 1),
       ('Can I change my username?', '2024-09-01 11:00:00', 2),
       ('What is the refund policy?', '2024-09-05 12:00:00', 3),
       ('How do I contact support?', '2024-09-10 13:00:00', 4),
       ('Where can I find tutorials?', '2024-09-15 14:00:00', 5),
       ('How to delete my account?', '2024-09-20 15:00:00', 6);

-- Store
INSERT INTO store (store_name, store_location, store_open_time, store_end_time, store_week, store_info, store_status,
                   user_id)
VALUES ('PizzaPlace', 'Seoul', '09:00:00', '22:00:00', 'Mon-Sun', 'Best pizza in town', 'OPEN', 1),
       ('BurgerKing', 'Busan', '10:00:00', '20:00:00', 'Mon-Fri', 'Tasty burgers', 'CLOSED', 2),
       ('CoffeeCorner', 'Incheon', '08:00:00', '18:00:00', 'Mon-Sat', 'Cozy coffee spot', 'OPEN', 3),
       ('SushiWorld', 'Seoul', '11:00:00', '23:00:00', 'Tue-Sun', 'Fresh sushi and sashimi', 'OPEN', 4),
       ('SteakHouse', 'Daegu', '12:00:00', '22:00:00', 'Wed-Sun', 'Best steaks in town', 'OPEN', 5),
       ('PastaPalace', 'Gwangju', '10:00:00', '21:00:00', 'Mon-Fri', 'Delicious pasta dishes', 'CLOSED', 6);

-- Favorite
INSERT INTO favorite (user_id, store_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6);

-- Menu Category
INSERT INTO menu_category (menu_category_name)
VALUES ('Pizza'),
       ('Burger'),
       ('Coffee'),
       ('Sushi'),
       ('Steak'),
       ('Pasta');

-- Menu
INSERT INTO menu (store_id, menu_name, menu_price, menu_category_id)
VALUES (1, 'Margherita Pizza', 15000, 1),
       (2, 'Cheese Burger', 8000, 2),
       (3, 'Cappuccino', 5000, 3),
       (4, 'Salmon Sushi', 20000, 4),
       (5, 'Grilled Steak', 25000, 5),
       (6, 'Carbonara Pasta', 12000, 6);

-- Review
INSERT INTO review (store_id, user_id, review_content, rating, create_date, is_blinded)
VALUES (1, 1, 'Great pizza!', 5, '2024-08-10 14:00:00', FALSE),
       (2, 2, 'Nice burgers!', 4, '2024-08-11 15:00:00', FALSE),
       (3, 3, 'Loved the coffee!', 5, '2024-08-12 16:00:00', FALSE),
       (4, 4, 'Fresh sushi, will visit again!', 5, '2024-08-13 17:00:00', FALSE),
       (5, 5, 'Steak was a bit overcooked.', 3, '2024-08-14 18:00:00', FALSE),
       (6, 6, 'Pasta was delicious!', 4, '2024-08-15 19:00:00', FALSE);

-- Order Menu
INSERT INTO order_menu (review_id, menu_id, user_id)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 4),
    (5, 5, 5);

-- Post
INSERT INTO post (user_id, post_title, post_content, create_date, is_blinded)
VALUES (1, 'Welcome to Pizza Lovers', 'We love pizza!', '2024-08-15 12:00:00', FALSE),
       (2, 'Burger Fans Unite', 'Lets discuss burgers!', '2024-08-16 13:00:00', FALSE),
       (3, 'Morning Coffee Thoughts', 'Cant start the day without coffee.', '2024-08-17 14:00:00', FALSE),
       (4, 'Sushi Paradise', 'Best places to get sushi?', '2024-08-18 15:00:00', FALSE),
       (5, 'Steakhouse Recommendations', 'Where to get the best steak?', '2024-08-19 16:00:00', FALSE),
       (6, 'Pasta Lovers', 'Share your favorite pasta dishes!', '2024-08-20 17:00:00', FALSE);

-- Post Comment
INSERT INTO post_comment (post_id, user_id, comment_content, create_date, is_blinded)
VALUES (1, 2, 'I love this place!', '2024-08-15 12:30:00', FALSE),
       (1, 3, 'Cant wait to try it!', '2024-08-15 12:45:00', FALSE),
       (2, 1, 'Burgers are life.', '2024-08-16 13:30:00', FALSE),
       (3, 4, 'Coffee is a must!', '2024-08-17 14:30:00', FALSE),
       (4, 5, 'SushiWorld is the best!', '2024-08-18 15:30:00', FALSE),
       (5, 6, 'I recommend SteakHouse.', '2024-08-19 16:30:00', FALSE);

-- Schedule Info
INSERT INTO schedule_info (schedule_date, schedule_title, schedule_content, user_id)
VALUES ('2024-08-20', 'Pizza Party', 'Join us for a pizza party!', 1),
       ('2024-09-01', 'Burger Bash', 'Lets have a burger feast!', 2),
       ('2024-09-10', 'Coffee Meetup', 'Discuss coffee brewing techniques.', 3),
       ('2024-09-15', 'Sushi Night', 'Enjoy sushi with friends.', 4),
       ('2024-09-20', 'Steak Tasting', 'Sample different cuts of steak.', 5),
       ('2024-09-25', 'Pasta Workshop', 'Learn to make pasta from scratch.', 6);

-- Group Category
INSERT INTO group_category (group_category_name)
VALUES ('Food Lovers'),
       ('Pizza Enthusiasts'),
       ('Burger Fans'),
       ('Coffee Addicts'),
       ('Sushi Aficionados'),
       ('Steak Connoisseurs');

-- Group Info
INSERT INTO group_info (group_category_id, user_id, group_title, user_counting, group_status, create_date, end_date,
                        group_post, is_blinded)
VALUES (1, 1, 'Join the Pizza Lovers', 10, TRUE, '2024-08-01 10:00:00', '2024-09-01 18:00:00', 'Pizza is love!', FALSE),
       (2, 2, 'Burger Fan Club', 8, TRUE, '2024-08-05 11:00:00', '2024-09-05 19:00:00', 'Burgers forever!', FALSE),
       (3, 3, 'Coffee Enthusiasts', 15, TRUE, '2024-08-10 12:00:00', '2024-09-10 20:00:00', 'Coffee is life.', FALSE),
       (4, 4, 'Sushi Nights', 12, TRUE, '2024-08-15 13:00:00', '2024-09-15 21:00:00', 'Sushi for everyone!', FALSE),
       (5, 5, 'Steak Lovers', 20, TRUE, '2024-08-20 14:00:00', '2024-09-20 22:00:00', 'All about steak!', FALSE),
       (6, 6, 'Pasta Workshops', 18, TRUE, '2024-08-25 15:00:00', '2024-09-25 23:00:00', 'Lets cook pasta!', FALSE);

-- Group Comment
INSERT INTO group_comment (group_id, user_id, comment_content, create_date, is_blinded)
VALUES (1, 2, 'Pizza for life!', '2024-08-01 10:30:00', FALSE),
       (2, 3, 'Burgers are the best!', '2024-08-05 11:30:00', FALSE),
       (3, 4, 'Coffee keeps me going.', '2024-08-10 12:30:00', FALSE),
       (4, 5, 'Sushi is amazing!', '2024-08-15 13:30:00', FALSE),
       (5, 6, 'Steak is love!', '2024-08-20 14:30:00', FALSE),
       (6, 1, 'Pasta makes my day.', '2024-08-25 15:30:00', FALSE);

-- Group User
INSERT INTO group_user (user_id, group_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6);

-- Report
INSERT INTO report (report_title, report_content, target, target_id, user_id, create_date, is_resolved)
VALUES ('Spam Post', 'This post is spam.', 'POST', 1, 1, '2024-08-16 09:00:00', FALSE),
       ('Offensive Comment', 'This comment is offensive.', 'COMMENT', 2, 2, '2024-08-17 10:30:00', FALSE),
       ('Inappropriate Review', 'The review contains inappropriate content.', 'REVIEW', 3, 3, '2024-08-18 12:00:00',
        FALSE),
       ('Fake Profile', 'This user is using a fake profile.', 'COMMENT', 4, 4, '2024-08-19 13:15:00', FALSE),
       ('Spam Message', 'This message is spam.', 'COMMENT', 5, 5, '2024-08-20 14:30:00', FALSE),
       ('Inappropriate Group Post', 'Group post violates guidelines.', 'POST', 6, 6, '2024-08-21 15:45:00', FALSE);


-- Good (Like)
INSERT INTO good (user_id, target, target_id)
VALUES (1, 'POST', 1),
       (2, 'REVIEW', 2),
       (3, 'COMMENT', 3),
       (4, 'GROUP', 4),
       (5, 'POST', 5),
       (6, 'REVIEW', 6);

-- Picture
INSERT INTO picture (picture_url, target, target_id)
VALUES ('http://example.com/image1.jpg', 'POST', 1),
       ('http://example.com/image2.jpg', 'REVIEW', 2),
       ('http://example.com/image3.jpg', 'COMMENT', 3),
       ('http://example.com/image4.jpg', 'GROUP', 4),
       ('http://example.com/image5.jpg', 'POST', 5),
       ('http://example.com/image6.jpg', 'POST', 6);


-- Notification
INSERT INTO notification (content, target, target_id, user_id, is_read, create_date)
VALUES ('Your post has a new comment!', 'POST', 1, 1, FALSE, '2024-08-15 12:05:00'),
       ('You have a new friend request.', 'USER', 2, 2, FALSE, '2024-08-16 13:10:00'),
       ('Your review was liked!', 'REVIEW', 3, 3, FALSE, '2024-08-17 14:15:00'),
       ('Group chat created successfully.', 'GROUP', 4, 4, FALSE, '2024-08-18 15:20:00'),
       ('Your comment received a reply.', 'COMMENT', 5, 5, FALSE, '2024-08-19 16:25:00'),
       ('New event added to your schedule.', 'USER', 6, 6, FALSE, '2024-08-20 17:30:00');





