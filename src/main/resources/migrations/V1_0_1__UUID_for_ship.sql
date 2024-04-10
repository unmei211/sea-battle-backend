DROP TABLE IF EXISTS cell;
create table public.cell
(
    id            bigserial
        primary key,
    axis          integer,
    contains_ship boolean,
    is_shot_down  boolean,
    ordinate      integer,
    session_id    bigint
        constraint fkq1r869px7k768anrjj48jr2iu
            references public.session ON DELETE CASCADE,
    user_id       bigint
        constraint fka6kai3ioq9b2kwvics3fiowpd
            references public.users ON DELETE CASCADE
);
ALTER TABLE cell
    ADD COLUMN IF NOT EXISTS ship_id varchar;



