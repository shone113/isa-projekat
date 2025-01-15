INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.users (email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ('neki@mail.com', 1, 2, null, 'neko', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, 'nekic', 20.4489, 44.7866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'aa@aa.aa', 1, 0, null, 'ss', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'aa', 21.4489, 44.9866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'admin@gmail.com', 10, 10, null, 'ss', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'aa', 20.8489, 44.8866);
INSERT INTO public.users (email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ('mika@gmail.com', 1, 1, null, 'mika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, 'mikic', 19.4489, 45.7866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'zika@gmail.com', 10, 10, null, 'zika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'zikic', 20.4489, 44.9866);
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname, longitude, latitude) VALUES ( 'pera@gmail.com', 10, 10, null, 'pera', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'peric', 20.1489, 44.7866);

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (4, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (5, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (6, 2);


INSERT INTO public.profile(user_id) VALUES ( 1);
INSERT INTO public.profile(user_id) VALUES ( 3);
INSERT INTO public.profile(user_id) VALUES ( 4);
INSERT INTO public.profile(user_id) VALUES ( 5);
INSERT INTO public.profile(user_id) VALUES ( 6);

INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (1, 2);
INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (1, 3);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (2, 1);
INSERT INTO public.profile_follower(profile_id, follower_profile_id) VALUES (1, 2);

INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ('divan dan za druzenje', 'bunny.jpg', 35, '2023-11-15', 1, 20.4489, 44.7866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'zeccc', 'bunny2.webp', 160, '2024-12-12', 1,  20.3489, 44.7966);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'mika', 'bunny.jpg', 255, '2024-12-25',  2, 20.7489, 44.9866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'zeka zec', 'bunny.jpg', 255, '2024-12-26',  1, 20.9489, 45.7866);
INSERT INTO public.posts(description, image, likes_count, publishing_date, profile_id, longitude, latitude)VALUES ( 'NOVI Zec', 'bunny.jpg', 255, '2024-12-24',  2, 21.0489, 45.7866);

INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('wow', '2024-12-26', 1, 1);

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
