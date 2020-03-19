mkdir microservices
cd microservices

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=status-service \
--package-name=com.wexort.hatee.core.status \
--groupId=com.wexort.hatee.core.status \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
status-service

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=comment-service \
--package-name=com.wexort.hatee.core.comment \
--groupId=com.wexort.hatee.core.comment \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
comment-service

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=status-composite-service \
--package-name=com.wexort.hatee.composite.status \
--groupId=com.wexort.hatee.composite.status \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
status-composite-service

cd ..