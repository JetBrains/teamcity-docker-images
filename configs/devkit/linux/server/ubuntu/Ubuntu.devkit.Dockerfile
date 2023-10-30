#
# The DevKit container is the environment in which TeamCity is executed. It is as an environment for the testing ...
# ... of potential tooling extension and security testing purposes.
#

# @param teamCityImage TeamCity Server Docker Image
ARG teamCityImage

FROM ${teamCityImage}

USER root

RUN rm -rf /opt/teamcity
USER tcuser:tcuser

CMD ["sleep", "infinity"]
