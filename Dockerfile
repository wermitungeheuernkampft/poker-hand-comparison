FROM ubuntu:latest
#RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-key C99B11DEB97541F0 && apt-add-repository https://cli.github.com/packages && apt update && apt install gh -y
RUN apt-get update && apt-get install git -y 
# RUN useradd -ms /bin/bash 
# RUN chown realtebo:realtebo /home/realtebo \
#        && chown realtebo:realtebo /home/realtebo/.ssh \
#        && chown realtebo:realtebo /home/realtebo/.ssh/*
# RUN echo >>  ~/.ssh/known_hosts \
#        && ssh-keyscan github.com >> ~/.ssh/known_hosts \
#        && cd /home/realtebo \
#        && git clone git@github.com:realtebo/my-private-tool.git tools        
#RUN git clone https://github.com/wermitungeheuernkampft/poker-hand-comparison.git
#RUN cd poker-hand-comparison/
#ADD /poker-hand-comparison/ .
#COPY --chown=root / .
COPY --chown=root prepare.sh .
COPY --chown=root run.sh .
RUN chmod +x prepare.sh
RUN chmod +x run.sh
RUN whoami
RUN ls -la .
RUN ./prepare.sh
RUN sleep 200
RUN cat src/test/scala/input.sh && ./run.sh > src/test/scala/output.sh
