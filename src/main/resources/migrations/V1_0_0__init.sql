create table public.users
(
    id       bigserial
        primary key,
    login    varchar(255)
        constraint uk_ow0gan20590jrb00upg3va2fn
            unique,
    password varchar(255),
    rating   integer
);

alter table public.users
    owner to postgres;

create table public.session
(
    id             bigserial
        primary key,
    date           timestamp(6),
    game_state     varchar(255),
    turn_player_id bigint
        constraint fkxl74vtpsiyi3jq8tpdque7ww
            references public.users,
    user_first     bigint
        constraint fkg5ugvq5rino88ot8pcfl86u1k
            references public.users,
    user_second    bigint
        constraint fkiey40nmbi2qkn7t5p2js8qh9e
            references public.users,
    user_id        bigint
        constraint fkj3ffkfbfbdoh9uon462ow7lhl
            references public.users,
    winner_id      bigint
        constraint fklcstvnqmo0ijjqdrhqh01pxwo
            references public.users
);

alter table public.session
    owner to postgres;

create table public.statistic
(
    id        bigserial
        primary key,
    defeats   bigint,
    victories bigint,
    user_id   bigint
        constraint uk_n3bu9ae0ntpf25pl5svv7u6sd
            unique
        constraint fkoifiete5h1dmj02jk2fwe6d65
            references public.users
);

alter table public.statistic
    owner to postgres;

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
            references public.session,
    user_id       bigint
        constraint fka6kai3ioq9b2kwvics3fiowpd
            references public.users
);

alter table public.cell
    owner to postgres;

