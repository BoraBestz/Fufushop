# Build แอปด้วย Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# กำหนด working directory
WORKDIR /app

# คัดลอกไฟล์ทั้งหมดเข้าไป
COPY . .

# Build โดยข้าม unit test
RUN mvn clean package -DskipTests

# Stage 2: Run JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Working directory
WORKDIR /app

# คัดลอก JAR ที่ build แล้วจาก stage ก่อนหน้า
COPY --from=builder /app/target/*.jar app.jar

# เปิดพอร์ตที่แอปรับ
EXPOSE 8080

# สั่งรัน Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]