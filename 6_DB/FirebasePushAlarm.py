from pyfcm import FCMNotification

APIKEY = "AAAA0brfvYU:APA91bG31YupO8cjeVl669-duqw_kjRE1XSUdLl0dS9PhPL9YDN1D164W6Hurr9JCUbWgK5Wa9WZ4luNt9UMcccP0XVlNMgvfyaiudeAQcHscCaIu7AZAuB5wZna1uUfr8mM66whRKqx"
TOKEN = "fvZEUEdZTmCJxeoI3ORBaX:APA91bHkPONeVihrXQYD96YB11xhoQGU4-NKgXW9mmAlTtCLLwKZ3-IgdaGz6D_HINEwpJqKLZFw3SE6AjgqoCmDITcZnobJxl9dO0OPsBs8RK49JHI-at3O7FoomBEsKAVW70eOpSTn"

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


sendMessage("인식완료", "똑쇼")
