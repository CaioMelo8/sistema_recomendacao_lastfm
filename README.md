# sistema_recomendacao_lastfm
Sistema criado para a disciplina de Tópicos avançados em Bancos de Dados, pela da Universidade Federal do Ceará.
Os dados utilizados pelo sistema foram dados públicos do aplicativo Last.fm, obtidos [neste link](http://www.dtic.upf.edu/~ocelma/MusicRecommendationDataset/index.html).

Os procedimentos necessários para reproduzir o projeto são explicitados a seguir.

# Procedimentos

- O banco de dados foi criado.

```SQL
CREATE DATABASE sistema_recomendacao_2;
```

- Os registros do número de execuções de um artista por um usuário foram armazenados na tabela artist_play_count;

```SQL
DROP TABLE artist_play_count;

CREATE TABLE artist_play_count (id serial, user_sha1 varchar(40), art_mbid varchar(40), art_name varchar, play_count int, PRIMARY KEY(id));
COPY artist_play_count(user_sha1, art_mbid, art_name, play_count) FROM **path_of_the_TSV_file** DELIMITER E'\t';
-- (COPY 17559529 linhas copiadas)
```
  
- Os registros contendo os dados do usuário foram armazenados na tabela user_profile;

```SQL
DROP TABLE user_profile;

CREATE TABLE user_profile (id serial, user_sha1 varchar(40), gender char(1), age char(5), country varchar(50), signup date);

COPY user_profile(user_sha1, gender, age, country, signup) FROM **path_of_the_TSV_file** DELIMITER E'\t';
-- (359347 linhas copiadas)

UPDATE user_profile SET age = '0' WHERE age = '';	

ALTER TABLE user_profile ALTER COLUMN age TYPE integer USING age::integer;
```

- Todos os registros na tabela artist_play_count com menos de 200 execuções foram removidos.
```SQL
SELECT count(*) FROM artist_play_count WHERE play_count < 200;

DELETE FROM artist_play_count WHERE play_count < 200;	
```

- Uma tabela number_artist_entries foi criada, contendo a quantidade de vezes que um artista aparece na tabela art_play_count, ou seja, quantos usuários o escutaram ao menos 1 vez.

```SQL
DROP TABLE number_artist_entries;

CREATE TABLE number_artist_entries (id serial, art_name varchar, number_entries int, PRIMARY KEY(id));

INSERT INTO number_artist_entries(art_name, number_entries) SELECT art_name, count(art_name) from artist_play_count GROUP BY art_name;
```

- Uma tabela number_user_entries foi criada, contendo a quantidade de artistas distintos que um usuário escutou e que está na tabela artist_play_count.

```SQL
DROP TABLE number_user_entries;

CREATE TABLE number_user_entries(id serial, user_sha1 varchar(40), number_entries int, PRIMARY KEY (id));

INSERT INTO number_user_entries(user_sha1, number_entries) SELECT user_sha1, count(user_sha1) FROM artist_play_count GROUP BY user_sha1;
```

- Todos os usuários que escutaram menos de 61 artistas distintos foram removidos a tabela number_user_entries.
	
```SQL	
SELECT count(*) FROM number_user_entries WHERE number_entries < 61;

DELETE FROM number_user_entries WHERE number_entries < 61;
```

- Todos os usuários que não escutaram ao menos 61 artistas distintos também foram removidos da tabela user_profile.

```SQL
SELECT count(*) FROM user_profile WHERE user_sha1 NOT in (SELECT user_sha1 FROM number_user_entries);

DELETE FROM user_profile WHERE user_sha1 NOT in (SELECT user_sha1 FROM number_user_entries);
```

- Todos os artistas com menos de 200 aparições foram removidos da tabela number_artist_entries.
	
```SQL	
SELECT count(*) FROM number_artist_entries WHERE number_entries < 200;

DELETE FROM number_artist_entries WHERE number_entries < 200;
```

- Todos os artistas que possuem menos de 200 aparições foram excluídos da tabela artist_play_count.
	
```SQL	
SELECT count(*) FROM artist_play_count WHERE art_name NOT in (SELECT art_name FROM number_artist_entries);

DELETE FROM artist_play_count WHERE art_name NOT in (SELECT art_name FROM number_artist_entries);
```

-  Todos os registros da tabela artist_play_count que não estão relacionados a nenhum usuário que ainda está na tabela number_user_entries foram removidos.

```SQL
SELECT count(*) FROM artist_play_count WHERE user_sha1 NOT in (SELECT user_sha1 FROM number_user_entries);

DELETE FROM artist_play_count WHERE user_sha1 NOT in (SELECT user_sha1 FROM number_user_entries);
```

- Todos os registros na tabela number_artist_entries ue não estão mais relacionados à tabela artist_play_count.
	
```SQL	
SELECT count(*) FROM number_artist_entries WHERE art_name not in (SELECT DISTINCT art_name FROM artist_play_count);

DELETE FROM number_artist_entries WHERE art_name not in (SELECT DISTINCT art_name FROM artist_play_count);
```

- Todos os usuários da tabela user_profile que não estão relacionados a nenhum registro da tabela artist_play_count foram removidos.
	
```SQL
SELECT count(*) FROM number_user_entries WHERE user_sha1 NOT in (SELECT DISTINCT user_sha1 FROM artist_play_count);

DELETE FROM number_user_entries WHERE user_sha1 NOT in (SELECT DISTINCT user_sha1 FROM artist_play_count);
```

- A tabela similaridade foi criada, e serve para armazenar o resultado da similaridade entre os usuários.

```
DROP TABLE similaridade;

CREATE TABLE similaridade(id serial, user1 varchar(40), user2 varchar(40), similaridade double precision, PRIMARY KEY(id));
```

- A tabela artist_play_count_norm foi criada para armazenar os registros da tabela artist_play_count, porém com os valores normalizados. A normalização foi feita através da divisão de todos os valores de play_count pelo maior valor de play_count na tabela. 

```SQL
DROP TABLE artist_play_count_norm;

CREATE TABLE artist_play_count_norm (id serial, user_sha1 varchar(40), art_mbid varchar(40), art_name varchar, play_count double precision, primary key(id));

INSERT INTO artist_play_count_norm(user_sha1, art_mbid, art_name, play_count) SELECT user_sha1, art_mbid, art_name, CAST(play_count AS double precision) / (SELECT max(play_count) FROM artist_play_count) FROM artist_play_count;
```

- Para todos os usuários, a tabela artist_play_count foi preenchida com os artistas que o usuário não escutou nenhuma vez. Os registros para esses usuários foram inseridos tendo como play_count o valor 0. (10631062 registros no total)

- O algoritmo de cálculo de similaridade foi executado, a fim de calcular a similaridade entre todos os usuários. os resultados foram salvos a tabela similaridade.

- A segunda parte do algoritmo calculou os artistas a serem recomendados para cada usuário, baseados nos demais usuários que possuem uma similaridade de, ao menos, 40%.
  Para cada dupla de usuários, foram recomendados até 5 artistas.

- A tabela recommendacoes foi criada, para armazenar as recommendacoes de artista para cada usuario.

```SQL
DROP TABLE recomendacao;

CREATE TABLE recomendacao(id serial, user_sha1 varchar(40), art_name varchar, primary key(id));
```

- A tabela number_artist_recommended_entries foi criada, para armazenar o número de vezes que um artista é recomendado.

```SQL
DROP TABLE number_artist_recommended_entries;

CREATE TABLE number_artist_recommended_entries(id serial, art_name varchar, number_entries int, primary key(id));

INSERT INTO number_artist_recommended_entries(art_name, number_entries) SELECT art_name, count(*) FROM recomendacao GROUP BY art_name;
```
