INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.user_tab(
    id, email, followers_count, following_count, name, password, posts_count, surname, isActivated)
VALUES (1, 'mika@gmail.com', 200, 250, 'mika', '123', 6, 'mikic',true);

INSERT INTO public.user_tab(
    id, email, followers_count, following_count, name, password, posts_count, surname, isActivated)
VALUES (2, 'john@doe.com', 400, 500, 'john', '123', 16, 'doe', true);

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1);

INSERT INTO public.profile(
    id, user_id)
VALUES (1, 1);

INSERT INTO public.profile(
    id, user_id)
VALUES (2, 2);

INSERT INTO public.profile_following(
    profile_id, following_profile_id)
VALUES (1, 2);

INSERT INTO public.posts(
    description, image, likes_count, publishing_date, publishing_location_id, profile_id)
VALUES ('divan dan za druzenje', 'bunny.jpg', 35, '2024-11-15', 1, 1);

INSERT INTO public.posts(
    id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)
VALUES (3, 'zeccc', 'bunny2.webp', 160, '12/10/2024', 1, 1);

INSERT INTO public.posts(
    id, description, image, likes_count, publishing_date, publishing_location_id, profile_id)
VALUES (2, 'mika', 'bunny.jpg', 255, '12/10/2026', 1, 2);

INSERT INTO public.comments(
    content, creation_date, creator_id, post_id)
VALUES ('wow', '2024-11-15', 1, 1);

