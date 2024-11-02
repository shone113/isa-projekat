INSERT INTO public.administrator(
    id, email, location_id, name, password, surname, username, type)
VALUES (1, 'nenad123@gmail.com', 1, 'nenad', '123', 'dubovac', 'shone', 2);


INSERT INTO public.app_user(id, email, location_id, name, password, surname, username, authenticated, following_count, posts_count, type)
VALUES (2, 'simke@gmail.com', 2, 'sima', '123', 'simic', 'simke', true, 400, 25, 1);

INSERT INTO public.app_user(id, email, location_id, name, password, surname, username, authenticated, following_count, posts_count, type)
VALUES (3, 'pera@gmail.com', 3, 'pera', '123', 'peric', 'ricpe', true, 100, 100, 0);