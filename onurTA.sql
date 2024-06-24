PGDMP  9    $                |            tourismAgency    15.7    16.3 6    B           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            C           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            D           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            E           1262    25231    tourismAgency    DATABASE     �   CREATE DATABASE "tourismAgency" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE "tourismAgency";
                postgres    false            �            1259    25374    facility    TABLE     o   CREATE TABLE public.facility (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    type text NOT NULL
);
    DROP TABLE public.facility;
       public         heap    postgres    false            �            1259    25373    facility_id_seq    SEQUENCE     �   ALTER TABLE public.facility ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.facility_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            �            1259    25353    hotel    TABLE     �   CREATE TABLE public.hotel (
    id bigint NOT NULL,
    name text NOT NULL,
    city text NOT NULL,
    region text NOT NULL,
    address text NOT NULL,
    email text NOT NULL,
    telephone text NOT NULL,
    star text NOT NULL
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    25352    hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    25361    pension    TABLE     n   CREATE TABLE public.pension (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    type text NOT NULL
);
    DROP TABLE public.pension;
       public         heap    postgres    false            �            1259    25360    pension_id_seq    SEQUENCE     �   ALTER TABLE public.pension ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    25418    price    TABLE       CREATE TABLE public.price (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    pension_id bigint NOT NULL,
    season_id bigint NOT NULL,
    room_id bigint NOT NULL,
    adult_price double precision NOT NULL,
    child_price double precision NOT NULL
);
    DROP TABLE public.price;
       public         heap    postgres    false            �            1259    25417    price_id_seq    SEQUENCE     �   ALTER TABLE public.price ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    227            �            1259    25444    reservation    TABLE     g  CREATE TABLE public.reservation (
    id bigint NOT NULL,
    room_id bigint NOT NULL,
    customer_name text NOT NULL,
    customer_tc text NOT NULL,
    customer_email text NOT NULL,
    total_price double precision NOT NULL,
    start date NOT NULL,
    finish date NOT NULL,
    number_of_adults bigint NOT NULL,
    number_of_children bigint NOT NULL
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    25443    reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    229            �            1259    25400    room    TABLE     �  CREATE TABLE public.room (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    pension_id bigint NOT NULL,
    type text NOT NULL,
    stock bigint NOT NULL,
    bed_count bigint NOT NULL,
    size bigint NOT NULL,
    has_tv boolean NOT NULL,
    has_minibar boolean NOT NULL,
    has_game_console boolean NOT NULL,
    has_projector boolean NOT NULL,
    has_safe boolean NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    25399    room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    225            �            1259    25387    season    TABLE     �   CREATE TABLE public.season (
    id bigint NOT NULL,
    hotel_id bigint NOT NULL,
    type text NOT NULL,
    start date NOT NULL,
    finish date NOT NULL
);
    DROP TABLE public.season;
       public         heap    postgres    false            �            1259    25386    season_id_seq    SEQUENCE     �   ALTER TABLE public.season ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    223            �            1259    25345    user    TABLE     �   CREATE TABLE public."user" (
    id integer NOT NULL,
    name text NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    user_role text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    25344    user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            7          0    25374    facility 
   TABLE DATA           6   COPY public.facility (id, hotel_id, type) FROM stdin;
    public          postgres    false    221   �>       3          0    25353    hotel 
   TABLE DATA           X   COPY public.hotel (id, name, city, region, address, email, telephone, star) FROM stdin;
    public          postgres    false    217   �?       5          0    25361    pension 
   TABLE DATA           5   COPY public.pension (id, hotel_id, type) FROM stdin;
    public          postgres    false    219   �@       =          0    25418    price 
   TABLE DATA           g   COPY public.price (id, hotel_id, pension_id, season_id, room_id, adult_price, child_price) FROM stdin;
    public          postgres    false    227   �@       ?          0    25444    reservation 
   TABLE DATA           �   COPY public.reservation (id, room_id, customer_name, customer_tc, customer_email, total_price, start, finish, number_of_adults, number_of_children) FROM stdin;
    public          postgres    false    229   lA       ;          0    25400    room 
   TABLE DATA           �   COPY public.room (id, hotel_id, pension_id, type, stock, bed_count, size, has_tv, has_minibar, has_game_console, has_projector, has_safe) FROM stdin;
    public          postgres    false    225   B       9          0    25387    season 
   TABLE DATA           C   COPY public.season (id, hotel_id, type, start, finish) FROM stdin;
    public          postgres    false    223   �B       1          0    25345    user 
   TABLE DATA           I   COPY public."user" (id, name, username, password, user_role) FROM stdin;
    public          postgres    false    215   ]C       F           0    0    facility_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.facility_id_seq', 25, true);
          public          postgres    false    220            G           0    0    hotel_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.hotel_id_seq', 5, true);
          public          postgres    false    216            H           0    0    pension_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.pension_id_seq', 10, true);
          public          postgres    false    218            I           0    0    price_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.price_id_seq', 10, true);
          public          postgres    false    226            J           0    0    reservation_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.reservation_id_seq', 6, true);
          public          postgres    false    228            K           0    0    room_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.room_id_seq', 11, true);
          public          postgres    false    224            L           0    0    season_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.season_id_seq', 10, true);
          public          postgres    false    222            M           0    0    user_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.user_id_seq', 2, true);
          public          postgres    false    214            �           2606    25380    facility facility_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.facility
    ADD CONSTRAINT facility_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.facility DROP CONSTRAINT facility_pkey;
       public            postgres    false    221            �           2606    25359    hotel hotel_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    217            �           2606    25367    pension pension_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_pkey;
       public            postgres    false    219            �           2606    25422    price price_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.price
    ADD CONSTRAINT price_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.price DROP CONSTRAINT price_pkey;
       public            postgres    false    227            �           2606    25450    reservation reservation_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    229            �           2606    25406    room room_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    225            �           2606    25393    season season_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
       public            postgres    false    223            �           2606    25351    user user_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    215            �           2606    25368    pension hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(id) ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.pension DROP CONSTRAINT hotel_id;
       public          postgres    false    217    219    3211            �           2606    25381    facility hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.facility
    ADD CONSTRAINT hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(id) ON DELETE CASCADE;
 ;   ALTER TABLE ONLY public.facility DROP CONSTRAINT hotel_id;
       public          postgres    false    3211    217    221            �           2606    25394    season hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.season
    ADD CONSTRAINT hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(id) ON DELETE CASCADE;
 9   ALTER TABLE ONLY public.season DROP CONSTRAINT hotel_id;
       public          postgres    false    217    223    3211            �           2606    25407    room hotel_id    FK CONSTRAINT        ALTER TABLE ONLY public.room
    ADD CONSTRAINT hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(id) ON DELETE CASCADE;
 7   ALTER TABLE ONLY public.room DROP CONSTRAINT hotel_id;
       public          postgres    false    225    217    3211            �           2606    25423    price hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.price
    ADD CONSTRAINT hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(id) ON DELETE CASCADE;
 8   ALTER TABLE ONLY public.price DROP CONSTRAINT hotel_id;
       public          postgres    false    227    3211    217            �           2606    25412    room pension_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT pension_id FOREIGN KEY (pension_id) REFERENCES public.pension(id) ON DELETE CASCADE;
 9   ALTER TABLE ONLY public.room DROP CONSTRAINT pension_id;
       public          postgres    false    3213    219    225            �           2606    25428    price pension_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.price
    ADD CONSTRAINT pension_id FOREIGN KEY (pension_id) REFERENCES public.pension(id) ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.price DROP CONSTRAINT pension_id;
       public          postgres    false    3213    227    219            �           2606    25433    price room_id    FK CONSTRAINT     }   ALTER TABLE ONLY public.price
    ADD CONSTRAINT room_id FOREIGN KEY (room_id) REFERENCES public.room(id) ON DELETE CASCADE;
 7   ALTER TABLE ONLY public.price DROP CONSTRAINT room_id;
       public          postgres    false    227    3219    225            �           2606    25451    reservation room_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT room_id FOREIGN KEY (room_id) REFERENCES public.room(id) ON DELETE CASCADE;
 =   ALTER TABLE ONLY public.reservation DROP CONSTRAINT room_id;
       public          postgres    false    229    3219    225            �           2606    25438    price season_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.price
    ADD CONSTRAINT season_id FOREIGN KEY (season_id) REFERENCES public.season(id) ON DELETE CASCADE;
 9   ALTER TABLE ONLY public.price DROP CONSTRAINT season_id;
       public          postgres    false    223    227    3217            7   �   x�]�K� D��0UL �e9)jD����E�"��
�1���b1���L�$2!O��?�]���e�U8&�|z���?�LХ���w���s�L��-��Ά�J0��,�e�0��VIQ&�M�m�99*��L�q]6]]�$�A�P���eځl�և̊J)�eo��k#�ҩ��H���4�c�      3   �   x�u�AJ�@E�է��$�;�BD�n�N���Hw"t.�-�ҭ�x�$��p�UU������Ҵ�-$����mywq,��l�t���zn<�7�U��<��͟�Ex��Q'�t��lkX���a��ԏZi�t��6Ay�4�A�T����1�g�+S�^����?(��"�v]���ggSm����;+W��n�_4X�9j�}���ȚG�yL� ��qe���<{�      5   :   x�3�4�t����s�	uqu�2�4��		r�G��4EUg�i�*`h�i�*���� w��      =   j   x�5�Q�0B��0m�����ch����B�n��,H$�AMӅZÆ��zH�^d�4(+Br�)�(�ka��N���ɏ�Hr������fԱ-?���҃:      ?   �   x�m�A
�0�����d&I��]�n�D,4
Z������%m�a�<�0��`�����0��G��JA��Fu�5J��I`��[�SU�n�ݦ9kE�O��{U�1+w��}�B��5�k�{�}Q~��/�B6�cHc�k�ó(q�e:�D��]C      ;   �   x�U�K�0D��à�I��"*T���Rw���q
ȕ��x<~����x���T�6�!2�_Q���u.���#_%�aW���Z�`��=N�l�9q]�q)�Y��HI��`�9kXŖt[ơL��2�i���&K*�'�?X�*���u4��"��:�;|K�      9   �   x�u�=�0��پ����00Сu�����RB�`YoY��;���M&;Q�}L�|�ij���j5�`!� "pB>	�����qF����	�P�eC��{N��?��tRk��
�!��=�y�?T      1   ?   x�3���+-�t�/�,��442�tt����2�H-*��K��tvK������p��qqq լ�     