INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
ALTER TABLE users ALTER COLUMN version SET DEFAULT 0;
INSERT INTO public.users (email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ('neki@mail.com', 0, 2, null, 'neko', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 3, 'nekic', 20.4489, 44.7866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'ana@gmail.com', 1, 1, null, 'ana', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 2, 'nikolic', 21.4489, 44.9866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'janko@gmail.com', 2, 3, null, 'janko', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, 'markovic', 20.8489, 44.8866);
INSERT INTO public.users (email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ('mika@gmail.com', 1, 0, null, 'mika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, 'mikic', 19.4489, 45.7866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'zika@gmail.com', 1, 0, null, 'zika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 2, 'zikic', 21.4489, 44.9866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'pera@gmail.com', 1, 0, null, 'pera', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 0, 'peric', 20.1489, 45.7866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'admin@gmail.com', 0, 0, null, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 0, 'admin', 20.1489, 44.7866);

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (4, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (5, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (6, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (7, 2);

INSERT INTO public.profile(user_id) VALUES ( 1);
INSERT INTO public.profile(user_id) VALUES ( 2);
INSERT INTO public.profile(user_id) VALUES ( 3);
INSERT INTO public.profile(user_id) VALUES ( 4);
INSERT INTO public.profile(user_id) VALUES ( 5);
INSERT INTO public.profile(user_id) VALUES ( 6);
INSERT INTO public.profile(user_id) VALUES ( 7);

INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (1, 2);
INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (1, 3);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (2, 1);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (3, 1);
INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (2, 3);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (3, 2);
INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (3, 4);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (4, 3);
INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (3, 5);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (5, 3);
INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (3, 6);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (6, 3);

INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ('divan dan za druzenje', 'bunny.jpg', 5, '2024-11-15', 1, 20.4489, 44.7866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'zeccc', 'bunny2.webp', 6, '2024-12-12', 1,  20.3489, 44.7966);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'mika', 'zec1.jpg', 5, '2025-3-25',  2, 20.7489, 44.9866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'zeka zec', 'zec2.jpg', 5, '2025-4-26',  1, 20.9489, 45.7866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'NOVI Zec', 'zec5.jpg', 4, '2025-4-24',  2, 21.0489, 45.7866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'janov zec', 'zec6.jpeg', 3, '2025-7-20',  3, 22.0489, 45.7866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'moj zec', 'zec4.webp', 3, '2025-7-19',  4, 22.3489, 45.9866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'dusko, pera i zeka', 'zec3.jpg', 2, '2025-7-20',  5, 22.0489, 45.7866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'zecica', 'zec7.png', 2, '2025-7-18',  5, 21.1489, 44.7866);


INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('wow', '2024-11-16', 1, 1);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('nice', '2024-11-26', 2, 1);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('lijep zeka', '2024-12-27', 3, 1);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('cute :P', '2024-12-16', 1, 2);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('prelijepoo', '2024-12-20', 3, 2);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('kjutt', '2024-12-26', 5, 2);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('slatkoo', '2025-1-6', 2, 2);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('bas ti je sladak zec', '2025-3-26', 1, 3);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('bas je saldak zec', '2025-7-17', 5, 5);
INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('wowww', '2025-7-18', 6, 5);


INSERT INTO public.chats(id, chat_type, title, admin_profile_id) VALUES (1, 'GROUP', 'fudbal', 1);
INSERT INTO public.chats(id, chat_type, title, admin_profile_id) VALUES (2, 'GROUP', 'dispomanija', 1);
SELECT setval('chats_id_seq', (SELECT MAX(id) FROM chats));

INSERT INTO public.chat_members(chat_id, profile_id) VALUES (1, 1);
INSERT INTO public.chat_members(chat_id, profile_id) VALUES (1, 2);
INSERT INTO public.chat_members(chat_id, profile_id) VALUES (2, 1);
INSERT INTO public.chat_members(chat_id, profile_id) VALUES (2, 2);
INSERT INTO public.chat_members(chat_id, profile_id) VALUES (2, 3);

INSERT INTO public.messages(id, content, creation_date, chat_id, creator_id) VALUES (1, 'hej ti', '2024-12-26', 1, 1);
INSERT INTO public.messages(id, content, creation_date, chat_id, creator_id) VALUES (2, 'oo', '2024-12-26', 1, 1);
INSERT INTO public.messages(id, content, creation_date, chat_id, creator_id) VALUES (3, 'djesi', '2024-12-26', 2, 1);
SELECT setval('messages_id_seq', (SELECT MAX(id) FROM messages));
