FROM postgres:latest
RUN localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
ENV LANG en_US.utf8

ADD 1-schema.sql /docker-entrypoint-initdb.d/
ADD 2-data.sql /docker-entrypoint-initdb.d/