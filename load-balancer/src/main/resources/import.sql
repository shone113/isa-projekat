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
