INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.users (email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ('neki@mail.com', 1, 2, null, 'neko', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, 'nekic');
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ( 'aa@aa.aa', 1, 0, null, 'ss', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'aa');
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ( 'admin@gmail.com', 10, 10, null, 'ss', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'aa');
INSERT INTO public.users (email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ('mika@gmail.com', 1, 1, null, 'mika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, 'mikic');
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ( 'zika@gmail.com', 10, 10, null, 'zika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'zikic');
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ( 'pera@gmail.com', 10, 10, null, 'pera', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'peric');

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

INSERT INTO public.posts(description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES ('divan dan za druzenje', 'bunny.jpg', 35, '2023-11-15', 1, 1);

INSERT INTO public.posts(id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES (3, 'zeccc', 'bunny2.webp', 160, '11/10/2024', 1, 1);

INSERT INTO public.posts(id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES (2, 'mika', 'bunny.jpg', 255, '12/25/2024', 1, 2);
INSERT INTO public.posts(id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES ( 4,'zeka zec', 'bunny.jpg', 255, '12/26/2024', 1, 1);
INSERT INTO public.posts(id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES ( 5,'NOVI Zec', 'bunny.jpg', 255, '12/24/2024', 1, 2);

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
