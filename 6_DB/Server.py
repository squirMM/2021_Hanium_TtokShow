import socket, threading;
import pymysql
import cvbarcode as cv

def barcode(bar):
    local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
    db = pymysql.connect(
        host=local,
        user='user',
        db='ttockshow',
        password='qwert123',
        charset='utf8'
    )
    sendD = []
    curs = db.cursor()
    curs2 = db.cursor()
    while bar == 'Start':
        bar = cv.getBarcode()
    bar = bar[0:13]
    sql = """select user_id, date, contents, star_rank, cite from review where barcord_id = %s order by date desc"""
    sql2 = """select barcord_id, name, image_link, star_avg from product where barcord_id = %s """
    curs.execute(sql, [bar])
    curs2.execute(sql2,[bar])
    select = list(curs.fetchmany(1000))
    selectp = list(curs2.fetchmany(1))
    db.commit()
    for i in range(len(selectp[0])):
        sendD.append(str(selectp[0][i]))
    sendD.append("0")
    for i in range(len(select)):
        for j in range(len(select[i])):
            sendD.append(str(select[i][j]))
    ret = '#'.join(sendD)
    return ret

def binder(client_socket, addr):
    print('Connected by', addr)
    try:
        while True:
            data = client_socket.recv(4)
            length = int.from_bytes(data, "little")
            data = client_socket.recv(length)
            msg = data.decode()
            if len(msg) != 0:
                msg = barcode(msg)
            else:
                #msg = barcode("start")
                msg = barcode("8801007099507")

            data = msg.encode()
            
            length = len(data)
            client_socket.sendall(length.to_bytes(4, byteorder='little'))
            client_socket.sendall(data)

    except:
        print("except : " , addr)
    finally:
        print("end")
        client_socket.close()
        
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server_socket.bind(('', 9999))
server_socket.listen()
try:
    print("Start")
    while True:
        client_socket, addr = server_socket.accept()
        th = threading.Thread(target=binder, args = (client_socket,addr))
        th.start()
except:
    print("server")
finally:
    server_socket.close()


