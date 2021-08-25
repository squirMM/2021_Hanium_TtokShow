from pyfcm import FCMNotification

APIKEY = "AAAA0brfvYU:APA91bG31YupO8cjeVl669-duqw_kjRE1XSUdLl0dS9PhPL9YDN1D164W6Hurr9JCUbWgK5Wa9WZ4luNt9UMcccP0XVlNMgvfyaiudeAQcHscCaIu7AZAuB5wZna1uUfr8mM66whRKqx"
TOKEN = "cl9EC1XESUeCCydRN6QEhf:APA91bHvzEJwbnygF79hYXT4xvqi1kpPnVde3bIOx8jgbgV93B1jBh-v-JfYb-o-os8cHlRu-EYEyPhbXv42ia-7TD4hx6sFUey3tHzFRckZ7i0xs2y7IltVyYEvjqX-9_nMe16Zi9wo"

# 파이어베이스 콘솔에서 얻어 온 서버 키를 넣어 줌
push_service = FCMNotification(APIKEY)


def sendMessage(body, title):
    # 메시지 (data 타입)
    data_message = {
        "body": body,
        "title": title
    }

    # 토큰값을 이용해 1명에게 푸시알림을 전송함
    result = push_service.single_device_data_message(registration_id=TOKEN, data_message=data_message)

    # 전송 결과 출력
    print(result)


sendMessage("인식완료", "상품을 인식하였습니다.")
