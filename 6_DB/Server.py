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
    curs = db.cursor()
    curs2 = db.cursor()
    sendD = []
    while bar == 'Start':
        bar = cv.getBarcode()
    bar = bar[0:13]
    sql = """select user_id, date, contents, star_rank, cite from review where barcord_id = %s order by date desc"""
    sql2 = """select barcord_id, name, image_link, star_avg, count from product where barcord_id = %s """
    curs.execute(sql, [bar])
    curs2.execute(sql2,[bar])
    select = list(curs.fetchall())
    selectp = list(curs2.fetchmany(10))
    db.commit()
    for i in range(len(selectp[0])):
        sendD.append(str(selectp[0][i]))
    for i in range(len(select)):
        for j in range(len(select[i])):
            sendD.append(str(select[i][j]))
    sendD = '#'.join(sendD)
    return sendD

def binder(client_socket, addr):
    print('Connected by', addr);
    try:
        msg = barcode('8801007160337')
        while True:
            data = msg.encode();

            data1 = "아이스브레이커스"
            data1 = data1.encode();
            length1 = len(data1);

            length = len(data);
            client_socket.sendall(length.to_bytes(4, byteorder='little'));
            client_socket.sendall(data);

    except:
        print("except : " , addr);
    finally:
        client_socket.close();
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1);
server_socket.bind(('', 9999));
server_socket.listen();
try:
    print("Start");
    while True:
        client_socket, addr = server_socket.accept();
        th = threading.Thread(target=binder, args = (client_socket,addr));
        th.start();
except:
    print("server");
finally:
    server_socket.close();


