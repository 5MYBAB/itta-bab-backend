package com.fivemybab.ittabab.user.command.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivemybab.ittabab.config.redis.MailService;
import com.fivemybab.ittabab.config.redis.RedisService;
import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.exception.ServerInternalException;
import com.fivemybab.ittabab.user.command.application.dto.AuthCodeRequestDto;
import com.fivemybab.ittabab.user.command.application.dto.CreateUserRequest;
import com.fivemybab.ittabab.user.command.application.dto.MailRequestDto;
import com.fivemybab.ittabab.user.command.application.dto.UpdateUserRequest;
import com.fivemybab.ittabab.user.command.domain.aggregate.BootCamp;
import com.fivemybab.ittabab.user.command.domain.aggregate.Course;
import com.fivemybab.ittabab.user.command.domain.aggregate.UserInfo;
import com.fivemybab.ittabab.user.command.domain.repository.BootCampRepository;
import com.fivemybab.ittabab.user.command.domain.repository.CourseRepository;
import com.fivemybab.ittabab.user.command.domain.repository.UserRepository;
import com.fivemybab.ittabab.user.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BootCampRepository bootCampRepository;
    private final MailService mailService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;

    private String AUTH_CODE_PREFIX = "AuthCode_";
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Value("${google.api.key}")
    private String googleApiKey;
    @Value("${google.map-api-url}")
    private String geolocationUrl;

    @Value("${naver.client-id}")
    private String clientId;
    @Value("${naver.secret-key}")
    private String clientSecret;
    @Value("${naver.geocoding-url}")
    private String geocodingUrl;

    private final double allowedDistance = 1; // 허용 가능한 거리 (킬로미터 단위)

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARACTERS = LOWER + DIGITS;
    private static final int PASSWORD_LENGTH = 12;

    @Transactional
    public void createUser(CreateUserRequest newUser) {

        // 넘어온 객체를 UserEntity로 변환
        UserInfo user = modelMapper.map(newUser, UserInfo.class);

        // 위치 인증
        Long courseId = user.getCourseId();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("훈련 과정을 찾을 수 없습니다. ID: " + courseId));

        Long bootId = course.getBootId();
        BootCamp bootCamp = bootCampRepository.findById(bootId)
                .orElseThrow(() -> new NotFoundException("훈련 기관(bootcamp)을 찾을 수 없습니다. ID: " + bootId));

        // 부캠 주소를 좌표로 변환
        double[] location = getCoordinates(bootCamp.getAddress());

        double targetLatitude = location[0];
        double targetLongitude = location[1];

        // 현재 위치가 해당 부캠에서 0.5km 안에 있는지 검증
        currentLocationVerification(targetLatitude, targetLongitude);

        user.encryptPwd(passwordEncoder.encode(newUser.getPwd()));
        userRepository.save(user);
    }

    @Transactional
    public void modifyUser(Long userNo, UpdateUserRequest updateUserRequest) {

        UserInfo foundUser = userRepository.findByUserId(userNo);
        foundUser.modifyPwd(passwordEncoder.encode(updateUserRequest.getPwd()));
    }

    @Transactional
    public void deleteUser(Long userNo) {

        userRepository.deleteById(userNo);
    }

    public void sendCodeToEmail(MailRequestDto mailRequestDto) {
        String email = mailRequestDto.getEmail();

        // email 중복검사
        sameUserInDBByEmail(email);

        String title = "[itta-bab] 회원가입 이메일 인증 번호";
        String authCode = createCode();
        mailService.sendEmail(email, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + email,
                authCode, Duration.ofMillis(authCodeExpirationMillis));
    }

    private void sameUserInDBByEmail(String email) {

        UserInfo user = userRepository.findByEmail(email);

        if (user != null) {
            // 사용자 정보가 존재할 경우 예외 발생
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + email);
        }
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("UserCommandService.createCode() exception occur");
            throw new ServerInternalException("UserCommandService.createCode()에서 에러가 발생했습니다.");
        }
    }


    public boolean checkAuthCode(AuthCodeRequestDto authCodeRequestDto) {
        return EmailVerification(authCodeRequestDto.getEmail(), authCodeRequestDto.getAuthCode());
    }

    private boolean EmailVerification(String email, String authCode) {

        sameUserInDBByEmail(email);

        String key = AUTH_CODE_PREFIX + email;
        String redisAuthCode = redisService.getValues(key);

        if (!(redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode))) {
            return false;   // 인증번호 틀림
        } else {
            redisService.deleteValues(key);
            return true;   // 인증번호 맞음
        }
    }

    /* 현재 위치 구하기 */
    public double[] getCurrentLocation(RestTemplate restTemplate) {

        try {

            // API 호출
            JsonNode response = restTemplate.postForObject(geolocationUrl + googleApiKey, null, JsonNode.class);

            double latitude = 0L;
            double longitude = 0L;

            if (response != null) {

                // 좌표 추출
                JsonNode location = response.path("location");
                latitude = location.path("lat").asDouble();
                longitude = location.path("lng").asDouble();
            }

            return new double[]{latitude, longitude};
        } catch (Exception e) {
            throw new ServerInternalException("현재 위치 구하던 중 에러 발생");
        }
    }

    // Haversine 공식을 사용하여 두 좌표 간의 거리 계산
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (킬로미터 단위)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // 거리를 킬로미터로 변환

        return distance; // 거리 반환
    }

    /* 현재 위치 검증 (등록한 훈련기관(bootcamp)의 위치와 0.5km 안에 있는 지 검증 */
    public void currentLocationVerification(double targetLatitude, double targetLongitude) {

        RestTemplate restTemplate = new RestTemplate();

        double[] curLocations = getCurrentLocation(restTemplate);

        double currentLatitude = curLocations[0];
        double currentLongitude = curLocations[1];

        System.out.println("현재 위치: 위도 " + currentLatitude + ", 경도 " + currentLongitude);
        System.out.println("타겟 위치: 위도 " + targetLatitude + ", 경도 " + targetLongitude);

        // 두 위치 간의 거리 계산
        double distance = haversine(targetLatitude, targetLongitude, currentLatitude, currentLongitude);
        System.out.println("현재 위치와 지정된 위치 간의 거리: " + distance + " km");

        // 거리 검증
        if (distance <= allowedDistance) {
            System.out.println("현재 위치가 허용 범위 내에 있습니다.");
        } else {
            System.out.println("현재 위치가 허용 범위를 초과합니다.");
            throw new IllegalArgumentException("현재 위치가 허용 범위를 초과합니다. 회원 가입이 불가합니다. ");
        }

    }

    /* 주소를 받아 좌표를 반환 */
    public double[] getCoordinates(String address) {

        try {
            String addr = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String apiUrl = geocodingUrl + addr;
            URL url = new URL(apiUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("x-ncp-apigw-api-key-id", clientId);
            con.setRequestProperty("x-ncp-apigw-api-key", clientSecret);

            int responseCode = con.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON 파싱
                JSONObject jsonObj = new JSONObject(response.toString());
                JSONArray addresses = jsonObj.getJSONArray("addresses");

                if (addresses.length() > 0) {
                    JSONObject location = addresses.getJSONObject(0);
                    double latitude = location.getDouble("y"); // 위도
                    double longitude = location.getDouble("x"); // 경도
                    return new double[]{latitude, longitude};
                } else {
                    throw new Exception("좌표 정보를 찾을 수 없습니다.");
                }

            } else {
                throw new Exception("네이버 API 응답 오류: " + responseCode);
            }

        } catch (Exception e) {
            throw new ServerInternalException("geocoding 중 에러 발생");
        }
    }

    @Transactional
    public void findPwdAndRandomPwd(String username, String loginId, String email) {

        Long userId = userMapper.findPwd(username, loginId, email)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));

        String title = "[itta-bab] 비밀번호가 재설정되었습니다.";
        String password = generateTemporaryPassword();

        UserInfo foundUser = userRepository.findByUserId(userId);
        foundUser.modifyPwd(passwordEncoder.encode(password));

        mailService.sendEmail(email, title, password);

    }

    public static String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // 나머지 길이를 랜덤하게 채우기
        for (int i = 2; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // 패스워드 문자열 섞기
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        List<Character> characters = input.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(characters);
        StringBuilder shuffled = new StringBuilder();
        characters.forEach(shuffled::append);
        return shuffled.toString();
    }

}
