#!/usr/bin/env python
# coding=utf-8

import os

from subprocess import check_output

os.environ['DOCKER_HOST'] = 'unix://' + os.environ.get('XDG_RUNTIME_DIR') + '/docker.sock'


def isApplicationRunning():
    output = check_output(["docker", "ps", "-a"])
    if output.find('ariielm/tasks-api') != -1:
        return True
    else:
        return False


if isApplicationRunning():
    os.system("docker rm -f backend")

os.system("docker pull ariielm/tasks-api")
os.system("docker run --name backend -d -p 8080 --link nginx --net arielmolinacom_default ariielm/tasks-api")
