#
# The DevKit container is the environment in which TeamCity is executed. It is as an environment for the testing ...
# ... of potential tooling extension and security testing purposes.
#

# @param teamCityImage TeamCity Agent Docker Image
ARG teamCityImage

FROM ${teamCityImage}

USER root

# Remove agent code sequentially to work around the inability to delete volumes from the base image.
RUN rm -rf '/opt/buildagent/bin/*' && \
    rm -rf '/opt/buildagent/tools/*' && \
    rm -rf '/opt/buildagent/system/*' && \
    rm -rf '/opt/buildagent/plugins/*' && \
    rm -rf '/opt/buildagent/temp/*' && \
    rm -rf '/opt/buildagent/work/*' && \
    rm -rf '/opt/buildagent/lib/*' && \
    rm -rf '/opt/buildagent/conf/*' && \
    rm -rf '/opt/buildagent/launcher/*' && \
    rm -rf '/data/teamcity_agent/conf/*'

USER buildagent

CMD ["sleep", "infinity"]