#
# The DevKit container is the environment in which TeamCity is executed. It is as an environment for the testing ...
# ... of potential tooling extension and security testing purposes.
#

# @param teamCityImage TeamCity Agent Docker Image
ARG teamCityImage

FROM ${teamCityImage}

USER root

RUN rm -rf /opt/buildagent
USER buildagent

CMD ["sleep", "infinity"]
