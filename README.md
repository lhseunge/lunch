# Lunch 🍽️

카카오 Maps 기반 개인 맞춤형 점심 메뉴 관리 및 자동 추천 시스템


## 📋 프로젝트 소개
Lunch는 매일 점심 메뉴 선택에 고민하는 직장인들을 위한 개인 맞춤형 점심 추천 서비스입니다.

사용자의 위치 및 가게 정보 저장을 기반으로 카카오 Maps API를 활용하여 최적의 점심 장소를 추천합니다.

## 🎯 핵심 기능
- 카카오 Maps API를 통한 현재 위치 기반 음식점 검색

- 사용자별 또는 메뉴 카테고리별로 관리 가능한 개인화 저장 기능

- 메뉴 추첨 이력을 시각화하여 특정 메뉴가 몇 회 추천되었는지 확인 가능


## 🛠 기술 스택

__Backend__

- Language: Java 17
- Framework: Spring Boot 3.4.2
- Template Engine: Thymeleaf
- Database: PostgreSQL, H2 (개발/테스트)
- ORM: Spring Data JPA + QueryDSL 5.0.0
- Build Tool: Gradle
- Authentication: JWT (JJWT 0.12.6)
- HTTP Client: WebClient
- Cache: Spring Cache + Caffeine
- Documentation: SpringDoc OpenAPI 3 (Swagger)
- Utility: Lombok

__External APIs__

- Kakao Maps API: 지도 및 장소 검색

## 🗂 프로젝트 구조
```
src/
├── main/  
│   ├── java/  
│   │   └── com/fun/lunch/  
│   │       ├── LunchApplication.java  
│   │       ├── domain/  
│   │       │   └── store/  
│   │       │       ├── controller/  
│   │       │       │   ├── PersonalController.java  
│   │       │       │   └── StoreController.java  
│   │       │       ├── dto/  
│   │       │       │   ├── DrawStatistics.java  
│   │       │       │   ├── PersonalRequest.java  
│   │       │       │   ├── PersonalResponse.java  
│   │       │       │   ├── StoreRequest.java  
│   │       │       │   └── StoreResponse.java  
│   │       │       ├── entity/  
│   │       │       │   ├── DrawHistory.java  
│   │       │       │   ├── Personal.java  
│   │       │       │   └── Store.java  
│   │       │       ├── repository/  
│   │       │       │   ├── DrawHistoryRepository.java  
│   │       │       │   ├── PersonalRepository.java  
│   │       │       │   └── StoreRepository.java  
│   │       │       └── service/  
│   │       │           ├── impl/  
│   │       │           │   ├── PersonalServiceImpl.java  
│   │       │           │   └── StoreServiceImpl.java  
│   │       │           ├── PersonalService.java  
│   │       │           └── StoreService.java  
│   │       ├── works/  
│   │       │   ├── controller/  
│   │       │   │   └── WorksController.java  
│   │       │   ├── dto/  
│   │       │   │   ├── AbstractBotMessage.java  
│   │       │   │   ├── BotMessage.java  
│   │       │   │   ├── BotMessageButton.java  
│   │       │   │   ├── BotMessageButtonAction.java  
│   │       │   │   ├── BotMessageSticker.java  
│   │       │   │   ├── BotMessageText.java  
│   │       │   │   ├── ExceptionAlertRequest.java  
│   │       │   │   ├── WorksAccessToken.java  
│   │       │   │   └── WorksAccessTokenRequest.java  
│   │       │   ├── repository/  
│   │       │   └── service/  
│   │       │       ├── impl/  
│   │       │       │   ├── JwtTokenProvider.java  
│   │       │       │   ├── WorksApi.java  
│   │       │       │   └── WorksServiceImpl.java  
│   │       │       └── WorksService.java  
│   │       └── global/  
│   │           ├── config/  
│   │           │   ├── AsyncConfig.java  
│   │           │   ├── CacheConfig.java  
│   │           │   ├── CacheType.java  
│   │           │   ├── SwaggerConfig.java  
│   │           │   └── WorksConfig.java  
│   │           ├── exception/  
│   │           │   ├── CustomExceptionEnum.java  
│   │           │   └── ResponseException.java  
│   │           ├── handler/  
│   │           │   └── GlobalExceptionHandler.java  
│   │           ├── scheduler/  
│   │           │   └── Scheduler.java  
│   │           └── view/  
│   │               └── ViewController.java  
│   └── resources/  
│       ├── api/  
│       │   └── lunch.http              # HTTP 클라이언트 테스트 파일  
│       ├── keys/  
│       │   └── works_private.key       # Works API 인증키  
│       ├── static/  
│       │   ├── css/  
│       │   └── favicon.ico  
│       ├── templates/                  # Thymeleaf 템플릿  
│       │   ├── add.html  
│       │   ├── error.html  
│       │   ├── index.html  
│       │   ├── map.html  
│       │   └── statistics.html  
│       ├── application.yml  
│       ├── application-dev.yml  
│       └── application-inmemory.yml  
└── test/                               # 테스트 코드
```
