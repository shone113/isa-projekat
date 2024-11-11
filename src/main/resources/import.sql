INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.users (email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ('neki@mail.com', 1, 1, null, 'neko', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, 'nekic');
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ( 'aa@aa.aa', 10, 10, null, 'ss', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'aa');
INSERT INTO public.users(email, followers_count, following_count, activation_token, name, password, posts_count, surname) VALUES ( 'admin@gmail.com', 10, 10, null, 'ss', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 12, 'aa');

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 2);

INSERT INTO public.profile(user_id) VALUES ( 1);
INSERT INTO public.profile(user_id) VALUES ( 2);

INSERT INTO public.profile_following(profile_id, following_profile_id) VALUES (1, 2);

INSERT INTO public.posts(description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES ('divan dan za druzenje', 'bunny.jpg', 35, '2024-11-15', 1, 1);

INSERT INTO public.posts(id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES (3, 'zeccc', 'bunny2.webp', 160, '12/10/2024', 1, 1);

INSERT INTO public.posts(id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)VALUES (2, 'mika', 'bunny.jpg', 255, '12/10/2026', 1, 2);

INSERT INTO public.comments(content, creation_date, creator_id, post_id)VALUES ('wow', '2024-11-15', 1, 1);

