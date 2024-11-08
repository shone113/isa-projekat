INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.user_tab(
    id, email, followers_count, following_count, name, password, posts_count, surname, isActivated)
VALUES (1, 'mika@gmail.com', 200, 250, 'mika', '123', 6, 'mikic',false);

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO public.posts(
    description, image, likes_count, publishing_date, publishing_location_id)
VALUES ('divan dan za druzenje', null, 35, '2024-11-15', 1);

INSERT INTO public.comments(
    content, creation_date, creator_id, post_id)
VALUES ('wow', '2024-11-15', 1, 1);

