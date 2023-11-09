#
# The DevKit container is the environment in which TeamCity is executed. It is as an environment for the testing ...
# ... of potential tooling extension and security testing purposes.
#

# @param teamCityImage TeamCity Server Docker Image
ARG teamCityImage

FROM ${teamCityImage}

USER root

RUN rm -rf /opt/teamcity/temp/* && \
    rm -rf /opt/teamcity/logs/* && \
    rm -rf /opt/teamcity/conf/* && \
    rm -rf /opt/teamcity/lib/* && \
    rm -rf /opt/teamcity/bin/* && \
    rm -rf /opt/teamcity/devPackage/* && \
    rm -rf /opt/teamcity/webapps/* && \
    rm -rf /opt/teamcity/licenses/*

USER tcuser:tcuser

CMD ["sleep", "infinity"]
