<h1 align="center">Diggin' Room</h1>
<p align="center"><img align="center" width=300 height=300 src="https://play-lh.googleusercontent.com/j7jUmkZh6r2u3d-AVk-zRx-WmUG9kB1ZdDyRFch7HQuzgymAjtjKf-K8Gj5eHoO2zes=w480-h960-rw"> </p>

<p align="center">사용자 활동을 기반으로 음악을 추천하는 숏폼 컨텐츠 서비스 🎵</p>

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

# 기능
**음악 탐색**

새로운 음악을 찾기 위해 아래로 스와이프 해보세요!

**추천 알고리즘**

세상의 모든 장르에서 추천되는 음악을 즐겨보세요!

**스크랩**

탐색하다 발견한 좋은 음악은 스크랩해서 나중에 또 들어보세요!

**댓글**

다른 친구들과 음악에 대한 생각을 나눠보세요!

# 구현
## 서버

## 안드로이드

**기술 스택**
- MVVM
- AAC ViewModel
- Repository 패턴
- UI State
- Retrofit
- MockK
- 자체 자동 의존 주입

**[RoomPager](https://github.com/DYGames/RoomPager)**

문제점
- 추천 알고리즘 반영을 위한 "싫어요" 제스처를 위해 4방향 스크롤 지원이 필요
- 기존의 ScrollView 중첩으로는 WebView 호환이 불가능 (WebView에서 터치 이벤트를 가로챔)
- WebView 캐싱, 뷰 재활용 등 복잡한 상태 관리를 위해 커스텀 뷰를 만들 필요성 제기

기능
- 4방향 스크롤
- 부드러운 페이징
- 끊김없는 동영상 재생을 위한 이전, 다음 영상 미리 로딩
- 최소한의 뷰(3개)로 4방향 스크롤 가능하게 뷰 재사용
- RecyclerView와 동일하게 Adatper와 ViewHolder 구현으로 사용 가능
- 새로운 동영상 로드, 재사용에 대한 콜백 제공

**LogResult**

문제점
- 네트워크 요청, 비즈니스 로직에서 성공과 실패 상황 모두 로그 출력이 필요

기능
- kotlin의 Result 클래스와 비슷하지만 로그를 자동으로 출력하는 LogResult 구현
- logRunCatching 전역 함수 제공
- 로그를 여러 채널에서 출력 할 수 있는 Logger 인터페이스 제공
- logRunCatching으로 코드를 실행하고 onSuccess, onFailure 콜백을 호출하면서 등록한 Logger들에 자동으로 로그 출력
- 현재 앱에는 콘솔, 파이어베이스 채널 구현

**테스트**
- ViewModel Test
- Domain Test

**환경**
- Kotlin 1.8.20
- Android Target SDK 33
- Android Gradle Plugin 8.0.2

## 협업
**Git**
- GitHub Flow 기반 [DRF (DigginRoomFlow)](https://github.com/woowacourse-teams/2023-diggin-room/wiki/%EA%B9%83-%EB%B8%8C%EB%9E%9C%EC%B9%98-%EC%A0%84%EB%9E%B5)

**Github Wiki**
- 회고
- 프로젝트 운영 및 계획
- 개발 문서

**Notion**
- 데일리 미팅
- 회의록
- 기획
- 자료
- 고민하기
- 팀 컨벤션

**Slack**
- 잡담방아님?ㅋㅋ

## 팀원 & 역할
![](https://avatars.githubusercontent.com/u/39221443)|![](https://avatars.githubusercontent.com/u/39221443)|![](https://avatars.githubusercontent.com/u/39221443)|![](https://avatars.githubusercontent.com/u/39221443)|![](https://avatars.githubusercontent.com/u/74398096)|![](https://avatars.githubusercontent.com/u/39221443)|![](https://avatars.githubusercontent.com/u/39221443)
|-- | -- | -- | -- | -- | -- | --|
우기 | 코건 | 베리 | 파워 | 블랙캣 | 땡칠 | 콩하나

