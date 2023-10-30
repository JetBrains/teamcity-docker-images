#
# The DevKit container is the environment in which TeamCity is executed. It is as an environment for the testing ...
# ... of potential tooling extension and security testing purposes.
#

ARG teamCityServerImage

FROM ${teamCityServerImage}

USER root

RUN rm -rf /opt/teamcity
USER tcuser:tcuser

CMD ["sleep", "infinity"]
