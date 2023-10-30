#
# The DevKit container is the environment in which TeamCity is executed. It is as an environment for the testing ...
# ... of potential tooling extension and security testing purposes.
#

ARG teamCityAgentImage

FROM ${teamCityAgentImage}

USER root

RUN rm -rf /opt/buildagent
USER buildagent

CMD ["sleep", "infinity"]
