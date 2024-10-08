PGDMP  *    %                |         
   datatarefa    17rc1    17rc1     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    16388 
   datatarefa    DATABASE     �   CREATE DATABASE datatarefa WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE datatarefa;
                     postgres    false            �            1259    16390    tarefas    TABLE     6  CREATE TABLE public.tarefas (
    id integer NOT NULL,
    titulo character varying(255) NOT NULL,
    descricao text,
    responsavel character varying(100),
    prioridade character varying(10),
    deadline character varying(20),
    situacao character varying(50) DEFAULT 'Andamento'::character varying
);
    DROP TABLE public.tarefas;
       public         heap r       postgres    false            �            1259    16389    tarefas_id_seq    SEQUENCE     �   CREATE SEQUENCE public.tarefas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.tarefas_id_seq;
       public               postgres    false    218            �           0    0    tarefas_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.tarefas_id_seq OWNED BY public.tarefas.id;
          public               postgres    false    217            W           2604    16393 
   tarefas id    DEFAULT     h   ALTER TABLE ONLY public.tarefas ALTER COLUMN id SET DEFAULT nextval('public.tarefas_id_seq'::regclass);
 9   ALTER TABLE public.tarefas ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    218    217    218            �          0    16390    tarefas 
   TABLE DATA           e   COPY public.tarefas (id, titulo, descricao, responsavel, prioridade, deadline, situacao) FROM stdin;
    public               postgres    false    218   �       �           0    0    tarefas_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.tarefas_id_seq', 70, true);
          public               postgres    false    217            Z           2606    16397    tarefas tarefas_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.tarefas
    ADD CONSTRAINT tarefas_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.tarefas DROP CONSTRAINT tarefas_pkey;
       public                 postgres    false    218            �   �   x�m�M
�0FדSx�J�u���Xą7�IK�M`l7��G�^�jZE,����{#�ui���`G)ZS!���b,3s%y�gA��H�0׶pLL�|��nXt�E2��2�0��� &��H[���V�v�]��C�֣^۟�����쐝t7zU�9�pg�Yi21��̰x��8X7o~/��m�����{�Yp�     