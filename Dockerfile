FROM ubuntu:latest
LABEL authors="Viktor"

ENTRYPOINT ["top", "-b"]