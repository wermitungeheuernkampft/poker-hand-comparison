FROM ubuntu:latest
#RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-key C99B11DEB97541F0 && apt-add-repository https://cli.github.com/packages && apt update && apt install gh -y
RUN apt-get update && apt-get install git -y 
RUN mkdir /root/.ssh/
ADD id_rsa /root/.ssh/id_rsa
RUN chmod -R 0600 /root/.ssh/id_rsa
RUN touch /root/.ssh/known_hosts
RUN ssh-keyscan github.com  >> /root/.ssh/known_hosts

# RUN useradd -ms /bin/bash testuser
# COPY id_rsa /home/testuser/.ssh/
# COPY id_rsa.pub /home/testuser/.ssh/
# RUN chown testuser:testuser /home/testuser \
#        && chown testuser:testuser /home/testuser/.ssh \
#        && chown testuser:testuser /home/testuser/.ssh/*
# RUN echo >>  ~/.ssh/known_hosts \
#        && ssh-keyscan github.com >> ~/.ssh/known_hosts \
#        #&& cd /home/testuser \
#        && git clone git@github.com:wermitungeheuernkampft/poker-hand-comparison.git
# RUN git config --global user.name "azzamhafez"
# RUN git config --global user.email "azzamhafez87@gmail.com"  
# RUN git config --global --list     
RUN git clone git@github.com:wermitungeheuernkampft/poker-hand-comparison.git
RUN cd poker-hand-comparison/
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
